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
from compiler.infra_contracts import pki_contract_filename, cloak_service_contract_filename


class FunctionVisitor(CodeVisitor):

	def __init__(self, sol: CodeVisitor, function_helper):
		super().__init__(False)
		self.sol = sol
		self.function_helper: function_helper.FunctionHelper = function_helper
		self.proof_helper = ProofHelper()

		self.want_bool = False

	def ensure_pki(self):
		contract_name = 'PublicKeyInfrastructure'
		pki = f'{tag}{contract_name}'
		if self.sol.pki_contract is None:
			self.sol.pki_contract = UsedContract(pki_contract_filename, contract_name, pki)
			self.sol.used_contracts += [self.sol.pki_contract]
		return pki

	def from_solidity(self, ast: Expression):
		# inplemented by successors to handle none private BuildinFunctions
		pass

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