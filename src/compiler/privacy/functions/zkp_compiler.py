from compiler.privacy.functions.function_compiler import FunctionVisitor, WantBool
from zkay_ast.ast import AST, CodeVisitor, AnnotatedTypeName, MeExpr, TeeExpr, ReclassifyExpr, \
	FunctionDefinition, Parameter, RequireStatement, ExpressionStatement, SimpleStatement, \
	AssignmentStatement, Expression, Identifier, IdentifierExpr, indent, ReturnStatement, Mapping, \
	ConstructorDefinition, UserDefinedTypeName, ContractDefinition, StateVariableDeclaration, Block, \
	VariableDeclaration, VariableDeclarationStatement, TypeName, FunctionCallExpr, BuiltinFunction, BooleanLiteralExpr, \
	ConstructorOrFunctionDefinition, FunctionPrivacyType
from compiler.zokrates.proof_helper import ProofHelper, FromZok, ParameterCheck, FromSolidity
from compiler.zokrates.tags import tag, helper_tag, param_tag, tee_tag


class ZokratesVisitor(FunctionVisitor):

	def from_zok(self, ast: Expression):
		with WantBool(self, False):
			expr = self.visit(ast)

		is_all = ast.annotated_type.privacy_annotation.is_all_expr()

		# add to function parameter
		t, c = self.function_helper.get_next_precomputed_parameter(ast)

		# add to zokrates argument
		zok_argument = f'{t}[{c}]'
		self.proof_helper.zok_arguments += [zok_argument]
		if not is_all:
			pki = self.ensure_pki()
			owner = ast.annotated_type.privacy_annotation.privacy_annotation_label()
			self.proof_helper.zok_arguments += [f'{pki}.getPk({self.sol.visit(owner)})']

		# add to zokrates parameter
		zok_parameter = f'{t}{c}'
		self.proof_helper.add_public_param(zok_parameter, ast)

		# add to zokrates arguments & emit zokrates code
		if is_all:
			# add check
			self.proof_helper.statements += [f'{zok_parameter} == {expr}']
		else:
			# add to zokrates arguments
			randomness = self.proof_helper.add_randomness(zok_parameter)
			key = f'{zok_parameter}PK'
			self.proof_helper.add_public_param(key, ast)

			# add check
			self.proof_helper.statements += [
				f'field {zok_parameter}Dec = {expr}',
				f'{zok_parameter} == enc({zok_parameter}Dec, {randomness}, {key})'
			]

		# add to zokrates proof argument
		self.proof_helper.proof_arguments += [FromZok(ast)]

		# return expression in solidity
		return zok_argument


	# def check_proper_encryption(self, p: Parameter):
	# 	if p.annotated_type.privacy_annotation.is_all_expr():
	# 		# no check necessary
	# 		pass
	# 	else:
	# 		# prepare zokrates argument
	# 		t, c = self.function_helper.get_next_temporary_variable()
	# 		helper_var_name = f'{t}[{c}]'
	# 		self.sol.pre_simple_statement += [f'{helper_var_name} = {p.idf};']

	# 		# add to zokrates argument
	# 		pki = self.ensure_pki()
	# 		owner = p.annotated_type.privacy_annotation.privacy_annotation_label()
	# 		self.proof_helper.zok_arguments += [helper_var_name, f'{pki}.getPk({self.sol.visit(owner)})']

	# 		# add to zokrates parameter
	# 		parameter_var_name = f'{t}{c}'
	# 		self.proof_helper.add_public_param(parameter_var_name, p)
	# 		value = self.proof_helper.add_value(parameter_var_name)
	# 		randomness = self.proof_helper.add_randomness(parameter_var_name)
	# 		key = f'{parameter_var_name}PK'
	# 		self.proof_helper.add_public_param(key, p)

	# 		# add to zokrates code
	# 		self.proof_helper.statements += [f'{parameter_var_name} == enc({value}, {randomness}, {key})']

	# 		# add to zokrates proof argument
	# 		self.proof_helper.proof_arguments += [ParameterCheck(p)]


	def visitIdentifierExpr(self, ast: IdentifierExpr):
		assert (ast.annotated_type.type_name.can_be_private())

		# prepare zokrates argument
		t, c = self.function_helper.get_next_temporary_variable()
		helper_var_name = f'{t}[{c}]'
		sol_expr = self.sol.visit(ast)
		self.sol.pre_simple_statement += [f'{helper_var_name} = {sol_expr};']

		# add to zokrates argument
		self.proof_helper.zok_arguments += [helper_var_name]

		# add to zokrates parameter, prepare expression holding `ast`
		parameter_var_name = f'{t}{c}'
		self.proof_helper.add_public_param(parameter_var_name, ast)
		if ast.annotated_type.privacy_annotation.is_all_expr():
			ret = parameter_var_name
		else:
			key = self.proof_helper.add_private_key(parameter_var_name)
			ret = f'dec({parameter_var_name}, {key})'

		# add to zokrates proof argument
		self.proof_helper.proof_arguments += [FromSolidity(ast)]

		# check return zokrates expression holding ast
		return self.ensure_bool_or_int(ret, False)


