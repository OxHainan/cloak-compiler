from typing import Union

from zkay_ast.ast import AST, ContractDefinition, Identifier, IdentifierExpr, ReturnStatement, IfStatement, \
	AssignmentExpr, BooleanLiteralExpr, NumberLiteralExpr, AnnotatedTypeName, Expression, TypeName, \
	FunctionDefinition, FunctionPrivacyType, StateVariableDeclaration, Mapping, ConstructorOrFunctionDefinition, \
	AssignmentStatement, MeExpr, AllExpr, TeeExpr, ConstructorDefinition, ReclassifyExpr, FunctionCallExpr, \
	BuiltinFunction, VariableDeclarationStatement, RequireStatement
from zkay_ast.visitor.deep_copy import deep_copy
from zkay_ast.visitor.visitor import AstVisitor
from type_check.contains_private import contains_private
from type_check.final_checker import check_final
from type_check.type_exceptions import TypeMismatchException, TypeException


def privacy_type_set(ast):
	'''
	Derive and set the privacy type of the function
	'''
	check_final(ast)
	v = PrivacyTypeVisitor()
	v.visit(ast)


def is_prior_to(p_type_1: FunctionPrivacyType, p_type_2: FunctionPrivacyType):
	# if the privacy type of p_type_2 is prior to p_type_2
	if p_type_2 != p_type_1:
		# TEE > MPC > ZKP > PUB
		if p_type_2 == FunctionPrivacyType.TEE:
			return False
		elif p_type_2 == FunctionPrivacyType.PUB:
			return True
		elif p_type_2 == FunctionPrivacyType.MPC and p_type_1 == FunctionPrivacyType.TEE:
			return True
		elif p_type_2 == FunctionPrivacyType.ZKP and (p_type_1 == FunctionPrivacyType.MPC or p_type_1 == FunctionPrivacyType.TEE):
			return True
	else:
		return False

