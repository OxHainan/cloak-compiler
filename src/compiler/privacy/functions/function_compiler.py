import os
import my_logging
import compiler.privacy.functions.function_helper
from zkay_ast.ast import AST, CodeVisitor, AnnotatedTypeName, MeExpr, TeeExpr, ReclassifyExpr, \
	FunctionDefinition, Parameter, RequireStatement, ExpressionStatement, SimpleStatement, \
	AssignmentStatement, Expression, Identifier, IdentifierExpr, indent, ReturnStatement, Mapping, \
	ConstructorDefinition, UserDefinedTypeName, ContractDefinition, StateVariableDeclaration, Block, \
	VariableDeclaration, VariableDeclarationStatement, TypeName, FunctionCallExpr, BuiltinFunction, BooleanLiteralExpr, \
	ConstructorOrFunctionDefinition, FunctionPrivacyType
from zkay_ast.process_ast import get_processed_ast
from compiler.privacy.used_contract import UsedContract
from compiler.solidity.sol_template import hash_function
from compiler.zokrates.tags import tag, helper_tag, param_tag, tee_tag
from compiler.zokrates.compiler import compile_zokrates, n_proof_arguments, get_work_dir
from compiler.zokrates.proof_helper import ProofHelper, FromZok, ParameterCheck, FromSolidity
from utils.helpers import save_to_file, prepend_to_lines, lines_of_code
from compiler.pki import pki_contract_filename, pki_contract_template
from compiler.zokrates.zok_template import zok_helpers


class FunctionVisitor(CodeVisitor):

	def __init__(self, sol: CodeVisitor, function_helper):
		super().__init__(False)
		self.sol = sol
		self.function_helper: function_helper.FunctionHelper = function_helper
		self.proof_helper = ProofHelper()

		self.want_bool = False

	def code(self):
		if len(self.proof_helper.statements) > 0:
			inputs = ", ".join(self.proof_helper.public_params)
			statements = [f'1 == checkHash([{inputs}], [inputHash0, inputHash1])'] + self.proof_helper.statements + ['return 1']
			s = '\n'.join(statements)    # like '1 == checkHash([genHelper0, genHelper0PK], [inputHash0, inputHash1])\ngenHelper0 == enc(genHelper0Value, genHelper0R, genHelper0PK)\nreturn 1'
			s = indent(s)
		else:
			return None

		n_public_params = len(self.proof_helper.public_params)

		args = ', '.join(self.proof_helper.zok_params + ['field inputHash0', 'field inputHash1'])
		my_logging.data('nPublicParams', n_public_params)
		docs = '\n'.join([f'{n}: {d}' for n, d in self.proof_helper.param_docs])    # comments for parameters before main
		docs = prepend_to_lines(docs, '// ')

		adjusted_zok_helpers = zok_helpers.replace('$NINPUTS', str(n_public_params))  # include predefined zok functions like 'enc', 'dec', 'checkHash'
		return f'{adjusted_zok_helpers}\n\n{docs}\ndef main({args}) -> (field):\n{s}'

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

	def ensure_pki(self):
		contract_name = 'PublicKeyInfrastructure'
		pki = f'{tag}{contract_name}'
		if self.sol.pki_contract is None:
			self.sol.pki_contract = UsedContract(pki_contract_filename, contract_name, pki)
			self.sol.used_contracts += [self.sol.pki_contract]
		return pki

	def check_proper_encryption(self, p: Parameter):
		if p.annotated_type.privacy_annotation.is_all_expr():
			# no check necessary
			pass
		else:
			# prepare zokrates argument
			t, c = self.function_helper.get_next_temporary_variable()
			helper_var_name = f'{t}[{c}]'
			self.sol.pre_simple_statement += [f'{helper_var_name} = {p.idf};']

			# add to zokrates argument
			pki = self.ensure_pki()
			owner = p.annotated_type.privacy_annotation.privacy_annotation_label()
			self.proof_helper.zok_arguments += [helper_var_name, f'{pki}.getPk({self.sol.visit(owner)})']

			# add to zokrates parameter
			parameter_var_name = f'{t}{c}'
			self.proof_helper.add_public_param(parameter_var_name, p)
			value = self.proof_helper.add_value(parameter_var_name)
			randomness = self.proof_helper.add_randomness(parameter_var_name)
			key = f'{parameter_var_name}PK'
			self.proof_helper.add_public_param(key, p)

			# add to zokrates code
			self.proof_helper.statements += [f'{parameter_var_name} == enc({value}, {randomness}, {key})']

			# add to zokrates proof argument
			self.proof_helper.proof_arguments += [ParameterCheck(p)]

	def from_solidity(self, ast: Expression):
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

	def visitBooleanLiteralExpr(self, ast: BooleanLiteralExpr):
		return self.ensure_bool_or_int('1' if ast.value else '0', False)

	def visitIdentifierExpr(self, ast: IdentifierExpr):
		return self.from_solidity(ast)

	@staticmethod
	def bool_to_int(e: str):
		return f'if {e} then 1 else 0 fi'

	@staticmethod
	def int_to_bool(e: str):
		return f'{e} == 1'

	def ensure_bool_or_int(self, e: str, is_bool: bool):
		if self.want_bool and is_bool:
			return e
		elif self.want_bool and not is_bool:
			return self.int_to_bool(e)
		elif not self.want_bool and is_bool:
			return self.bool_to_int(e)
		elif not self.want_bool and not is_bool:
			return e
		assert False

	def visitFunctionCallExpr(self, ast: FunctionCallExpr):
		if isinstance(ast.func, BuiltinFunction):
			if not ast.func.is_private:
				return self.from_solidity(ast)
			elif ast.func.op == 'ite':
				with WantBool(self, True):
					cond = self.visit(ast.args[0])
				t = self.visit(ast.args[1])
				e = self.visit(ast.args[2])
				# bool vs int correct because self.want_bool is handled recursively
				return f'if {cond} then {t} else {e} fi'
			elif ast.func.is_bop():
				with WantBool(self, True):
					args = [self.visit(arg) for arg in ast.args]
					# add parenthesis
					args = [f'({a})' for a in args]
				e = ast.func.format_string().format(*args)
				return self.ensure_bool_or_int(e, True)
			elif ast.func.op == '!=':
				args = [self.visit(arg) for arg in ast.args]
				e = f'! ({args[0]} == {args[1]})'
				return self.ensure_bool_or_int(e, True)
			elif ast.func.op == '==' or ast.func.is_comp():
				with WantBool(self, False):
					e = super().visitFunctionCallExpr(ast)
				return self.ensure_bool_or_int(e, True)

		return super().visitFunctionCallExpr(ast)

	def visitReclassifyExpr(self, ast: ReclassifyExpr):
		# stay within zokrates, even if sub-expression is public
		# we will only step out when we hit an operation zokrates cannot handle
		return self.visit(ast.expr)

class WantBool:
	def __init__(self, v: FunctionVisitor, want_bool: bool):
		self.v = v
		self.want_bool = want_bool

	def __enter__(self):
		self.old = self.v.want_bool
		self.v.want_bool = self.want_bool

	def __exit__(self, t, value, traceback):
		self.v.want_bool = self.old