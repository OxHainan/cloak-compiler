from typing import List, Union

from cloak.type_check.type_exceptions import TypeException
from cloak.errors.exceptions import CloakCompilerError
from cloak.cloak_ast.ast import FunctionCallExpr, LocationExpr, AssignmentStatement, \
    AST, Expression, Statement, StateVariableDeclaration, BuiltinFunction, \
    TupleExpr, InstanceTarget, VariableDeclaration, Parameter, Mapping
from cloak.cloak_ast.visitor.function_visitor import FunctionVisitor
from cloak.cloak_ast.visitor.visitor import AstVisitor


def has_side_effects(ast: AST) -> bool:
    return SideEffectsDetector().visit(ast)


def compute_modified_sets(ast: AST):
    v = DirectModificationDetector()
    v.visit(ast)

    v = IndirectModificationDetector()
    v.iterate_until_fixed_point(ast)


def check_for_undefined_behavior_due_to_eval_order(ast: AST):
    EvalOrderUBChecker().visit(ast)


class SideEffectsDetector(AstVisitor):

    def visitFunctionCallExpr(self, ast: FunctionCallExpr):
        if isinstance(ast.func, LocationExpr) and not ast.is_cast and ast.func.target.has_side_effects:
            return True
        else:
            return self.visitExpression(ast)

    def visitAssignmentStatement(self, ast: AssignmentStatement):
        return True

    def visitExpression(self, ast: Expression):
        return self.visitAST(ast)

    def visitStatement(self, ast: Statement):
        return self.visitAST(ast)

    def visitAST(self, ast: AST):
        return any(map(self.visit, ast.children()))


class DirectModificationDetector(FunctionVisitor):
    def visitAssignmentStatement(self, ast: AssignmentStatement):
        self.visitAST(ast)
        self.collect_modified_values(ast, ast.lhs)

    def collect_modified_values(self, target: Union[Expression, Statement], expr: Union[TupleExpr, LocationExpr]):
        if isinstance(expr, TupleExpr):
            for elem in expr.elements:
                self.collect_modified_values(target, elem)
        else:
            mod_value = InstanceTarget(expr)
            if mod_value in target.modified_values:
                raise TypeException(f'Undefined behavior due multiple different assignments to the same target in tuple assignment', expr)
            target.modified_values[mod_value] = None

    def visitLocationExpr(self, ast: LocationExpr):
        self.visitAST(ast)
        if ast.is_rvalue() and isinstance(ast.target, (VariableDeclaration, StateVariableDeclaration, Parameter)):
            red_value = InstanceTarget(ast)
            ast.read_values.add(red_value)

    def visitVariableDeclaration(self, ast: VariableDeclaration):
        ast.modified_values[InstanceTarget(ast)] = None

    def visitAST(self, ast: AST):
        ast.modified_values.clear()
        ast.read_values.clear()
        for child in ast.children():
            self.visit(child)
            ast.modified_values.update(child.modified_values)
            ast.read_values.update(child.read_values)


class IndirectModificationDetector(FunctionVisitor):
    def __init__(self):
        super().__init__()
        self.fixed_point_reached = True

    def iterate_until_fixed_point(self, ast):
        while True:
            self.visit(ast)
            if self.fixed_point_reached:
                break
            else:
                self.fixed_point_reached = True

    def visitFunctionCallExpr(self, ast: FunctionCallExpr):
        self.visitAST(ast)
        if isinstance(ast.func, LocationExpr):
            # for now no reference types -> only state could have been modified
            fdef = ast.func.target
            rlen = len(ast.read_values)
            # don't support call another fucntion that read or mutated mapping state variables now.
            for v in fdef.read_values:
                if isinstance(v.target, StateVariableDeclaration):
                    if isinstance(v.target.annotated_type.type_name, Mapping):
                        msg = f"Don't support *{ast.get_related_function().idf}* to call another fucntion *{fdef.name}*"\
                               " that read mapping state variables now"
                        raise CloakCompilerError(msg)
                    ast.read_values.add(v)
            # ast.read_values.update({v for v in fdef.read_values if isinstance(v.target, StateVariableDeclaration)})
            self.fixed_point_reached &= rlen == len(ast.read_values)

            # update modified values if any
            mlen = len(ast.modified_values)
            for v in fdef.modified_values:
                if isinstance(v.target, StateVariableDeclaration):
                    if isinstance(v.target.annotated_type.type_name, Mapping):
                        msg = f"Don't support *{ast.get_related_function().idf}* to call another fucntion *{fdef.name}*"\
                               " that mutated mapping state variables now"
                        raise CloakCompilerError(msg)
                    ast.modified_values[v] = None
            self.fixed_point_reached &= mlen == len(ast.modified_values)

    def visitAST(self, ast: AST):
        mlen = len(ast.modified_values)
        rlen = len(ast.read_values)
        for child in ast.children():
            self.visit(child)
            ast.modified_values.update(child.modified_values)
            ast.read_values.update(child.read_values)
        self.fixed_point_reached &= mlen == len(ast.modified_values)
        self.fixed_point_reached &= rlen == len(ast.read_values)


class EvalOrderUBChecker(AstVisitor):
    @staticmethod
    def visit_child_expressions(parent: AST, exprs: List[AST]):
        if len(exprs) > 1:
            modset = set(exprs[0].modified_values.keys())
            for arg in exprs[1:]:
                diffset = modset.intersection(arg.modified_values)
                if diffset:
                    setstr = f'{{{", ".join(map(str, diffset))}}}'
                    raise TypeException(f'Undefined behavior due to potential side effect on the same value(s) \'{setstr}\' in multiple expression children.\n'
                                        'Solidity does not guarantee an evaluation order for non-shortcircuit expressions.\n'
                                        'Since zkay requires local simulation for transaction transformation, all semantics must be well-defined.', parent)
                else:
                    modset.update(diffset)

            for arg in exprs:
                modset = set(arg.modified_values.keys())
                other_args = [e for e in exprs if e != arg]
                for arg2 in other_args:
                    diffset = modset.intersection(arg2.read_values)
                    if diffset:
                        setstr = f'{{{", ".join([str(val) + (f".{str(member)}" if member else "") for val, member in diffset])}}}'
                        raise TypeException(
                            f'Undefined behavior due to read of value(s) \'{setstr}\' which might be modified in this subexpression.\n'
                            'Solidity does not guarantee an evaluation order for non-shortcircuit expressions.\n'
                            'Since zkay requires local simulation for transaction transformation, all semantics must be well-defined.', arg)

    def visitFunctionCallExpr(self, ast: FunctionCallExpr):
        if isinstance(ast.func, BuiltinFunction):
            if ast.func.has_shortcircuiting():
                return
        self.visit_child_expressions(ast, ast.args)

    def visitExpression(self, ast: Expression):
        self.visit_child_expressions(ast, ast.children())

    def visitAssignmentStatement(self, ast: AssignmentStatement):
        self.visit_child_expressions(ast, ast.children())
