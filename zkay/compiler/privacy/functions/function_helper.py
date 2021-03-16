from zkay_ast.ast import AST, CodeVisitor, AnnotatedTypeName, MeExpr, TeeExpr, ReclassifyExpr, \
	FunctionDefinition, Parameter, RequireStatement, ExpressionStatement, SimpleStatement, \
	AssignmentStatement, Expression, Identifier, IdentifierExpr, indent, ReturnStatement, Mapping, \
	ConstructorDefinition, UserDefinedTypeName, ContractDefinition, StateVariableDeclaration, Block, \
	VariableDeclaration, VariableDeclarationStatement, TypeName, FunctionCallExpr, BuiltinFunction, BooleanLiteralExpr, \
	ConstructorOrFunctionDefinition, FunctionPrivacyType
from typing import List
from compiler.privacy.functions.tee_compiler import TeeVisitor
from compiler.privacy.functions.zkp_compiler import ZokratesVisitor
from compiler.zokrates.tags import tag, helper_tag, param_tag, tee_tag

class FunctionHelper:

	def __init__(self, v, ast):
		assert isinstance(v, CodeVisitor)
		self.return_variable: str = None

		# directory holding zokrates information (especially keys)
		self.compiled_to_directory: str = None

		self.n_temporary_variables = 0
		self.n_tee_result_variables = 0
		self.precomputed_parameters: List[Expression] = []
		self.proof_parameter = None
		self.verifier_contract_parameters = []
		self.ast = ast

		assert isinstance(self.ast, ConstructorOrFunctionDefinition)
		if self.ast.privacy_type == FunctionPrivacyType.ZKP \
            or self.ast.privacy_type == FunctionPrivacyType.PUB:
			self.function_visitor = ZokratesVisitor(v, self)
		elif self.ast.privacy_type == FunctionPrivacyType.TEE:
			self.function_visitor = TeeVisitor(v, self)
		elif self.ast.privacy_type == FunctionPrivacyType.MPC:
			# TODO: add visitor for mpc
			pass

	def get_next_temporary_variable(self):
		c = self.n_temporary_variables
		self.n_temporary_variables += 1
		return helper_tag, c

	def get_next_tee_result_variable(self):
		c = self.n_tee_result_variables
		self.n_tee_result_variables += 1
		return tee_tag, c

	def get_next_precomputed_parameter(self, expr: Expression):
		index = len(self.precomputed_parameters)
		self.precomputed_parameters += [expr]
		return param_tag, index

	def declare_temporary_variables(self, body: str):
		if self.n_temporary_variables > 0:
			body = f'uint[{self.n_temporary_variables}] memory {helper_tag};\n{body}'
		return body

	def add_return_variable(self, body: str):
		if self.return_variable is not None:
			body += f'\nreturn {self.return_variable};'
		return body

	def get_all_parameters(self, params: List):
		# make shallow copy of list
		new_params = []
		# delete non public params
		for p in params:
			if p.annotated_type.privacy_annotation.is_all_expr():
				new_params.append(p)

		# add proof parameter
		if self.proof_parameter:
			new_params += [self.proof_parameter]

		n = len(self.precomputed_parameters)
		if n > 0:
			new_params += [f'uint[{n}] memory {param_tag}']

		# add verifier contract parameters (only non-empty for constructor)
		new_params += self.verifier_contract_parameters

		return new_params

	def get_zok_arguments(self):
		if self.ast.privacy_type == FunctionPrivacyType.ZKP:
			return self.function_visitor.proof_helper.zok_arguments
		elif self.ast.privacy_type == FunctionPrivacyType.TEE:
			return self.function_visitor.proof_helper.zok_arguments
		elif self.ast.privacy_type == FunctionPrivacyType.MPC:
			# TODO: add visitor for mpc
			pass
		return None
		