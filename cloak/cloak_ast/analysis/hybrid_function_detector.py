from cloak.type_check.type_exceptions import TypeException
from cloak.cloak_ast.ast import FunctionPrivacyType, FunctionTypeName, ReclassifyExpr, ConstructorOrFunctionDefinition, AllExpr, FunctionCallExpr, LocationExpr, BuiltinFunction, \
    PrimitiveCastExpr
from cloak.cloak_ast.visitor.function_visitor import FunctionVisitor
from cloak.type_check.type_setter import update_function_privacy_type


def detect_hybrid_functions(ast):
    """

    :param ast:
    :return: marks all functions which will require verification
    """
    v = DirectHybridFunctionDetectionVisitor()
    v.visit(ast)

    v = IndirectHybridFunctionDetectionVisitor()
    v.visit(ast)

    v = NonInlineableCallDetector()
    v.visit(ast)


class DirectHybridFunctionDetectionVisitor(FunctionVisitor):
    def visitReclassifyExpr(self, ast: ReclassifyExpr):
        update_function_privacy_type(ast.statement.function, FunctionPrivacyType.ZKP)


    def visitPrimitiveCastExpr(self, ast: PrimitiveCastExpr):
        if ast.expr.evaluate_privately:
            update_function_privacy_type(ast.statement.function, FunctionPrivacyType.ZKP)
        else:
            self.visitChildren(ast)

    def visitAllExpr(self, ast: AllExpr):
        pass

    def visitFunctionCallExpr(self, ast: FunctionCallExpr):
        if isinstance(ast.func, BuiltinFunction) and ast.func.is_private:
            update_function_privacy_type(ast.statement.function, FunctionPrivacyType.ZKP)
        elif ast.is_cast and ast.evaluate_privately:
            update_function_privacy_type(ast.statement.function, FunctionPrivacyType.ZKP)
        else:
            self.visitChildren(ast)

    def visitConstructorOrFunctionDefinition(self, ast: ConstructorOrFunctionDefinition):
        self.visit(ast.body)

        if ast.can_be_external:
            if ast.requires_verification:
                ast.requires_verification_when_external = True
            else:
                for param in ast.parameters:
                    if param.annotated_type.is_private():
                        ast.requires_verification_when_external = True
                        break


class IndirectHybridFunctionDetectionVisitor(FunctionVisitor):
    def visitConstructorOrFunctionDefinition(self, ast: ConstructorOrFunctionDefinition):
        if not ast.is_tee():
            for fct in ast.called_functions:
                if not fct.is_pub():
                    update_function_privacy_type(ast, fct.privacy_type)
                    # renqian TODO: adapt requires_verification_when_external to TEE
                    if ast.can_be_external:
                        ast.requires_verification_when_external = True
                    if ast.is_tee():
                        break


class NonInlineableCallDetector(FunctionVisitor):
    def visitFunctionCallExpr(self, ast: FunctionCallExpr):
        if not ast.is_cast and isinstance(ast.func, LocationExpr):
            if ast.func.target.requires_verification and ast.func.target.is_recursive:
                raise TypeException("Non-inlineable call to recursive private function", ast.func)
        self.visitChildren(ast)
