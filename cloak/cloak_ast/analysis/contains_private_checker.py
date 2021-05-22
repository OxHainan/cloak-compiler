from typing import Optional

from cloak.cloak_ast.ast import Expression, AST, FunctionCallExpr, LocationExpr
from cloak.cloak_ast.visitor.visitor import AstVisitor


def contains_private_expr(ast: Optional[AST]):
    if ast is None:
        return False
    v = ContainsPrivVisitor()
    v.visit(ast)
    return v.contains_private


class ContainsPrivVisitor(AstVisitor):
    def __init__(self):
        super().__init__('node-or-children')
        self.contains_private = False

    def visitFunctionCallExpr(self, ast: FunctionCallExpr):
        if isinstance(ast.func, LocationExpr) and not ast.is_cast:
            self.contains_private |= ast.func.target.requires_verification
        self.visitExpression(ast)

    def visitExpression(self, ast: Expression):
        if ast.evaluate_privately:
            self.contains_private = True
        self.visitAST(ast)

    def visitAST(self, ast):
        if self.contains_private:
            return
        self.visitChildren(ast)
