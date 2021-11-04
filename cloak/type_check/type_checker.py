import json
from cloak.utils.helpers import save_to_file

from cloak.type_check.contains_private import contains_private
from cloak.type_check.final_checker import check_final
from cloak.type_check.type_exceptions import TypeMismatchException, TypeException
from cloak.cloak_ast.ast import CodeVisitor, IdentifierExpr, ReturnStatement, IfStatement, AnnotatedTypeName, Expression, TeeExpr, TypeName, \
    StateVariableDeclaration, Mapping, AssignmentStatement, MeExpr, ReclassifyExpr, FunctionCallExpr, \
    BuiltinFunction, VariableDeclarationStatement, RequireStatement, MemberAccessExpr, TupleType, IndexExpr, Array, \
    LocationExpr, NewExpr, TupleExpr, ConstructorOrFunctionDefinition, WhileStatement, ForStatement, NumberLiteralType, \
    BooleanLiteralType, EnumValue, EnumTypeName, EnumDefinition, EnumValueTypeName, PrimitiveCastExpr, FunctionPrivacyType, \
    UserDefinedTypeName, get_privacy_expr_from_label, issue_compiler_warning, AllExpr, ContractDefinition, VariableDeclaration
from cloak.cloak_ast.visitor.deep_copy import replace_expr
from cloak.cloak_ast.visitor.visitor import AstVisitor
from cloak.policy.privacy_policy import FunctionPolicy, PrivacyPolicy, FUNC_INPUTS, FUNC_READ, FUNC_MUTATE, FUNC_OUTPUTS


def check_type(ast):
    check_final(ast)

    v = TypeCheckVisitor()
    v.visit(ast)