class PrivacyTypeVisitor(AstVisitor):

	def __init__(self, traversal='post', log=False):
	 super().__init__(traversal=traversal, log=log)

	def set_function_privacy_type(self, expect_type: AnnotatedTypeName, rhs: Expression, ast: AST, instance=None, isReclasify=False):
		'''
		Set the privacy type of the function by new information. this is the main goal of the PrivacyTypeVisiter.
		'''
		actual_type = rhs.annotated_type
		_new_ast = deep_copy(ast)

		ast = ast.get_related_function()

		def update_function_privacy_type(ast: ConstructorOrFunctionDefinition, privacy_type: FunctionPrivacyType):
			if is_prior_to(privacy_type, ast.privacy_type):
				ast.privacy_type = privacy_type
				if privacy_type == FunctionPrivacyType.PUB:
					print(f'function {ast.name} is setted to \'PUB\'')
				elif privacy_type == FunctionPrivacyType.ZKP:
					print(f'function {ast.name} is setted to \'ZKP\', because actual {str(actual_type)} is a instance of expected {str(expect_type)} in statement: {_new_ast}')
				elif privacy_type == FunctionPrivacyType.TEE:
					print(f'function {ast.name} is setted to \'TEE\', because actual {str(actual_type)} is a instance of Tee in statement: {_new_ast}')
				elif privacy_type == FunctionPrivacyType.MPC:
					print(f'function {ast.name} is setted to \'MPC\', because actual {str(actual_type)} is incompatibale with expected {str(expect_type)} in statement: {_new_ast}')
				else:
					TypeException(f"Unknown function privacy type: {privacy_type}")


		if isinstance(ast, ConstructorOrFunctionDefinition):
			act_pri = actual_type.privacy_annotation
			exp_pri = expect_type.privacy_annotation			

			if isReclasify:
				update_function_privacy_type(ast, FunctionPrivacyType.ZKP)
			else:
				if not instance: 
					# act_pri != AllExpr and exp_pri != AllExpr
					if isinstance(act_pri, TeeExpr):
						update_function_privacy_type(ast, FunctionPrivacyType.TEE)
					else:
						update_function_privacy_type(ast, FunctionPrivacyType.MPC)
				elif instance == 'make-private':  
					# act_pri == AllExpr and exp_pri != AllExpr
					update_function_privacy_type(ast, FunctionPrivacyType.ZKP)
				elif instance: 
					# act_pri == exp_pri
					if isinstance(act_pri, TeeExpr): 
						# both private to tee.
						update_function_privacy_type(ast, FunctionPrivacyType.TEE)
					elif not isinstance(act_pri, AllExpr): 
						# both private to id, me
						update_function_privacy_type(ast, FunctionPrivacyType.ZKP)
					else: 
						# both AllExpr
						pass
				else:
					TypeException(f"Invalid instance. instance should be True, False or 'make-private, while it is actually {instance}'")

	def get_right_hand_statement(self, rhs: Expression, expected_type: AnnotatedTypeName):
		'''
		get right hand statement
		'''
		instance = rhs.instanceof(expected_type)
		self.set_function_privacy_type(expected_type, rhs, rhs.parent, instance)
		if instance == 'make-private':
			return self.make_private(rhs, expected_type.privacy_annotation)
		else:
			return rhs

	def visitAssignmentStatement(self, ast: AssignmentStatement):
		expected_type = ast.lhs.annotated_type
		ast.rhs = self.get_right_hand_statement(ast.rhs, expected_type)
		ast.annotated_type = expected_type

	def visitVariableDeclarationStatement(self, ast: VariableDeclarationStatement):
		if ast.expr:
			ast.expr = self.get_right_hand_statement(ast.expr, ast.variable_declaration.annotated_type)

	def handle_builtin_function_call(self, ast: FunctionCallExpr, func: BuiltinFunction, private=False):
		# set parameter type, like [uint, uint]
		parameter_types = func.input_types()
		# set output type
		if private:
			p = Expression.me_expr()
		else:
			p = Expression.all_expr()
		output_type = AnnotatedTypeName(ast.func.output_type(), p)
		# can function be evaluated privately?
		can_be_private = func.can_be_private()

		# handle special cases
		if func.is_eq():
			# handle eq
			t = ast.args[0].annotated_type.type_name
			parameter_types = 2*[t]
			can_be_private = t == TypeName.uint_type() or t == TypeName.bool_type()
		elif func.is_index():
			return self.handle_index(ast)
		elif func.is_parenthesis():
			ast.annotated_type = ast.args[0].annotated_type
			return
		elif func.is_ite():
			t = ast.args[1].annotated_type.type_name
			if t == TypeName.uint_type() or t == TypeName.bool_type():
				can_be_private = True

			parameter_types = [TypeName.bool_type(), t, t]
			output_type = AnnotatedTypeName(t, p)

		for i in range(len(parameter_types)):
			t = parameter_types[i]
			arg = ast.args[i]
			expected = AnnotatedTypeName(t, p)
			instance = arg.instanceof(expected)
			if not instance:
				if can_be_private and not private:
					func.is_private = True
					return self.handle_builtin_function_call(ast, func, True)
				else:
					self.set_function_privacy_type(expected, arg, ast, instance)
			elif instance == 'make-private':
				# replace argument
				self.set_function_privacy_type(expected, arg, ast, instance)
				ast.args[i] = self.make_private(arg, Expression.me_expr())
			else:
				# no action necessary
				pass

		assert(output_type is not None)
		assert(isinstance(output_type, AnnotatedTypeName))
		ast.annotated_type = output_type

	def handle_index(self, ast: FunctionCallExpr):
		map_t = ast.args[0].annotated_type
		map_t.type_name.instantiated_key = ast.args[1]
		ast.annotated_type = map_t.type_name.value_type


	@staticmethod
	def make_private(expr: Expression, privacy: Expression):
		assert(privacy.privacy_annotation_label() is not None)

		if expr.get_related_function().privacy_type == FunctionPrivacyType.TEE:
			return expr
		else:
			_new_recla_expr = ReclassifyExpr(expr, privacy.privacy_annotation_label())

			# set type
			_new_recla_expr.annotated_type = AnnotatedTypeName(expr.annotated_type.type_name, privacy)

			# set statement
			_new_recla_expr.statement = expr.statement

			# set parents
			_new_recla_expr.parent = expr.parent
			_new_recla_expr.annotated_type.parent = _new_recla_expr
			expr.parent = _new_recla_expr

			return _new_recla_expr

	def visitFunctionCallExpr(self, ast: FunctionCallExpr):
		if isinstance(ast.func, BuiltinFunction):
			self.handle_builtin_function_call(ast, ast.func)

	def visitReclassifyExpr(self, ast: ReclassifyExpr):
		ast.annotated_type = AnnotatedTypeName(ast.expr.annotated_type.type_name, ast.privacy)
		self.set_function_privacy_type(ast.annotated_type, ast.expr, ast, None, True)

	def visitReturnStatement(self, ast: ReturnStatement):
		f = ast.parent
		while not isinstance(f, FunctionDefinition):
			f = f.parent

		assert(isinstance(f, FunctionDefinition))
		expected_types = f.get_return_type()

		instance = ast.expr.instanceof(expected_types)
		if instance == 'make-private':
			ast.expr = self.make_private(ast.expr, expected_types.privacy_annotation)

	def visitBooleanLiteralExpr(self, ast: BooleanLiteralExpr):
		ast.annotated_type = AnnotatedTypeName.bool_all()

	def visitNumberLiteralExpr(self, ast: NumberLiteralExpr):
		ast.annotated_type = AnnotatedTypeName.uint_all()

	def visitMeExpr(self, ast: MeExpr):
		ast.annotated_type = AnnotatedTypeName.address_all()

	def visitTeeExpr(self, ast: TeeExpr):
		ast.annotated_type = AnnotatedTypeName.address_all()

	def visitIdentifierExpr(self, ast: IdentifierExpr):
		if isinstance(ast.target, Mapping):
			# no action necessary, the identifier will be replaced later
			pass
		else:
			ast.annotated_type = deep_copy(ast.target.annotated_type)

	def visitFunctionDefinition(self, ast: FunctionDefinition):
		print(f'Function {ast.name} setted...')

	def visitConstructorDefinition(self, ast: ConstructorDefinition):
		print(f'Function {ast.name} setted...')