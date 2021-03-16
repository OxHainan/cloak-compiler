from shutil import copy
from typing import List, Dict, Optional
from zkay import my_logging
from zkay.zkay_ast.ast import AST, CodeVisitor, AnnotatedTypeName, MeExpr, TeeExpr, ReclassifyExpr, \
	FunctionDefinition, Parameter, RequireStatement, ExpressionStatement, SimpleStatement, \
	AssignmentStatement, Expression, Identifier, IdentifierExpr, indent, ReturnStatement, Mapping, \
	ConstructorDefinition, UserDefinedTypeName, ContractDefinition, StateVariableDeclaration, Block, \
	VariableDeclaration, VariableDeclarationStatement, TypeName, FunctionCallExpr, BuiltinFunction, BooleanLiteralExpr, \
	ConstructorOrFunctionDefinition, FunctionPrivacyType
from zkay.zkay_ast.process_ast import get_processed_ast
from zkay.compiler.privacy.functions.function_helper import FunctionHelper
from zkay.compiler.infra_contracts import pki_contract_template, cloak_service_template, cloak_service_contract_filename
from zkay.compiler.privacy.used_contract import UsedContract
from zkay.compiler.solidity.sol_template import hash_function, tee_function
from zkay.compiler.zokrates.tags import tag, helper_tag, param_tag, tee_tag
from zkay.compiler.zokrates.compiler import compile_zokrates, n_proof_arguments, get_work_dir
from zkay.my_logging.log_context import log_context
from zkay.utils.helpers import read_file, save_to_file, prepend_to_lines, lines_of_code
from zkay.type_check import type_pure
from zkay.utils.timer import time_measure
from zkay.type_check.privacy_policy import PrivacyPolicyEncoder, PrivacyPolicy


def compile_ast(ast: AST, output_directory: str, output_file: Optional[str], simulate=False):

	print(f"Begin compiling contract: {ast.contracts[0].idf.name} ......")
	"""
	Parameters:
	simulate (bool): Only simulate compilation to determine
	                 how to translate transactions
	"""
	v = CloakCompilerVisitor(output_directory, simulate)
	s = v.visit(ast)

	my_logging.data('zkpAddedLOCFull', ast.zkpAddedLOCFull)
	my_logging.data('teeAddedLOCFull', ast.teeAddedLOCFull)
	my_logging.data('newLoc', lines_of_code(s))

	if simulate:
		return ast, v
	else:
		# Save pure Solidity code as target function
		cloak_code = read_file(f'{output_directory}/../{output_file}')
		pured_solidity_code = type_pure.delete_cloak_annotation(cloak_code)
		save_to_file(output_directory, f'pured-{output_file}', pured_solidity_code)

		# Save parsed Cloak code as verifier 
		original_code = ast.code()
		original_code = prepend_to_lines(original_code, '// ')
		filename = save_to_file(output_directory, output_file, original_code + '\n\n' + s)
		copy(pki_contract_template, output_directory)
		copy(cloak_service_template, output_directory)
		return filename

def compile_code(code: str, output_directory: str, output_file: Optional[str], simulate=False):
	ast = get_processed_ast(code)

	return compile_ast(ast, output_directory, output_file, simulate)

ppv = CodeVisitor() # for privacy policy

