from cloak.cloak_ast.visitor.visitor import AstVisitor
from cloak.cloak_ast.ast import ConstructorOrFunctionDefinition
from cloak.type_check.type_exceptions import TypeException


class TypeCheckVisitor(AstVisitor): 

    def __init__(self, traversal='post', log=False):
        super().__init__(traversal=traversal, log=log)

    def visitConstructorOrFunctionDefinition(self, ast: ConstructorOrFunctionDefinition):
        for param in ast.parameters:
            if self.include_tee_param(str(param)):
                raise TypeException(f"Type check with cloak failed! Forbid using @tee in param. function: {ast.idf}, param: {param}", ast)

    def include_tee_param(self, param):
        if '@tee' in param:
            return True
        else:
            return False

def check_with_cloak(ast):
    tcv = TypeCheckVisitor()
    tcv.visit(ast)
