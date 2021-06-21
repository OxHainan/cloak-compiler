"""
This module defines zkay->solidity transformers for the smaller contract elements (statements, expressions, state variables).
"""

from __future__ import annotations
from cloak.compiler.name_remapper import V
from math import exp
from operator import index, xor
import re
from typing import Optional, Union

from cloak.compiler.privacy.circuit_generation.circuit_helper import HybridArgumentIdf, CircuitHelper
from cloak.cloak_ast.visitor.transformer_visitor import AstTransformerVisitor
from cloak.compiler.solidity.fake_solidity_generator import WS_PATTERN, ID_PATTERN
from cloak.config import cfg
from cloak.cloak_ast.analysis.contains_private_checker import contains_private_expr
from cloak.cloak_ast.ast import CipherText, ExpressionStatement, Identifier, ReclassifyExpr, Expression, IfStatement, StatementList, HybridArgType, BlankLine, \
    IdentifierExpr, Parameter, VariableDeclaration, AnnotatedTypeName, StateVariableDeclaration, Mapping, MeExpr, TypeName, \
    RequireStatement, Array, AssignmentStatement, Block, Comment, LiteralExpr, IndexExpr, FunctionCallExpr, BuiltinFunction, TupleExpr, \
    NumberLiteralExpr, MemberAccessExpr, WhileStatement, BreakStatement, ContinueStatement, ForStatement, DoWhileStatement
from cloak.cloak_ast.visitor.deep_copy import replace_expr


class TeeVarDeclTransformer(AstTransformerVisitor):
    """
    Transformer for types, which was left out in the paper.

    This removes all privacy labels and converts the types of non-public variables (not @all)
    to cipher_type.
    """

    def __init__(self):
        super().__init__()
        self.expr_trafo = TeeExpressionTransformer()

    def visitAnnotatedTypeName(self, ast: AnnotatedTypeName):
        if ast.is_private():
            t = TypeName.cipher_type(ast)
        else:
            t = self.visit(ast.type_name.clone())
        return AnnotatedTypeName(t)

    def visitVariableDeclaration(self, ast: VariableDeclaration):
        if ast.annotated_type.is_private():
            ast.storage_location = 'memory'
        return self.visit_children(ast)

    def visitParameter(self, ast: Parameter):
        if ast.get_related_function().is_tee():
            # will be replaced with tee paramaters later.
            return None

        ast = self.visit_children(ast)
        if not ast.annotated_type.type_name.is_primitive_type():
            ast.storage_location = 'memory'

        return ast

    def visitStateVariableDeclaration(self, ast: StateVariableDeclaration):
        ast.keywords = [k for k in ast.keywords if k != 'public']
        # make sure every state var gets a public getter (required for simulation)
        ast.keywords.append('public')
        ast.expr = self.expr_trafo.visit(ast.expr)
        return self.visit_children(ast)

    def visitMapping(self, ast: Mapping):
        if ast.key_label is not None:
            ast.key_label = ast.key_label.name
        return self.visit_children(ast)


