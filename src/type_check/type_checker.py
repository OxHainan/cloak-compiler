from typing import Union
from compiler import privacy

from zkay_ast.ast import AST, ContractDefinition, Identifier, IdentifierExpr, ReturnStatement, IfStatement, \
	AssignmentExpr, BooleanLiteralExpr, NumberLiteralExpr, AnnotatedTypeName, Expression, TypeName, \
	FunctionDefinition, FunctionPrivacyType, StateVariableDeclaration, Mapping, \
	AssignmentStatement, MeExpr, AllExpr, TeeExpr, ConstructorDefinition, ReclassifyExpr, FunctionCallExpr, \
	BuiltinFunction, VariableDeclarationStatement, RequireStatement
from zkay_ast.visitor.deep_copy import deep_copy
from zkay_ast.visitor.visitor import AstVisitor
from type_check.contains_private import contains_private
from type_check.final_checker import check_final
from type_check.type_exceptions import TypeMismatchException, TypeException

def type_check(ast):
	check_final(ast)
	v = TypeCheckVisitor()
	v.visit(ast)

class TypeCheckVisitor(AstVisitor):

	def __init__(self, traversal='post', log=False):
	 super().__init__(traversal=traversal, log=log)

	def check_right_hand_statement(self, rhs: Expression, expected_type: AnnotatedTypeName):
		'''
		check right hand statement
		'''
		instance = rhs.instanceof(expected_type)
		if not instance:
			raise TypeMismatchException(expected_type, rhs.annotated_type, rhs)

	def visitAssignmentExpr(self, ast: AssignmentExpr):
		raise TypeException("Subexpressions with side-effects are currently not supported", ast)

	def visitAssignmentStatement(self, ast: AssignmentStatement):
		# prevent modifying final
		if isinstance(ast, AssignmentExpr):
			f = ast.statement.function
		else:
			f = ast.function
		if isinstance(ast.lhs, IdentifierExpr):
			target = ast.lhs.target
			if hasattr(target, 'keywords'):
				if 'final' in target.keywords:
					if isinstance(target, StateVariableDeclaration) and isinstance(f, ConstructorDefinition):
						# assignment allowed
						pass
					else:
						raise TypeException('Modifying "final" variable', ast)

	def visitVariableDeclarationStatement(self, ast: VariableDeclarationStatement):
		if ast.get_related_function().privacy_type != FunctionPrivacyType.TEE:
			if ast.expr:
				self.check_right_hand_statement(ast.expr, ast.variable_declaration.annotated_type)

	def check_builtin_function_call(self, ast: FunctionCallExpr, func: BuiltinFunction, private=False):
		if func.is_index():
			return self.check_index(ast)

	def check_index(self, ast: FunctionCallExpr):
		arr = ast.args[0]
		index = ast.args[1]

		map_t = arr.annotated_type
		# should have already been checked
		assert (map_t.privacy_annotation.is_all_expr())

		# do actual type checking
		if isinstance(map_t.type_name, Mapping):
			key_type = map_t.type_name.key_type
			expected = AnnotatedTypeName(key_type, Expression.all_expr())
			instance = index.instanceof(expected)
			if not instance:
				raise TypeMismatchException(expected, index.annotated_type, ast)

			# record indexing information
			if not index.privacy_annotation_label():
				raise TypeException(f'Index cannot be used as a privacy type for array of type {map_t}', ast)

		else:
			raise TypeException('Indexing into non-mapping', ast)

	def visitFunctionCallExpr(self, ast: FunctionCallExpr):
		if isinstance(ast.func, BuiltinFunction):
			self.check_builtin_function_call(ast, ast.func)
		else:
			raise TypeException('Function calls currently not supported', ast)

	def visitReclassifyExpr(self, ast: ReclassifyExpr):
		if ast.get_related_function().privacy_type == FunctionPrivacyType.TEE:
			raise TypeException(f'reveal is not need in TEE-based private function', ast)
		elif isinstance(ast.privacy, Identifier):
			pass
		else:
			if not ast.privacy.privacy_annotation_label():
				raise TypeException('Second argument of "reveal" cannot be used as a privacy type', ast)
			if ast.privacy.is_all_expr() and ast.expr.annotated_type.privacy_annotation.is_all_expr():
				raise TypeException('Redundant "reveal": Expression is already "@all"', ast)

	def visitIfStatement(self, ast: IfStatement):
		if ast.get_related_function().privacy_type == FunctionPrivacyType.TEE:
			pass
		else:
			b = ast.condition
			expected = AnnotatedTypeName(TypeName.bool_type(), Expression.all_expr())
			if not b.instanceof(expected):
				raise TypeMismatchException(expected, b.annotated_type, b)

	def visitReturnStatement(self, ast: ReturnStatement):
		f = ast.parent
		while not isinstance(f, FunctionDefinition):
			f = f.parent

		assert(isinstance(f, FunctionDefinition))
		expected_types = f.get_return_type()

		instance = ast.expr.instanceof(expected_types)
		if not instance:
			raise TypeMismatchException(expected_types, ast.expr.annotated_type, ast)

	def visitNumberLiteralExpr(self, ast: NumberLiteralExpr):
		if ast.value < 0:
			raise TypeException("Negative number currently not supported:", ast)

	def visitFunctionDefinition(self, ast: FunctionDefinition):
		if ast.get_related_function().privacy_type != FunctionPrivacyType.TEE:
			for t in ast.get_parameter_types():
				ann = t.privacy_annotation
				if ann.is_all_expr() or ann.is_me_expr() or ann.is_tee_expr():
					continue
				else:
					raise TypeException(f'Only me/all accepted as privacy type of function parameters', ast)

		print(f'Function {ast.name} checked...')
		
	def visitConstructorDefinition(self, ast: ConstructorDefinition):
		print(f'Function {ast.name} checked...')

	def visitStateVariableDeclaration(self, ast: StateVariableDeclaration):
		if ast.expr:
			# prevent private operations in declaration
			if contains_private(ast):
				raise TypeException('Private assignments to state variables must be in the constructor', ast)

			# check type
			self.check_right_hand_statement(ast.expr, ast.annotated_type)

		# prevent "me" annotation
		p = ast.annotated_type.privacy_annotation
		if p.is_me_expr():
			raise TypeException(f'State variables cannot be annotated as me', ast)

	def visitMapping(self, ast: Mapping):
		if ast.key_label is not None:
			if ast.key_type != TypeName.address_type():
				raise TypeException(f'Only addresses can be annotated', ast)

	def visitRequireStatement(self, ast: RequireStatement):
		if ast.get_related_function().privacy_type == FunctionPrivacyType.TEE:
			pass
		elif not ast.condition.annotated_type.privacy_annotation.is_all_expr():
			raise TypeException(f'require needs public argument', ast)

	def visitAnnotatedTypeName(self, ast: AnnotatedTypeName):
		if ast.privacy_annotation != Expression.all_expr():
			if not ast.type_name.can_be_private():
				raise TypeException(f'Currently, we do not support private {str(ast.type_name)}', ast)

		p = ast.privacy_annotation
		if isinstance(p, IdentifierExpr):
			t = p.target
			if isinstance(t, Mapping):
				# no action necessary, this is the case: mapping(address!x => uint@x)
				pass
			elif 'final' not in t.keywords:
				raise TypeException('Privacy annotations must be "final", if they are expressions', ast)
			elif t.annotated_type != AnnotatedTypeName.address_all():
				raise TypeException(f'Privacy type is not a public address, but {str(t.annotated_type)}', ast)