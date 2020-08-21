from compiler.privacy.functions.function_compiler import FunctionVisitor, WantBool
from zkay_ast.ast import AST, CodeVisitor, AnnotatedTypeName, MeExpr, TeeExpr, ReclassifyExpr, \
	FunctionDefinition, Parameter, RequireStatement, ExpressionStatement, SimpleStatement, \
	AssignmentStatement, Expression, Identifier, IdentifierExpr, indent, ReturnStatement, Mapping, \
	ConstructorDefinition, UserDefinedTypeName, ContractDefinition, StateVariableDeclaration, Block, \
	VariableDeclaration, VariableDeclarationStatement, TypeName, FunctionCallExpr, BuiltinFunction, BooleanLiteralExpr, \
	ConstructorOrFunctionDefinition, FunctionPrivacyType
from compiler.zokrates.proof_helper import ProofHelper, FromZok, ParameterCheck, FromSolidity
from compiler.zokrates.tags import tag, helper_tag, param_tag, tee_tag

class TeeVisitor(FunctionVisitor):

	def from_tee(self, ast: Expression):
		# link and delete tee related function parameters
		with WantBool(self, False):
			expr = self.visit(ast) 

		is_all = ast.annotated_type.privacy_annotation.is_all_expr()

		# add to function parameter
		t, c = self.function_helper.get_next_tee_result_variable()

		# replace the left expression of AssignmentStatement and prepare zokrates argument
		func = ast.get_related_function()
		tee_parameter = Parameter([], AnnotatedTypeName(TypeName.uint_type(), ast.annotated_type.privacy_annotation), Identifier(f'{t}{c}'))
		tee_rhs_expr = IdentifierExpr(tee_parameter.idf)
		func.parameters.append(tee_parameter)

		tee_rhs_expr.target = tee_parameter

		# return expression in solidity
		return tee_rhs_expr

	# def check_proper_encryption(self, p: Parameter):
	# 	# TODO: add signature check for TEE, or just achieve this by require as now it is
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

		sol_expr = self.sol.visit(ast)

		# link and delete tee related function parameters
		_func = ast.get_related_function()
		if _func:
			_params = _func.parameters.copy()
			for p in _func.parameters:
				if sol_expr == p.idf.name:
					_params.remove(p)
			_func.parameters = _params

		v = None
		return self.ensure_bool_or_int(v, False)
