from cloak.type_check.contains_private import contains_private
from cloak.type_check.final_checker import check_final
from cloak.type_check.type_exceptions import TypeMismatchException, TypeException
from cloak.cloak_ast.ast import AST, CodeVisitor, IdentifierExpr, ReturnStatement, IfStatement, AnnotatedTypeName, Expression, TypeName, \
    StateVariableDeclaration, Mapping, AssignmentStatement, MeExpr, TeeExpr, ReclassifyExpr, FunctionCallExpr, \
    BuiltinFunction, VariableDeclarationStatement, RequireStatement, MemberAccessExpr, TupleType, IndexExpr, Array, \
    LocationExpr, NewExpr, TupleExpr, ConstructorOrFunctionDefinition, WhileStatement, ForStatement, NumberLiteralType, \
    BooleanLiteralType, EnumValue, EnumTypeName, EnumDefinition, EnumValueTypeName, PrimitiveCastExpr, \
    UserDefinedTypeName, get_privacy_expr_from_label, issue_compiler_warning, AllExpr, ContractDefinition, FunctionPrivacyType
from cloak.cloak_ast.visitor.deep_copy import deep_copy, replace_expr
from cloak.cloak_ast.visitor.visitor import AstVisitor

def set_type(ast):
    check_final(ast)

    # set function type
    fv = FunctionTypeVisitor() 
    fv.visit(ast)

def update_function_privacy_type(_func: ConstructorOrFunctionDefinition, privacy_type: FunctionPrivacyType, actual_type=None, expect_type=None, ast=None):
    privacy_type = FunctionPrivacyType.MPC
    if FunctionTypeVisitor.is_prior_to(privacy_type, _func.privacy_type):
        _func.privacy_type = privacy_type
        if privacy_type == FunctionPrivacyType.PUB:
            print(f'function {_func.name} is setted to \'PUB\'')
        elif privacy_type == FunctionPrivacyType.ZKP:
            if actual_type and expect_type:
                print(
                    f'function {_func.name} is setted to \'CT\', because actual {str(actual_type)} is a instance of expected {str(expect_type)} in statement: {ast}')
            else:
                print(f'function {_func.name} is setted to \'CT\'')
        elif privacy_type == FunctionPrivacyType.TEE:
            _func.get_related_contract().is_tee_related = True
            if actual_type and ast:
                print(
                    f'function {_func.name} is setted to \'MPT\', because actual {str(actual_type)} is a instance of Tee in statement: {ast}')
            else:
                print(f'function {_func.name} is setted to \'MPT\'')
        elif privacy_type == FunctionPrivacyType.MPC:
            # renqian TODO: temperoly replace MPC with TEE, restore it in the future.
            # print(f'function {_func.name} is setted to \'MPC\', because actual {str(actual_type)} is incompatibale with expected {str(expect_type)} in statement: {ast}')
            _func.privacy_type = FunctionPrivacyType.TEE
            _func.get_related_contract().is_tee_related = True
            if actual_type and expect_type and ast:
                print(f'function {_func.name} is setted to \'MPT\', because actual {str(actual_type)} is incompatibale with expected {str(expect_type)} in statement: {ast}, thus being marked as MPT')
            else:
                print(f'function {_func.name} is setted to \'MPT\'')
        else:
            TypeException(
                f"Unknown function privacy type: {privacy_type}")