class TeeStatementTransformer(AstTransformerVisitor):
    """Corresponds to T from paper, (with additional handling of return statement and loops)."""

    def __init__(self):
        super().__init__()
        self.expr_trafo = TeeExpressionTransformer()
        self.var_decl_trafo = TeeVarDeclTransformer()

    def get_type_transform_stmt(self, origin_expr: Expression, from_type: AnnotatedTypeName, target_type: AnnotatedTypeName):
        if from_type.compatible_with(target_type):
            return origin_expr

        if target_type.is_numeric:
            return FunctionCallExpr(IdentifierExpr('uint256'), [origin_expr])

        if target_type.is_address():
            return FunctionCallExpr(IdentifierExpr('address'), [origin_expr])

        return origin_expr

    def read_and_mutate_value(self, var, old_state_index, is_cipher=False, is_mutate=False, mutate_index=None, is_map=False):
        old_state_arr_idx = IdentifierExpr(f'{cfg.tee_old_state_name}').as_type(
            Array(AnnotatedTypeName.uint_all()))
        mutate_arr_idx = IdentifierExpr(f'{cfg.tee_mutate_name}').as_type(
            Array(AnnotatedTypeName.uint_all()))
        v_simple_idx = IdentifierExpr('i').as_type(
            AnnotatedTypeName(TypeName.uint_type()))
        old_state_start_idx = IdentifierExpr(f'{cfg.tee_old_state_name}_start_idx')

        new_stmts = []

        if is_mutate and not mutate_index:
            raise ValueError(
                "mutate start index should be provided in mutate mode")

        if is_cipher:
            if isinstance(var, StateVariableDeclaration):
                idx = IdentifierExpr(var.idf.clone()).as_type(Array(
                    AnnotatedTypeName.uint_all(), NumberLiteralExpr(3)))
            else:
                idx = var
            new_read_stmts, new_assign_stmts = [], []
            for i in range(cfg.cipher_len):
                target_idx = idx.index(NumberLiteralExpr(i))
                read_prim_args = self.get_type_transform_stmt(target_idx,
                                                              var.annotated_type.type_name,
                                                              old_state_arr_idx.annotated_type.type_name.get_type())
                if not is_map:
                    new_read_stmts.append(AssignmentStatement(old_state_arr_idx.index(
                        NumberLiteralExpr(old_state_index).binop('+', NumberLiteralExpr(i))), read_prim_args))
                else:
                    new_read_stmts.append(AssignmentStatement(old_state_arr_idx.index(
                        old_state_start_idx.binop("+", v_simple_idx.binop("*", NumberLiteralExpr(cfg.cipher_len))).binop('+', NumberLiteralExpr(i))), read_prim_args))
                
                if is_mutate:
                    new_assign_stmts.append(AssignmentStatement(
                        target_idx,
                        mutate_arr_idx.index(
                            mutate_index.binop('+', NumberLiteralExpr(i)))
                    )
                    )
            new_stmts += new_read_stmts + new_assign_stmts
        else:
            target_idx = IdentifierExpr(var.idf.clone(), var.annotated_type)
            read_prim_args = self.get_type_transform_stmt(target_idx.idf,
                                                           var.annotated_type.type_name,
                                                           old_state_arr_idx.annotated_type.type_name.get_type())
            
            if not is_map:
                new_stmts.append(AssignmentStatement(old_state_arr_idx.index(
                    NumberLiteralExpr(old_state_index)), read_prim_args))
            else:
                new_stmts.append(AssignmentStatement(old_state_arr_idx.index(
                    old_state_start_idx.binop("+", v_simple_idx).binop('+', NumberLiteralExpr(i))), read_prim_args))
            
            if is_mutate:
                new_stmts.append(AssignmentStatement(
                    target_idx,
                    mutate_arr_idx.index(mutate_index))
                )
        return new_stmts

    def read_and_mutate_map(self, map: Union(Mapping, Array), old_state_index, is_mutate=False):
        v_simple_idx = IdentifierExpr('i').as_type(
            AnnotatedTypeName(TypeName.uint_type()))
        v_simple_stmt = v_simple_idx.idf.decl_var(
            TypeName.uint_type(), NumberLiteralExpr(0))

        read_start_idx = IdentifierExpr(f'{cfg.tee_read_name}_start_idx')
        read_arr_idx = IdentifierExpr(f'{cfg.tee_read_name}').as_type(
            Array(AnnotatedTypeName.uint_all()))
        mutate_start_idx = IdentifierExpr(f'{cfg.tee_mutate_name}_start_idx')
        mutate_arr_idx = IdentifierExpr(f'{cfg.tee_mutate_name}').as_type(
            Array(AnnotatedTypeName.uint_all()))
        old_state_start_idx = IdentifierExpr(f'{cfg.tee_old_state_name}_start_idx')

        map_depth = map.annotated_type.type_name.get_map_depth()
        target_map_idx = IdentifierExpr(map.idf.clone(), map.annotated_type)
        if is_mutate:
            key_arr = IdentifierExpr(f'{cfg.tee_mutate_name}').as_type(
                Array(AnnotatedTypeName.uint_all()))
            key_index_arr = mutate_start_idx
        else:
            key_arr = IdentifierExpr(f'{cfg.tee_read_name}').as_type(
                Array(AnnotatedTypeName.uint_all()))
            key_index_arr = read_start_idx

        n_target_type = map.annotated_type.type_name
        for i in range(map_depth):
            target_map_idx = target_map_idx.index(
                self.get_type_transform_stmt(
                    key_arr.index(key_index_arr.binop(
                        '+', NumberLiteralExpr(i+1)).binop('+', v_simple_idx)) if map_depth == 1
                    else key_arr.index(key_index_arr.binop(
                        '+', NumberLiteralExpr(i+1)).binop('+', v_simple_idx.binop('*', NumberLiteralExpr(map_depth+(1 if is_mutate else 0))))),
                    key_arr.annotated_type.type_name.get_type(),
                    n_target_type.key_type
                )
            )
            if i+1 < map_depth:
                n_target_type = n_target_type.value_type.type_name

        new_body_stmts = []
        if is_mutate:
            v_cond_expr = v_simple_idx.binop(
                '<', mutate_arr_idx.index(mutate_start_idx))

            if map.annotated_type.type_name.get_type().is_primitive_type():
                new_body_stmts += self.read_and_mutate_value(target_map_idx, old_state_index, False, True,
                                                             mutate_start_idx.binop('+', NumberLiteralExpr(
                                                                 map_depth)).binop('+', v_simple_idx.binop('*', NumberLiteralExpr(map_depth+1))), is_map=True)
                var_step = map_depth+1
            else:
                new_body_stmts += self.read_and_mutate_value(target_map_idx, old_state_index, True, True,
                                                             mutate_start_idx.binop('+', NumberLiteralExpr(
                                                                 map_depth)).binop('+', v_simple_idx.binop('*', NumberLiteralExpr(map_depth+cfg.cipher_len))), is_map=True)
                var_step = map_depth+cfg.cipher_len
            # Update mutate_start_idx
            start_index_update = AssignmentStatement(mutate_start_idx, mutate_start_idx.binop(
                '+', NumberLiteralExpr(var_step).binop('*', mutate_arr_idx.index(mutate_start_idx))))
            old_state_index_update = AssignmentStatement(old_state_start_idx, old_state_start_idx.binop(
                '+', NumberLiteralExpr(var_step).binop('*', mutate_arr_idx.index(mutate_start_idx))))
        else:
            v_cond_expr = v_simple_idx.binop(
                '<', read_arr_idx.index(read_start_idx))

            if map.annotated_type.type_name.get_type().is_primitive_type():
                new_body_stmts += self.read_and_mutate_value(
                    target_map_idx, old_state_index, is_map=True)
            else:
                new_body_stmts += self.read_and_mutate_value(
                    target_map_idx, old_state_index, True, is_map=True)

            start_index_update = AssignmentStatement(
                read_start_idx, read_start_idx.binop('+', NumberLiteralExpr(map_depth).binop('*', read_arr_idx.index(read_start_idx))))
            old_state_index_update = AssignmentStatement(
                old_state_start_idx, old_state_start_idx.binop('+', NumberLiteralExpr(map_depth).binop('*', read_arr_idx.index(read_start_idx))))

        v_update_expr = AssignmentStatement(
            v_simple_idx, v_simple_idx.binop('+', NumberLiteralExpr(1)))
        v_for = ForStatement(
            v_simple_stmt, v_cond_expr, v_update_expr, Block(new_body_stmts))

        return [v_for, start_index_update, old_state_index_update]

    def visitStatementList(self, ast: StatementList):
        """
        Rule (1)

        All statements are transformed individually.
        Whenever the transformation of a statement requires the introduction of additional statements
        (the CircuitHelper indicates this by storing them in the statement's pre_statements list), they are prepended to the transformed
        statement in the list.

        If transformation changes the appearance of a statement (apart from type changes),
        the statement is wrapped in a comment block which displays the original statement's code.
        """

        new_statements = []

        new_statements += [RequireStatement(IdentifierExpr('msg').dot('sender').as_type(
            AnnotatedTypeName.address_all()).binop("==", IdentifierExpr('tee')))]

        # Initialize old state
        old_state_start_idx = IdentifierExpr(f'{cfg.tee_old_state_name}_start_idx')
        old_state_start_var = old_state_start_idx.idf.decl_var(
                TypeName.uint_type(), NumberLiteralExpr(0))
        new_statements.append(old_state_start_var)

        # Record old states to read in old state array
        supp = ast.get_related_sourceuint().privacy_policy
        ffp = supp.get_function_policy(ast.function.idf.name)
        assert ffp
        old_state_index = 0
        read_prim_index = 0
        for i, v in enumerate(ffp.read_values):
            read_prim_index = i
            if v.annotated_type.type_name.is_primitive_type():
                new_statements += self.read_and_mutate_value(v, old_state_index)
                old_state_index += 1
                read_prim_index += 1
            elif isinstance(v.annotated_type.type_name, CipherText):
                new_statements += self.read_and_mutate_value(
                    v, old_state_index, True)
                old_state_index += cfg.cipher_len
                read_prim_index += 1
            else:
                break
            
        # Update old_state_start_idx for primitive value
        new_statements.append(AssignmentStatement(old_state_start_idx, old_state_start_idx.binop(
            '+', NumberLiteralExpr(old_state_index))))

        for _, v in enumerate(ffp.read_values[read_prim_index:]):
            assert isinstance(v.annotated_type.type_name, (Mapping, Array))
            new_statements.append(Comment())
            new_statements.append(
                Comment(f'record read old state: {v.idf}'))

            new_statements += self.read_and_mutate_map(v, old_state_index)

        new_statements.append(Comment())

        # Record old states to mutate in old state array and assign new state
        mutate_start_idx = IdentifierExpr(f'{cfg.tee_mutate_name}_start_idx')
        mutate_stmts = []
        mutate_prim_index, mutate_start_index = 0, 0
        for i, v in enumerate(ffp.mutate_values):
            mutate_prim_index = i
            if v.annotated_type.type_name.is_primitive_type():
                mutate_stmts += self.read_and_mutate_value(
                    v, old_state_index, False, True, NumberLiteralExpr(mutate_start_index))
                mutate_start_index += 1
                mutate_prim_index += 1
            elif isinstance(v.annotated_type.type_name, CipherText):
                mutate_stmts += self.read_and_mutate_value(
                    v, old_state_index, True, True, NumberLiteralExpr(mutate_start_index))

                mutate_start_index += cfg.cipher_len
                mutate_prim_index += 1
            else:
                break
        new_statements.append(StatementList(
            mutate_stmts, excluded_from_simulation=True))

        # Update mutate_start_idx for primitive value
        new_statements.append(AssignmentStatement(mutate_start_idx, mutate_start_idx.binop(
            '+', NumberLiteralExpr(mutate_start_index))))
        new_statements.append(AssignmentStatement(old_state_start_idx, old_state_start_idx.binop(
            '+', NumberLiteralExpr(mutate_start_index))))

        for _, v in enumerate(ffp.mutate_values[mutate_prim_index:]):
            assert isinstance(v.annotated_type.type_name, (Mapping, Array))
            new_statements.append(Comment())
            new_statements.append(
                Comment(f'record mutate old state and update: {v.idf}'))

            new_statements += self.read_and_mutate_map(v, old_state_index, True)

        ast.statements = new_statements
        return ast


