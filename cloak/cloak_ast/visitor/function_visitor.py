from cloak.cloak_ast.ast import SourceUnit, Parameter
from cloak.cloak_ast.visitor.visitor import AstVisitor


class FunctionVisitor(AstVisitor):
    def __init__(self):
        super().__init__('node-or-children')

    def visitSourceUnit(self, ast: SourceUnit):
        for c in ast.contracts:
            for cd in c.constructor_definitions:
                self.visit(cd)
            for fd in c.function_definitions:
                self.visit(fd)

    def visitParameter(self, ast: Parameter):
        pass
