from typing import Union

from zkay_ast.ast import IdentifierExpr, ReturnStatement, IfStatement, \
	AssignmentExpr, BooleanLiteralExpr, NumberLiteralExpr, AnnotatedTypeName, Expression, TypeName, \
	FunctionDefinition, StateVariableDeclaration, Mapping, \
	AssignmentStatement, MeExpr, ConstructorDefinition, ReclassifyExpr, FunctionCallExpr, \
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

	def get_rhs(self, rhs: Expression, expected_type: AnnotatedTypeName):
		instance = rhs.instanceof(expected_type)
		if not instance:
			raise TypeMismatchException(expected_type, rhs.annotated_type, rhs)
		elif instance == 'make-private':
			return self.make_private(rhs, expected_type.privacy_annotation)
		else:
			return rhs

	def visitAssignmentExpr(self, ast: AssignmentExpr):
		raise TypeException("Subexpressions with side-effects are currently not supported", ast)

	def visitAssignmentStatement(self, ast: AssignmentStatement):
		expected_type = ast.lhs.annotated_type
		ast.rhs = self.get_rhs(ast.rhs, expected_type)
		ast.annotated_type = expected_type

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
		if ast.expr:
			ast.expr = self.get_rhs(ast.expr, ast.variable_declaration.annotated_type)

	def handle_builtin_function_call(self, ast: FunctionCallExpr, func: BuiltinFunction, private=False):
		# set parameter type
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
					raise TypeMismatchException(expected, arg.annotated_type, ast)
			elif instance == 'make-private':
				# replace argument
				ast.args[i] = self.make_private(arg, Expression.me_expr())
			else:
				# no action necessary
				pass

		assert(output_type is not None)
		assert(isinstance(output_type, AnnotatedTypeName))
		ast.annotated_type = output_type

	def handle_index(self, ast: FunctionCallExpr):
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
			if index.privacy_annotation_label():
				map_t.type_name.instantiated_key = index
			else:
				raise TypeException(f'Index cannot be used as a privacy type for array of type {map_t}', ast)

			# determine value type
			ast.annotated_type = map_t.type_name.value_type
		else:
			raise TypeException('Indexing into non-mapping', ast)

	@staticmethod
	def make_private(expr: Expression, privacy: Expression):
		assert(privacy.privacy_annotation_label() is not None)

		r = ReclassifyExpr(expr, privacy.privacy_annotation_label())

		# set type
		r.annotated_type = AnnotatedTypeName(expr.annotated_type.type_name, privacy)

		# set statement
		r.statement = expr.statement

		# set parents
		r.parent = expr.parent
		r.annotated_type.parent = r
		expr.parent = r

		return r

	def visitFunctionCallExpr(self, ast: FunctionCallExpr):
		if isinstance(ast.func, BuiltinFunction):
			self.handle_builtin_function_call(ast, ast.func)
		else:
			raise TypeException('Function calls currently not supported', ast)

	def visitReclassifyExpr(self, ast: ReclassifyExpr):
		if not ast.privacy.privacy_annotation_label():
			raise TypeException('Second argument of "reveal" cannot be used as a privacy type', ast)
		if ast.privacy.is_all_expr() and ast.expr.annotated_type.privacy_annotation.is_all_expr():
			raise TypeException('Redundant "reveal": Expression is already "@all"', ast)
		ast.annotated_type = AnnotatedTypeName(ast.expr.annotated_type.type_name, ast.privacy)

	def visitIfStatement(self, ast: IfStatement):
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
		elif instance == 'make-private':
			ast.expr = self.make_private(ast.expr, expected_types.privacy_annotation)

	def visitBooleanLiteralExpr(self, ast: BooleanLiteralExpr):
		ast.annotated_type = AnnotatedTypeName.bool_all()

	def visitNumberLiteralExpr(self, ast: NumberLiteralExpr):
		if ast.value < 0:
			raise TypeException("Negative number currently not supported:", ast)
		ast.annotated_type = AnnotatedTypeName.uint_all()

	def visitMeExpr(self, ast: MeExpr):
		ast.annotated_type = AnnotatedTypeName.address_all()

	def visitIdentifierExpr(self, ast: IdentifierExpr):
		if isinstance(ast.target, Mapping):
			# no action necessary, the identifier will be replaced later
			pass
		else:
			ast.annotated_type = deep_copy(ast.target.annotated_type)

	def visitFunctionDefinition(self, ast: FunctionDefinition):
		for t in ast.get_parameter_types():
			ann = t.privacy_annotation
			if ann.is_all_expr() or ann.is_me_expr():
				continue
			else:
				raise TypeException(f'Only me/all accepted as privacy type of function parameters', ast)

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
