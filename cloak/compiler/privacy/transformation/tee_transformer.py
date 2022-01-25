"""
This module defines zkay->solidity transformers for the smaller contract elements (statements, expressions, state variables).
"""

from __future__ import annotations
from math import exp
from operator import index, xor
import re
from typing import Optional, Union

from cloak.cloak_ast.visitor.transformer_visitor import AstTransformerVisitor
from cloak.compiler.solidity.fake_solidity_generator import WS_PATTERN, ID_PATTERN
from cloak.config import cfg
from cloak.cloak_ast.analysis.contains_private_checker import contains_private_expr
from cloak.cloak_ast.ast import ExpressionStatement, Identifier, ReclassifyExpr, Expression, IfStatement, StatementList, BlankLine, \
    IdentifierExpr, Parameter, VariableDeclaration, AnnotatedTypeName, StateVariableDeclaration, Mapping, MeExpr, TypeName, \
    RequireStatement, Array, AssignmentStatement, Block, Comment, LiteralExpr, IndexExpr, FunctionCallExpr, BuiltinFunction, TupleExpr, \
    NumberLiteralExpr, MemberAccessExpr, WhileStatement, BreakStatement, ContinueStatement, ForStatement, DoWhileStatement, BytesTypeName


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
            t = Array(BytesTypeName(), 5)
        else:
            t = self.visit(ast.type_name)
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
        return IdentifierExpr('msg').dot('sender')

    def visitLiteralExpr(self, ast: LiteralExpr):
        """Rule (7), don't modify constants."""
        return ast

    def visitIdentifierExpr(self, ast: IdentifierExpr):
        """Rule (8), don't modify identifiers."""
        return ast

    def visitIndexExpr(self, ast: IndexExpr):
        """Rule (9), transform location and index expressions separately."""
        return self.visit(ast.arr).index(self.visit(ast.key))

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