class CloakCompilerVisitor(CodeVisitor):

	def __init__(self, output_directory: str, simulate=False):
		# do not display `final` keywords (`final` is not in Solidity fragment)
		super().__init__(False)

		# global properties
		self.output_directory = output_directory
		self.simulate = simulate

		# synthesized code
		self.pki_contract: UsedContract = None
		self.used_contracts: List[UsedContract] = []
		self.new_state_variables: List[StateVariableDeclaration] = []

		# per-function properties
		self.function_helpers: Dict[ConstructorOrFunctionDefinition, FunctionHelper] = {}
		self.function_helper: FunctionHelper = None

		# per-statement properties
		self.pre_simple_statement: List[str] = None

	def visitConstructorDefinition(self, ast: ConstructorDefinition):
		return self.handle_function_definition(ast)

	def visitFunctionDefinition(self, ast: FunctionDefinition):
		return self.handle_function_definition(ast)

	def visitFunctionCallExpr(self, ast: FunctionCallExpr):
		if isinstance(ast.func, BuiltinFunction):
			if ast.func.is_private:
				if ast.get_related_function().privacy_type == FunctionPrivacyType.ZKP or ast.get_related_function().privacy_type == FunctionPrivacyType.PUB:
					return self.function_helper.function_visitor.from_zok(ast)
		return super().visitFunctionCallExpr(ast)

	def handle_function_definition(self, ast: ConstructorOrFunctionDefinition):
		if ast.privacy_type == FunctionPrivacyType.ZKP:
			return self.zkp_function_definition(ast)
		elif ast.privacy_type == FunctionPrivacyType.TEE or isinstance(ast, ConstructorDefinition):
			with time_measure('transformTEEFunction'):
				handled = self.tee_function_definition(ast)
			return handled
		elif ast.privacy_type == FunctionPrivacyType.MPC:
			return self.pub_function_definition(ast)

		return self.pub_function_definition(ast)

	def pub_function_definition(self, ast: ConstructorOrFunctionDefinition):
		teeAddedLOC = 0;
		# return self.zkp_function_definition(ast)
		with log_context('compileFunction', ast.name):

			self.function_helper = FunctionHelper(self, ast)
			self.function_helpers[ast] = self.function_helper
			
			# body
			body = ''

			# handle constructors: add addresses of required contracts
			_contract = ast.get_related_contract()
			assert(isinstance(_contract, ContractDefinition))
			if isinstance(ast, ConstructorDefinition):
				if _contract.is_tee_related:
					# tee_addr_param = Parameter([], AnnotatedTypeName(TypeName.address_type(), Expression.all_expr()), Identifier(f'tee_addr'))
					# ast.parameters.append(tee_addr_param)
					tee_service_name = "cloakService"
					c = UsedContract(cloak_service_contract_filename, "CloakService", tee_service_name)
					self.used_contracts += [c]
					# tee_addr_param = Parameter([], AnnotatedTypeName(TypeName.address_type(), Expression.all_expr()), Identifier(f'tee_addr'))
					# ast.parameters.append(tee_addr_param)

					for var_name in ["codeHash", "policyHash", "teeAppHash"]:
						t = AnnotatedTypeName(TypeName.uint_type(), Expression.all_expr())
						decl = StateVariableDeclaration(t, [], Identifier(f'{var_name}'), None)
						self.new_state_variables += [decl]
						self.function_helper.verifier_contract_parameters += [f'{self.visit(t)} _{var_name}']
						body = f'{var_name} = _{var_name};\n' + body
					
					var_name = "master"
					t = AnnotatedTypeName(TypeName.address_type(), Expression.all_expr())
					decl = StateVariableDeclaration(t, [], Identifier(f'{var_name}'), None)
					self.new_state_variables += [decl]
					self.function_helper.verifier_contract_parameters += [f'{self.visit(t)} _{var_name}']
					body = f'{var_name} = _{var_name};\n' + body

					teeAddedLOC += 16
					my_logging.data('teeAddedLOC', teeAddedLOC)

			body += self.visit_list(ast.body.statements)
			
			body = self.function_helper.add_return_variable(body)

			# wrap up
			body = indent(body)
			body = f'{{\n{body}\n}}'

			# prepare arguments for generating code
			if isinstance(ast, ConstructorDefinition):
				idf = None
				return_parameters = []
			elif isinstance(ast, FunctionDefinition):
				idf = ast.idf
				return_parameters = ast.return_parameters
			else:
				raise ValueError(ast)

			source_unit = ast.get_related_sourceuint()
			source_unit.teeAddedLOCFull += teeAddedLOC

			return super().function_definition_to_str(idf, ast.parameters, ast.modifiers, return_parameters, body)

	def zkp_function_definition(self, ast: ConstructorOrFunctionDefinition):
		zkpAddedLOC, teeAddedLOC = 0, 0
		with log_context('compile "ZKP" typed function', ast.name):

			self.function_helper = FunctionHelper(self, ast)
			self.function_helpers[ast] = self.function_helper

			body = ""
			# handle constructors: add addresses of required contracts
			_contract = ast.get_related_contract()
			assert(isinstance(_contract, ContractDefinition))
			if isinstance(ast, ConstructorDefinition):
				if _contract.is_tee_related:
					# tee_addr_param = Parameter([], AnnotatedTypeName(TypeName.address_type(), Expression.all_expr()), Identifier(f'tee_addr'))
					# ast.parameters.append(tee_addr_param)
					tee_service_name = "cloakService"
					c = UsedContract(cloak_service_contract_filename, "CloakService", tee_service_name)
					self.used_contracts += [c]
					# tee_addr_param = Parameter([], AnnotatedTypeName(TypeName.address_type(), Expression.all_expr()), Identifier(f'tee_addr'))
					# ast.parameters.append(tee_addr_param)

					for var_name in ["codeHash", "policyHash", "teeAppHash"]:
						t = AnnotatedTypeName(TypeName.uint_type(), Expression.all_expr())
						decl = StateVariableDeclaration(t, [], Identifier(f'{var_name}'), None)
						self.new_state_variables += [decl]
						self.function_helper.verifier_contract_parameters += [f'{self.visit(t)} _{var_name}']
						body = f'{var_name} = _{var_name};\n' + body
					
					var_name = "master"
					t = AnnotatedTypeName(TypeName.address_type(), Expression.all_expr())
					decl = StateVariableDeclaration(t, [], Identifier(f'{var_name}'), None)
					self.new_state_variables += [decl]
					self.function_helper.verifier_contract_parameters += [f'{self.visit(t)} _{var_name}']
					body = f'{var_name} = _{var_name};\n' + body

					teeAddedLOC += 16
					my_logging.data('teeAddedLOC', teeAddedLOC)

			# check private parameters
			self.pre_simple_statement = []
			for p in ast.parameters:
				self.function_helper.function_visitor.check_proper_encryption(p)   # out(e, alpha) & in(e, alpha)
			zkpAddedLOC += len(self.pre_simple_statement)
			body += '\n'.join(self.pre_simple_statement)  # add 'genHelper[0] = sol;'
				
			# body
			body += self.visit_list(ast.body.statements)
			zok_code = self.function_helper.function_visitor.code()   # generate .zok code (constraints writed in Zokrates DSL)

			# handle proofs
			my_logging.data('isPrivate', zok_code is not None)
			if zok_code is not None:
				if isinstance(ast, ConstructorDefinition):
					verifier_contract_name = 'Verify_constructor'
				elif isinstance(ast, FunctionDefinition):
					verifier_contract_name = f'Verify_{ast.idf.name}'  # like 'Verify_set_solution'
				else:
					raise ValueError(ast)

				if self.simulate:
					output_filename = None
					self.function_helper.compiled_to_directory = get_work_dir(self.output_directory, verifier_contract_name)
				else:
					my_logging.data('zokratesLoc', lines_of_code(zok_code))
					output_filename, d = compile_zokrates(zok_code, self.output_directory, name=verifier_contract_name)
					self.function_helper.compiled_to_directory = d  # like './eval-ccs2019/examples/exam/compiled/Verify_set_solution_zok'

				verifier_contract_variable = verifier_contract_name + '_var'   # like 'Verify_set_solution_var'
				c = UsedContract(output_filename, verifier_contract_name, verifier_contract_variable)
				self.used_contracts += [c]

				# proof
				proof_type = AnnotatedTypeName.array_all(TypeName.uint_type(), n_proof_arguments)
				proof_name = verifier_contract_name + 'proof'  # like 'Verify_set_solutionproof'
				proof_param = Parameter([], proof_type, Identifier(proof_name), 'memory')

				self.function_helper.proof_parameter = proof_param

				zok_arguments = self.function_helper.get_zok_arguments()  # like ['genHelper[0]', 'genPublicKeyInfrastr...sg.sender)']
				body += f'\nuint256[] memory {tag}inputs = new uint256[]({len(zok_arguments)});\n'
				inputs = [f'{tag}inputs[{i}]={name};' for i, name in enumerate(zok_arguments)]  # like ['geninputs[0]=genHelper[0];', 'geninputs[1]=genPubl...g.sender);']
				body += '\n'.join(inputs)
				body += f'\nuint128[2] memory {tag}Hash = get_hash({tag}inputs);'
				body += f'\n{verifier_contract_variable}.check_verify({proof_name}, [{tag}Hash[0], {tag}Hash[1], uint(1)]);'
				zkpAddedLOC += 6 + len(inputs)

			body = self.function_helper.declare_temporary_variables(body)  # like 'uint[1] memory genHelper; {body}'
			zkpAddedLOC += 1
			body = self.function_helper.add_return_variable(body)

			# handle constructors: add addresses of required contracts
			if isinstance(ast, ConstructorDefinition):
				for c in self.used_contracts:
					verifier_contract_parameter = c.state_variable_name + '_'
					t = AnnotatedTypeName(UserDefinedTypeName([Identifier(c.contract_name)]), Expression.all_expr())
					self.function_helper.verifier_contract_parameters += [f'{self.visit(t)} {verifier_contract_parameter}']
					body = f'{c.state_variable_name} = {verifier_contract_parameter};\n' + body
					decl = StateVariableDeclaration(t, [], Identifier(c.state_variable_name), None)
					self.new_state_variables += [decl]
					zkpAddedLOC += 3 # import, param and assign


			# wrap up
			body = indent(body)
			body = f'{{\n{body}\n}}'

			# prepare arguments for generating code
			if isinstance(ast, ConstructorDefinition):
				idf = None
				return_parameters = []
			elif isinstance(ast, FunctionDefinition):
				idf = ast.idf
				return_parameters = ast.return_parameters
			else:
				raise ValueError(ast)

			# determine parameters
			params = self.function_helper.get_all_parameters(ast.parameters)
			zkpAddedLOC += len(params) - len(ast.parameters)

			# record number of switches between zokrates and solidity
			my_logging.data('nCrosses', len(self.function_helper.function_visitor.proof_helper.proof_arguments))
			my_logging.data('zkpAddedLOC', zkpAddedLOC)
			source_unit = ast.get_related_sourceuint()
			source_unit.zkpAddedLOCFull += zkpAddedLOC
			source_unit.teeAddedLOCFull += teeAddedLOC

			return super().function_definition_to_str(idf, params, ast.modifiers, return_parameters, body)

	def tee_function_definition(self, ast: ConstructorOrFunctionDefinition):
		zkpAddedLOC, teeAddedLOC = 0, 0

		with log_context('compile "TEE" typed function', ast.name):
			self.function_helper = FunctionHelper(self, ast)
			self.function_helpers[ast] = self.function_helper

			body = ""

			my_logging.data('isPrivate', True)

			# handle constructors: add addresses of required contracts
			_contract = ast.get_related_contract()
			assert(isinstance(_contract, ContractDefinition))
			if isinstance(ast, ConstructorDefinition):
				if _contract.is_tee_related:
					tee_service_name = "cloakService"
					c = UsedContract(cloak_service_contract_filename, "CloakService", tee_service_name)
					self.used_contracts += [c]

					for var_name in ["codeHash", "policyHash", "teeAppHash"]:
						t = AnnotatedTypeName(TypeName.uint_type(), Expression.all_expr())
						decl = StateVariableDeclaration(t, [], Identifier(f'{var_name}'), None)
						self.new_state_variables += [decl]
						self.function_helper.verifier_contract_parameters += [f'{self.visit(t)} _{var_name}']
						body = f'{var_name} = _{var_name};\n' + body
					
					var_name = "master"
					t = AnnotatedTypeName(TypeName.address_type(), Expression.all_expr())
					decl = StateVariableDeclaration(t, [], Identifier(f'{var_name}'), None)
					self.new_state_variables += [decl]
					self.function_helper.verifier_contract_parameters += [f'{self.visit(t)} _{var_name}']
					body = f'{var_name} = _{var_name};\n' + body

					teeAddedLOC += 16

			# visit statements and delete tee related parameters and computation, just leave assignment
			self.pre_simple_statement = []
			body += self.visit_list(ast.body.statements)		

			if not isinstance(ast, ConstructorDefinition):
				tee_parameter = Parameter([], AnnotatedTypeName(UserDefinedTypeName([Identifier('uint[] memory')]), Expression.all_expr()), Identifier(f'report'))
				ast.parameters = [tee_parameter] + ast.parameters

				body = """
require(
	cloakService.verify(
		report,
		teeAppHash,
		codeHash,
		policyHash,
		funcHash,
		oldStateHash
	)
);\n""" + body

				if len(ast.mutate_states) > 0:
					body = f"uint oldStateHash = cloakService.get_hash(oldState);\n" + body
					for i in range(len(ast.mutate_states)):
						body = f"oldState[{i}] = {ast.mutate_states[i]};\n" + body
					body = f"uint[] memory oldState = new uint[]({len(ast.mutate_states)});\n" + body
				
				# TODO: compute function abi hash
				func_hash = "0x39a5e9243"
				body = f"uint funcHash = {func_hash};\n" + body

				teeAddedLOC += 1

			body = self.function_helper.add_return_variable(body)

			# handle constructors: add addresses of required contracts
			if isinstance(ast, ConstructorDefinition):
				for c in self.used_contracts:
					verifier_contract_parameter = c.state_variable_name + '_'
					t = AnnotatedTypeName(UserDefinedTypeName([Identifier(c.contract_name)]), Expression.all_expr())
					self.function_helper.verifier_contract_parameters += [f'{self.visit(t)} {verifier_contract_parameter}']
					body = f'{c.state_variable_name} = {verifier_contract_parameter};\n' + body
					decl = StateVariableDeclaration(t, [], Identifier(c.state_variable_name), None)
					self.new_state_variables += [decl]

			# wrap up
			body = indent(body)
			body = f'{{\n{body}\n}}'

			# prepare arguments for generating code
			if isinstance(ast, ConstructorDefinition):
				idf = None
				return_parameters = []
			elif isinstance(ast, FunctionDefinition):
				idf = ast.idf
				return_parameters = ast.return_parameters
			else:
				raise ValueError(ast)

			# determine parameters
			params = self.function_helper.get_all_parameters(ast.parameters)
			teeAddedLOC += len(params) - len(ast.parameters)

			my_logging.data('teeAddedLOC', teeAddedLOC)
			source_unit = ast.get_related_sourceuint()
			source_unit.zkpAddedLOCFull += zkpAddedLOC
			source_unit.teeAddedLOCFull += teeAddedLOC

			return super().function_definition_to_str(idf, params, ast.modifiers, return_parameters, body)

	def visitAnnotatedTypeName(self, ast: AnnotatedTypeName):
		# only display data type, not privacy annotation
		t = self.visit(ast.type_name)
		if ast.privacy_annotation.is_all_expr():
			return t
		else:
			return TypeName.uint_type().code()

	def visitMapping(self, ast: Mapping):
		k = self.visit(ast.key_type)
		v = self.visit(ast.value_type)
		return f"mapping({k} => {v})"

	def visitMeExpr(self, ast: MeExpr):
		return 'msg.sender'

	def visitTeeExpr(self, _: TeeExpr):
		return 'tee'

	def visitReclassifyExpr(self, ast: ReclassifyExpr):
		# take result from zokrates			
		r = self.function_helper.function_visitor.from_zok(ast)
		if ast.annotated_type.type_name == TypeName.bool_type() and ast.annotated_type.privacy_annotation.is_all_expr():
			return f'{r} == 1'
		else:
			return r

	def handleSimpleStatement(self, ast: SimpleStatement, f):
		self.pre_simple_statement = []
		code = f(ast)
		statements = '\n'.join(self.pre_simple_statement + [code])
		return statements

	def visitExpressionStatement(self, ast: ExpressionStatement):
		return self.handleSimpleStatement(ast, super().visitExpressionStatement)

	def visitRequireStatement(self, ast: RequireStatement):
		if ast.get_related_function().privacy_type == FunctionPrivacyType.TEE:
			# Delete require statement in on-chain contract. The true require statement will 
			# be held in TEE contract
			return None
		return self.handleSimpleStatement(ast, super().visitRequireStatement)

	def visitAssignmentStatement(self, ast: AssignmentStatement):
		if ast.get_related_function().privacy_type == FunctionPrivacyType.TEE:
			target = None
			if isinstance(ast.lhs, IdentifierExpr):
				target = ast.lhs.target
			elif isinstance(ast.lhs, FunctionCallExpr):
				map_id = ast.lhs
				while isinstance(map_id, FunctionCallExpr):
					map_id = map_id.args[0]
				target = map_id.target
			
			if isinstance(target, StateVariableDeclaration):
				lhs = self.visit(ast.lhs)
				# TODO: take result from zokrates to replace the rhs with a precomputed param signed by TEE
				r = self.function_helper.function_visitor.from_tee(ast.rhs)
				ast.rhs = r
				# ast.get_related_function().mutate_states.append(lhs)
				if ast.annotated_type.type_name == TypeName.bool_type() and ast.annotated_type.privacy_annotation.is_all_expr():
					return f'{lhs} = {r} == 1;'
				else:
					return f'{lhs} = {r};'
			else:
				return None

		return self.handleSimpleStatement(ast, super().visitAssignmentStatement)

	def visitVariableDeclarationStatement(self, ast: VariableDeclarationStatement):
		if ast.get_related_function().privacy_type == FunctionPrivacyType.TEE:
			return None
		return self.handleSimpleStatement(ast, super().visitVariableDeclarationStatement)

	def visitReturnStatement(self, ast: ReturnStatement):
		if ast.expr is None:
			return 'return;'
		else:
			if ast.get_related_function().privacy_type == FunctionPrivacyType.ZKP:
				self.function_helper.return_variable = f'{tag}Return'

				d = VariableDeclaration([], ast.expr.annotated_type, Identifier(self.function_helper.return_variable))
				d = VariableDeclarationStatement(d, ast.expr)

				# "return" will be emitted when handling function declaration
				return self.visit(d)
			else:
				_val = self.visit(ast.expr)
				return f'return {_val};'
		
	def visitContractDefinition(self, ast: ContractDefinition):
		with log_context('contract', ast.idf.name):
			functions = [self.visit(e) for e in ast.function_definitions]

			# constructors
			if len(ast.constructor_definitions) == 0:
				# add an empty constructor
				c = ConstructorDefinition([], ['public'], Block([]))
				c.parent = ast
				ast.constructor_definitions = [c]
			constructor_definitions = ast.constructor_definitions
			constructors = [self.visit(e) for e in constructor_definitions]

			# state variables
			decl = self.new_state_variables + ast.state_variable_declarations
			state_vars = [self.visit(e) for e in decl]

			# imports
			imported_filenames = [c.filename for c in self.used_contracts]
			imports = '\n'.join([f'import "./{f}";' for f in imported_filenames])

			# add util function
			functions += [tee_function]
			functions += [hash_function]

			# final string generation
			contract = self.contract_definition_to_str(
				ast.idf,
				state_vars,
				constructors,
				functions)
			return f'\n{imports}\n\n{contract}'