class TeeExpressionTransformer(AstTransformerVisitor):
    """
    Roughly corresponds to T_L / T_e from paper.

    T_L and T_e are equivalent here, because parameter encryption checks are handled in the verification wrapper of the function body.
    In addition to the features described in the paper, this transformer also supports primitive type casting,
    tuples (multiple return values), operations with short-circuiting and function calls.
    """

    def __init__(self):
        super().__init__()

    @staticmethod
    def visitMeExpr(ast: MeExpr):
        """Replace me with msg.sender."""
        return replace_expr(ast, IdentifierExpr('msg').dot('sender')).as_type(AnnotatedTypeName.address_all())

    def visitLiteralExpr(self, ast: LiteralExpr):
        """Rule (7), don't modify constants."""
        return ast

    def visitIdentifierExpr(self, ast: IdentifierExpr):
        """Rule (8), don't modify identifiers."""
        return ast

    def visitIndexExpr(self, ast: IndexExpr):
        """Rule (9), transform location and index expressions separately."""
        return replace_expr(ast, self.visit(ast.arr).index(self.visit(ast.key)))

    def visitMemberAccessExpr(self, ast: MemberAccessExpr):
        return self.visit_children(ast)

    def visitTupleExpr(self, ast: TupleExpr):
        return self.visit_children(ast)

    def visitReclassifyExpr(self, ast: ReclassifyExpr):
        """
        Rule (11), trigger a boundary crossing.

        The reclassified expression is evaluated in the circuit and its result is made available in solidity.
        """
        ast = ast.expr
        self.visit_children(ast)
        return ast

    # def visitExpression(self, ast: Expression):
    #     raise NotImplementedError()