class FunctionTypeVisitor(AstVisitor):

    @staticmethod
    def is_prior_to(privacy_type_1: FunctionPrivacyType, privacy_type_2: FunctionPrivacyType):
        """
        Is the function privacy type privacy_type_1 is prior to privacy_type_2

        'T1 is prior to T2' means a function recoginized as T2 has chance to upgrade to T1 
        while the composite is not allowed.
        """
        if privacy_type_2 != privacy_type_1:
            # TEE == MPC > ZKP > PUB
            if privacy_type_2 == FunctionPrivacyType.TEE:
                return False
            elif privacy_type_2 == FunctionPrivacyType.PUB:
                return True
            elif privacy_type_2 == FunctionPrivacyType.MPC and privacy_type_1 == FunctionPrivacyType.TEE:
                return True
            elif privacy_type_2 == FunctionPrivacyType.ZKP and (privacy_type_1 == FunctionPrivacyType.MPC or privacy_type_1 == FunctionPrivacyType.TEE):
                return True
        else:
            return False

    @staticmethod
    def set_function_privacy_type(expect_type: AnnotatedTypeName, rhs: Expression, ast: AST, instance=None, isReclasify=False):
        """
        Set the privacy type of the function by new information.
        """
        actual_type = rhs.annotated_type

        _func = ast.get_related_function()

        if isinstance(_func, ConstructorOrFunctionDefinition):
            act_pri = actual_type.privacy_annotation
            exp_pri = expect_type.privacy_annotation

            if isReclasify:
                update_function_privacy_type(_func, FunctionPrivacyType.ZKP, actual_type, expect_type, ast)
            else:
                if not instance:
                    # act_pri != AllExpr and exp_pri != AllExpr
                    if isinstance(act_pri, TeeExpr):
                        update_function_privacy_type(
                            _func, FunctionPrivacyType.TEE, actual_type, expect_type, ast)
                    else:
                        update_function_privacy_type(
                            _func, FunctionPrivacyType.MPC, actual_type, expect_type, ast)
                elif instance == 'make-private':
                    # act_pri == AllExpr and exp_pri != AllExpr
                    update_function_privacy_type(_func, FunctionPrivacyType.ZKP, actual_type, expect_type, ast)
                elif instance:
                    # act_pri == exp_pri
                    if isinstance(act_pri, TeeExpr):
                        # both private to tee.
                        update_function_privacy_type(_func, FunctionPrivacyType.TEE, actual_type, expect_type, ast)
                    elif not isinstance(act_pri, AllExpr):
                        # both private to id, me
                        pass
                        # update_function_privacy_type(_func, FunctionPrivacyType.ZKP, actual_type, expect_type, ast)
                    else:
                        # both AllExpr, default to PUB
                        pass
                else:
                    TypeException(
                        f"Invalid instance. instance should be True, False or 'make-private, while it is actually {instance}'")

    def get_rhs(self, rhs: Expression, expected_type: AnnotatedTypeName):
        if isinstance(rhs, TupleExpr):
            if not isinstance(rhs, TupleExpr) or not isinstance(expected_type.type_name, TupleType) or len(rhs.elements) != len(expected_type.type_name.types):
                raise TypeMismatchException(expected_type, rhs.annotated_type, rhs)
            exprs = [self.get_rhs(a, e) for e, a, in zip(expected_type.type_name.types, rhs.elements)]
            return replace_expr(rhs, TupleExpr(exprs)).as_type(TupleType([e.annotated_type for e in exprs]))

        instance = rhs.instanceof(expected_type)
        self.set_function_privacy_type(
            expected_type, rhs, rhs.parent, instance)

        return rhs

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

    @staticmethod
    def has_private_type(ast: Expression):
        return ast.annotated_type.is_private()

    @staticmethod
    def has_literal_type(ast: Expression):
        return isinstance(ast.annotated_type.type_name, (NumberLiteralType, BooleanLiteralType))

    def handle_builtin_function_call(self, ast: FunctionCallExpr, func: BuiltinFunction):
        # handle special cases
        if func.is_ite():
            cond_t = ast.args[0].annotated_type

            # Ensure that condition is boolean
            if not cond_t.type_name.implicitly_convertible_to(TypeName.bool_type()):
                raise TypeMismatchException(TypeName.bool_type(), cond_t.type_name, ast.args[0])

            res_t = ast.args[1].annotated_type.type_name.combined_type(ast.args[2].annotated_type.type_name, True)

            if cond_t.is_private():
                # Everything is turned private
                func.is_private = True
                a = res_t.annotate(Expression.me_expr())
            else:
                p = ast.args[1].annotated_type.combined_privacy(ast.analysis, ast.args[2].annotated_type)
                a = res_t.annotate(p)
            ast.args[1] = self.get_rhs(ast.args[1], a)
            ast.args[2] = self.get_rhs(ast.args[2], a)

            ast.annotated_type = a
            return
        elif func.is_parenthesis():
            ast.annotated_type = ast.args[0].annotated_type
            return

        # Check that argument types conform to op signature
        parameter_types = func.input_types()
        if not func.is_eq():
            for arg, t in zip(ast.args, parameter_types):
                if not arg.instanceof_data_type(t):
                    raise TypeMismatchException(t, arg.annotated_type.type_name, arg)

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
            if func.can_be_private():
                func.is_private = True
                p = Expression.me_expr()
            else:
                func.is_private = True
                p = Expression.tee_expr()
                # raise TypeException(f'Operation \'{func.op}\' does not support private operands', ast)
        else:
            p = None

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
            # if not expr.instanceof(expected):
            #     raise TypeMismatchException(expected, expr.annotated_type, expr)
            return AnnotatedTypeName(t.clone(), Expression.me_expr())
        else:
            return AnnotatedTypeName(t.clone())

    def visitMemberAccessExpr(self, ast: MemberAccessExpr):
        assert ast.target is not None
        if ast.expr.annotated_type.is_address() and ast.expr.annotated_type.is_private():
            raise TypeException("Cannot access members of private address variable", ast)
        ast.annotated_type = ast.target.annotated_type.clone()

    def visitReclassifyExpr(self, ast: ReclassifyExpr):
        ast.annotated_type = AnnotatedTypeName(
            ast.expr.annotated_type.type_name, ast.privacy)

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
            # if not instance:
            #     raise TypeMismatchException(expected, index.annotated_type, ast)

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
            ast.annotated_type = map_t.type_name.value_type.annotate(map_t.privacy_annotation)
        else:
            raise TypeException('Indexing into non-mapping', ast)


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