class TypeCheckVisitor(AstVisitor):

    def get_rhs(self, rhs: Expression, expected_type: AnnotatedTypeName):
        if isinstance(rhs, TupleExpr):
            if not isinstance(rhs, TupleExpr) or not isinstance(expected_type.type_name, TupleType) or len(rhs.elements) != len(expected_type.type_name.types):
                raise TypeMismatchException(expected_type, rhs.annotated_type, rhs)
            exprs = [self.get_rhs(a, e) for e, a, in zip(expected_type.type_name.types, rhs.elements)]
            return replace_expr(rhs, TupleExpr(exprs)).as_type(TupleType([e.annotated_type for e in exprs]))

        instance = rhs.instanceof(expected_type)
        if not instance and not rhs.get_related_function().is_tee():
            raise TypeMismatchException(expected_type, rhs.annotated_type, rhs)
        else:
            if rhs.annotated_type.type_name != expected_type.type_name:
                rhs = self.implicitly_converted_to(rhs, expected_type.type_name)

            if instance == 'make-private':
                return self.make_private(rhs, expected_type.privacy_annotation)
            else:
                return rhs

    @staticmethod
    def check_for_invalid_private_type(ast):
        assert hasattr(ast, 'annotated_type')
        at = ast.annotated_type
        if at.is_private() and not at.type_name.can_be_private():
            raise TypeException(f"Type {at.type_name} cannot be private", ast.annotated_type)

    def check_final(self, fct: ConstructorOrFunctionDefinition, ast: Expression):
        if isinstance(ast, IdentifierExpr):
            target = ast.target
            if hasattr(target, 'keywords'):
                if 'final' in target.keywords:
                    if isinstance(target, StateVariableDeclaration) and fct.is_constructor:
                        # assignment allowed
                        pass
                    else:
                        raise TypeException('Modifying "final" variable', ast)
        else:
            assert isinstance(ast, TupleExpr)
            for elem in ast.elements:
                self.check_final(fct, elem)

    def visitAssignmentStatement(self, ast: AssignmentStatement):
        # NB TODO? Should we optionally disallow writes to variables which are owned by someone else (with e.g. a new modifier)
        #if ast.lhs.annotated_type.is_private():
        #    expected_rhs_type = AnnotatedTypeName(ast.lhs.annotated_type.type_name, Expression.me_expr())
        #    if not ast.lhs.instanceof(expected_rhs_type):
        #        raise TypeException("Only owner can assign to its private variables", ast)

        if not isinstance(ast.lhs, (TupleExpr, LocationExpr)):
            raise TypeException("Assignment target is not a location", ast.lhs)

        expected_type = ast.lhs.annotated_type
        ast.rhs = self.get_rhs(ast.rhs, expected_type)

        # prevent modifying final
        f = ast.function
        if isinstance(ast.lhs, (IdentifierExpr, TupleExpr)):
            self.check_final(f, ast.lhs)

    def visitVariableDeclarationStatement(self, ast: VariableDeclarationStatement):
        if ast.expr:
            ast.expr = self.get_rhs(ast.expr, ast.variable_declaration.annotated_type)

    def make_private_if_not_already(self, ast: Expression):
        if ast.annotated_type.is_private():
            expected = AnnotatedTypeName(ast.annotated_type.type_name, Expression.me_expr())
            if not ast.instanceof(expected) and not ast.get_related_function().is_tee():
                raise TypeMismatchException(expected, ast.annotated_type, ast)
            return ast
        else:
            return self.make_private(ast, Expression.me_expr())

    @staticmethod
    def has_private_type(ast: Expression):
        return ast.annotated_type.is_private()

    @staticmethod
    def has_literal_type(ast: Expression):
        return isinstance(ast.annotated_type.type_name, (NumberLiteralType, BooleanLiteralType))

    def handle_builtin_function_call(self, ast: FunctionCallExpr, func: BuiltinFunction):
        # handle special cases
        if func.is_ite() or func.is_parenthesis():
            return

        t1 = ast.args[0].annotated_type.type_name
        t2 = None if len(ast.args) == 1 else ast.args[1].annotated_type.type_name

        if len(ast.args) == 1:
            arg_t = 'lit' if ast.args[0].annotated_type.type_name.is_literal else t1
        else:
            assert len(ast.args) == 2
            is_eq_with_tuples = func.is_eq() and isinstance(t1, TupleType)
            arg_t = t1.combined_type(t2, convert_literals=is_eq_with_tuples)

        # Infer argument and output types
        if arg_t == 'lit':
            res = func.op_func(*[arg.annotated_type.type_name.value for arg in ast.args])
            if isinstance(res, bool):
                assert func.output_type() == TypeName.bool_type()
                out_t = BooleanLiteralType(res)
            else:
                assert func.output_type() == TypeName.number_type()
                out_t = NumberLiteralType(res)
            if func.is_eq():
                arg_t = t1.to_abstract_type().combined_type(t2.to_abstract_type(), True)
        elif func.output_type() == TypeName.bool_type():
            out_t = TypeName.bool_type()
        else:
            out_t = arg_t

        assert arg_t is not None and (arg_t != 'lit' or not func.is_eq())

        private_args = any(map(self.has_private_type, ast.args))
        if private_args:
            assert arg_t != 'lit'
            if func.statement.function.is_zkp():
                # ZKP
                if func.can_be_private():
                    if func.is_shiftop():
                        if not ast.args[1].annotated_type.type_name.is_literal:
                            raise TypeException('Private shift expressions must use a constant (literal) shift amount', ast.args[1])
                        if ast.args[1].annotated_type.type_name.value < 0:
                            raise TypeException('Cannot shift by negative amount', ast.args[1])
                    if func.is_bitop() or func.is_shiftop():
                        for arg in ast.args:
                            if arg.annotated_type.type_name.elem_bitwidth == 256:
                                raise TypeException('Private bitwise and shift operations are only supported for integer types < 256 bit, '
                                                    'please use a smaller type', arg)

                    if func.is_arithmetic():
                        for a in ast.args:
                            if a.annotated_type.type_name.elem_bitwidth == 256:
                                issue_compiler_warning(func, 'Possible field prime overflow',
                                                            'Private arithmetic 256bit operations overflow at FIELD_PRIME.\n'
                                                            'If you need correct overflow behavior, use a smaller integer type.')
                                break
                    elif func.is_comp():
                        for a in ast.args:
                            if a.annotated_type.type_name.elem_bitwidth == 256:
                                issue_compiler_warning(func, 'Possible private comparison failure',
                                                            'Private 256bit comparison operations will fail for values >= 2^252.\n'
                                                            'If you cannot guarantee that the value stays in range, you must use '
                                                            'a smaller integer type to ensure correctness.')
                                break
                    func.is_private = True
                    p = Expression.me_expr()
                else:
                    raise TypeException(f'Operation \'{func.op}\' does not support private operands', ast)
            else:
                # TEE
                p = None
                pass
        else:
            p = None
            pass

        if arg_t != 'lit':
            # Add implicit casts for arguments
            arg_pt = arg_t.annotate(p)
            if func.is_shiftop() and p is not None:
                ast.args[0] = self.get_rhs(ast.args[0], arg_pt)
            else:
                ast.args[:] = map(lambda argument: self.get_rhs(argument, arg_pt), ast.args)

        ast.annotated_type = out_t.annotate(p)


    @staticmethod
    def is_accessible_by_invoker(ast: Expression):
        return True
        #return ast.annotated_type.is_public() or ast.is_lvalue() or \
        #       ast.instanceof(AnnotatedTypeName(ast.annotated_type.type_name, Expression.me_expr()))

    @staticmethod
    def make_private(expr: Expression, privacy: Expression):
        assert (privacy.privacy_annotation_label() is not None)

        pl = get_privacy_expr_from_label(privacy.privacy_annotation_label())
        r = ReclassifyExpr(expr, pl)

        # set type
        r.annotated_type = AnnotatedTypeName(expr.annotated_type.type_name, pl.clone())
        TypeCheckVisitor.check_for_invalid_private_type(r)

        # set statement
        r.statement = expr.statement

        # set parents
        r.parent = expr.parent
        r.annotated_type.parent = r
        expr.parent = r

        # set source location
        r.line = expr.line
        r.column = expr.column

        return r

    @staticmethod
    def implicitly_converted_to(expr: Expression, t: TypeName) -> Expression:
        assert expr.annotated_type.type_name.is_primitive_type()
        cast = PrimitiveCastExpr(t.clone(), expr, is_implicit=True).override(
            parent=expr.parent, statement=expr.statement, line=expr.line, column=expr.column)
        cast.elem_type.parent = cast
        expr.parent = cast
        cast.annotated_type = AnnotatedTypeName(t.clone(), expr.annotated_type.privacy_annotation.clone()).override(parent=cast)
        return cast

    def visitFunctionCallExpr(self, ast: FunctionCallExpr):
        if isinstance(ast.func, BuiltinFunction):
            self.handle_builtin_function_call(ast, ast.func)
        elif ast.is_cast:
            if not isinstance(ast.func.target, EnumDefinition):
                raise NotImplementedError('User type casts only implemented for enums')
            ast.annotated_type = self.handle_cast(ast.args[0], ast.func.target.annotated_type.type_name)
        elif isinstance(ast.func, LocationExpr):
            ft = ast.func.annotated_type.type_name

            if len(ft.parameters) != len(ast.args):
                raise TypeException("Wrong number of arguments", ast.func)

            # Check arguments
            for i in range(len(ast.args)):
                ast.args[i] = self.get_rhs(ast.args[i], ft.parameters[i].annotated_type)

            # Set expression type to return type
            if len(ft.return_parameters) == 1:
                ast.annotated_type = ft.return_parameters[0].annotated_type.clone()
            else:
                # TODO maybe not None label in the future
                ast.annotated_type = AnnotatedTypeName(TupleType([t.annotated_type for t in ft.return_parameters]), None)
        else:
            raise TypeException('Invalid function call', ast)

    def visitPrimitiveCastExpr(self, ast: PrimitiveCastExpr):
        ast.annotated_type = self.handle_cast(ast.expr, ast.elem_type)

    def handle_cast(self, expr: Expression, t: TypeName) -> AnnotatedTypeName:
        # because of the fake solidity check we already know that the cast is possible -> don't have to check if cast possible
        if expr.annotated_type.is_private():
            expected = AnnotatedTypeName(expr.annotated_type.type_name, Expression.me_expr())
            if not expr.instanceof(expected) and not expr.get_related_function().is_tee():
                raise TypeMismatchException(expected, expr.annotated_type, expr)
            return AnnotatedTypeName(t.clone(), Expression.me_expr())
        else:
            return AnnotatedTypeName(t.clone())

    def visitNewExpr(self, ast: NewExpr):
        # already has correct type
        pass

    def visitMemberAccessExpr(self, ast: MemberAccessExpr):
        assert ast.target is not None
        if ast.expr.annotated_type.is_address() and ast.expr.annotated_type.is_private():
            raise TypeException("Cannot access members of private address variable", ast)
        ast.annotated_type = ast.target.annotated_type.clone()

    def visitReclassifyExpr(self, ast: ReclassifyExpr):
        if not ast.privacy.privacy_annotation_label():
            raise TypeException('Second argument of "reveal" cannot be used as a privacy type', ast)

        # NB prevent any redundant reveal (not just for public)
        ast.annotated_type = AnnotatedTypeName(ast.expr.annotated_type.type_name, ast.privacy)
        if ast.instanceof(ast.expr.annotated_type) is True:
            raise TypeException(f'Redundant "reveal": Expression is already "@{ast.privacy.code()}"', ast)
        self.check_for_invalid_private_type(ast)

    def visitIfStatement(self, ast: IfStatement):
        b = ast.condition
        if not b.instanceof_data_type(TypeName.bool_type()) and not ast.get_related_function().is_tee():
            raise TypeMismatchException(TypeName.bool_type(), b.annotated_type.type_name, b)
        if ast.condition.annotated_type.is_private():
            expected = AnnotatedTypeName(TypeName.bool_type(), Expression.me_expr())
            if not b.instanceof(expected) and not ast.get_related_function().is_tee():
                raise TypeMismatchException(expected, b.annotated_type, b)

    def visitWhileStatement(self, ast: WhileStatement):
        if not ast.condition.instanceof(AnnotatedTypeName.bool_all()) and not ast.get_related_function().is_tee():
            raise TypeMismatchException(AnnotatedTypeName.bool_all(), ast.condition.annotated_type, ast.condition)
        # must also later check that body and condition do not contain private expressions

    def visitForStatement(self, ast: ForStatement):
        if not ast.condition.instanceof(AnnotatedTypeName.bool_all()) and not ast.get_related_function().is_tee():
            raise TypeMismatchException(AnnotatedTypeName.bool_all(), ast.condition.annotated_type, ast.condition)
        # must also later check that body, update and condition do not contain private expressions

    def visitReturnStatement(self, ast: ReturnStatement):
        assert ast.function.is_function
        rt = AnnotatedTypeName(ast.function.return_type)
        if ast.expr is None:
            self.get_rhs(TupleExpr([]), rt)
        elif not isinstance(ast.expr, TupleExpr):
            ast.expr = self.get_rhs(TupleExpr([ast.expr]), rt)
        else:
            ast.expr = self.get_rhs(ast.expr, rt)

    def visitTupleExpr(self, ast: TupleExpr):
        ast.annotated_type = AnnotatedTypeName(TupleType([elem.annotated_type.clone() for elem in ast.elements]))

    def visitMeExpr(self, ast: MeExpr):
        ast.annotated_type = AnnotatedTypeName.address_all()

    def visitTeeExpr(self, ast: TeeExpr):
        ast.annotated_type = AnnotatedTypeName.address_all()

    def visitIdentifierExpr(self, ast: IdentifierExpr):
        if isinstance(ast.target, Mapping):
            # no action necessary, the identifier will be replaced later
            pass
        else:
            target = ast.target
            if isinstance(target, ContractDefinition):
                raise TypeException(f'Unsupported use of contract type in expression', ast)
            ast.annotated_type = target.annotated_type.clone()

            if not self.is_accessible_by_invoker(ast):
                raise TypeException("Tried to read value which cannot be proven to be owned by the transaction invoker", ast)

    def visitIndexExpr(self, ast: IndexExpr):
        arr = ast.arr
        index = ast.key

        map_t = arr.annotated_type
        # should have already been checked
        # assert (map_t.privacy_annotation.is_all_expr())

        # do actual type checking
        if isinstance(map_t.type_name, Mapping):
            key_type = map_t.type_name.key_type
            expected = AnnotatedTypeName(key_type, Expression.all_expr())
            instance = index.instanceof(expected)
            if not instance and not ast.get_related_function().is_tee():
                raise TypeMismatchException(expected, index.annotated_type, ast)

            # record indexing information
            if map_t.type_name.key_label is not None: # TODO modification correct?
                if index.privacy_annotation_label():
                    map_t.type_name.instantiated_key = index
                else:
                    raise TypeException(f'Index cannot be used as a privacy type for array of type {map_t}', ast)

            # determine value type
            ast.annotated_type = map_t.type_name.value_type

            if not self.is_accessible_by_invoker(ast):
                raise TypeException("Tried to read value which cannot be proven to be owned by the transaction invoker", ast)
        elif isinstance(map_t.type_name, Array):
            if ast.key.annotated_type.is_private():
                raise TypeException('No private array index', ast)
            if not ast.key.instanceof_data_type(TypeName.number_type()):
                raise TypeException('Array index must be numeric', ast)
            # ast.annotated_type = map_t.type_name.annotate(map_t.privacy_annotation)
            ast.annotated_type = map_t.type_name.value_type.annotate(map_t.privacy_annotation)
        else:
            raise TypeException('Indexing into non-mapping', ast)

    # def visitConstructorOrFunctionDefinition(self, ast: ConstructorOrFunctionDefinition):
    #     for t in ast.parameter_types:
    #         if not isinstance(t.privacy_annotation, (MeExpr, AllExpr, TeeExpr)):
    #             raise TypeException('Only me/tee/all accepted as privacy type of function parameters', ast)

    #     if ast.can_be_external:
    #         for t in ast.return_type:
    #             if not isinstance(t.privacy_annotation, (MeExpr, AllExpr, TeeExpr)):
    #                 raise TypeException('Only me/tee/all accepted as privacy type of return values for public functions', ast)

    def visitEnumDefinition(self, ast: EnumDefinition):
        ast.annotated_type = AnnotatedTypeName(EnumTypeName(ast.qualified_name).override(target=ast))

    def visitEnumValue(self, ast: EnumValue):
        ast.annotated_type = AnnotatedTypeName(EnumValueTypeName(ast.qualified_name).override(target=ast))

    def visitStateVariableDeclaration(self, ast: StateVariableDeclaration):
        if ast.expr:
            # prevent private operations in declaration
            if contains_private(ast):
                raise TypeException('Private assignments to state variables must be in the constructor', ast)

            # check type
            self.get_rhs(ast.expr, ast.annotated_type)

        # prevent "me" annotation
        p = ast.annotated_type.privacy_annotation
        if p.is_me_expr():
            raise TypeException(f'State variables cannot be annotated as me', ast)

    def visitMapping(self, ast: Mapping):
        if ast.key_label is not None:
            if ast.key_type != TypeName.address_type():
                raise TypeException(f'Only addresses can be annotated', ast)

    def visitRequireStatement(self, ast: RequireStatement):
        if not ast.condition.annotated_type.privacy_annotation.is_all_expr():
            raise TypeException(f'require needs public argument', ast)

    def visitAnnotatedTypeName(self, ast: AnnotatedTypeName):
        if type(ast.type_name) == UserDefinedTypeName:
            if not isinstance(ast.type_name.target, EnumDefinition):
                raise TypeException('Unsupported use of user-defined type', ast.type_name)
            ast.type_name = ast.type_name.target.annotated_type.type_name.clone()

        # if ast.privacy_annotation != Expression.all_expr():
        #     if not ast.type_name.can_be_private():
        #         raise TypeException(f'Currently, we do not support private {str(ast.type_name)}', ast)

        p = ast.privacy_annotation
        if isinstance(p, IdentifierExpr):
            t = p.target
            if isinstance(t, Mapping):
                # no action necessary, this is the case: mapping(address!x => uint@x)
                pass
            elif not t.is_final and not t.is_constant:
                raise TypeException('Privacy annotations must be "final" or "constant", if they are expressions', p)
            elif t.annotated_type != AnnotatedTypeName.address_all():
                raise TypeException(f'Privacy type is not a public address, but {str(t.annotated_type)}', p)
