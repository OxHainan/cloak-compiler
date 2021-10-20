import inspect
from typing import TypeVar

from cloak.cloak_ast.ast import AST, Expression, Statement, UserDefinedTypeName
from cloak.cloak_ast.pointers.parent_setter import set_parents
from cloak.cloak_ast.pointers.symbol_table import link_identifiers
from cloak.cloak_ast.visitor.visitor import AstVisitor
from cloak.cloak_ast import ast as ast_module

T = TypeVar('T')


def deep_copy(ast: T, with_types=False, with_analysis=False) -> T:
    """

    :param ast:
    :param with_types: (optional)
    :return: a deep copy of `ast`

    Only parents and identifiers are updated in the returned ast (e.g., inferred types are not preserved)
    """
    assert isinstance(ast, AST)
    v = DeepCopyVisitor(with_types, with_analysis)
    ast_copy = v.visit(ast)
    ast_copy.parent = ast.parent
    set_parents(ast_copy)
    link_identifiers(ast_copy)
    return ast_copy


def replace_expr(old_expr: Expression, new_expr: Expression, copy_type: bool = False):
    """
        Copies over ast common ast attributes and reruns, parent setter, symbol table, side effect detector
    """
    _replace_ast(old_expr, new_expr)
    if copy_type:
        new_expr.annotated_type = old_expr.annotated_type
    return new_expr


def _replace_ast(old_ast: AST, new_ast: AST):
    new_ast.parent = old_ast.parent
    DeepCopyVisitor.copy_ast_fields(old_ast, new_ast)
    if old_ast.parent is not None:
        set_parents(new_ast)
        link_identifiers(new_ast)


class DeepCopyVisitor(AstVisitor):
    setting_later = {
        # General fields
        'line',
        'column',
        'modified_values',
        'read_values',

        # Specialized fields
        'parent',
        'namespace',
        'names',
        'had_privacy_annotation',
        'annotated_type',
        'statement',
        'before_analysis',
        'after_analysis',
        'target',
        'instantiated_key',
        'function',
        'is_private',
        'evaluate_privately',
        'has_side_effects',
        'contains_inlined_function',
        '_size_in_bits',
        'signed',
        '_annotated_type',

        # Function stuff
        'called_functions',
        'is_recursive',
        'has_static_body',
        'can_be_private',
        'requires_verification',
        'requires_verification_when_external',
        'has_side_effects',
        'can_be_external',
        'is_payable',
        'original_body',
        'original_code',
        'return_var_decls',

        'pre_statements',
        'excluded_from_simulation',

        # For array children (ciphertext, key etc.)
        'expr',
        'value_type'
    }

    def __init__(self, with_types, with_analysis):
        super().__init__('node-or-children')
        self.with_types = with_types
        self.with_analysis = with_analysis

    @staticmethod
    def copy_ast_fields(ast, ast_copy):
        ast_copy.line = ast.line
        ast_copy.column = ast.column
        ast_copy.modified_values = ast.modified_values
        ast_copy.read_values = ast.read_values

    def visitChildren(self, ast):
        c = ast.__class__
        args_names = inspect.getfullargspec(c.__init__).args[1:]
        new_fields = {}
        for arg_name in args_names:
            old_field = getattr(ast, arg_name)
            new_fields[arg_name] = self.copy_field(old_field)

        for k in ast.__dict__.keys():
            if k not in new_fields and k not in self.setting_later and k not in inspect.getfullargspec(c.__bases__[0].__init__).args[1:]:
                raise ValueError("Not copying", k)
        ast_copy = c(**new_fields)
        self.copy_ast_fields(ast, ast_copy)
        return ast_copy

    def visitAnnotatedTypeName(self, ast):
        ast_copy = self.visitChildren(ast)
        ast_copy.had_privacy_annotation = ast.had_privacy_annotation
        return ast_copy

    def visitUserDefinedTypeName(self, ast: UserDefinedTypeName):
        ast_copy = self.visitChildren(ast)
        ast_copy.target = ast.target
        return ast_copy

    def visitBuiltinFunction(self, ast):
        ast_copy = self.visitChildren(ast)
        ast_copy.is_private = ast.is_private
        return ast_copy

    def visitExpression(self, ast: Expression):
        ast_copy = self.visitChildren(ast)
        if self.with_types and ast.annotated_type is not None:
            ast_copy.annotated_type = ast.annotated_type.clone()
        ast_copy.evaluate_privately = ast.evaluate_privately
        return ast_copy

    def visitStatement(self, ast: Statement):
        ast_copy = self.visitChildren(ast)
        if self.with_analysis:
            ast_copy.before_analysis = ast.before_analysis
        return ast_copy

    # def visitConstructorOrFunctionDefinition(self, ast: ConstructorOrFunctionDefinition) -> ConstructorOrFunctionDefinition:
    #     ast_copy = self.visitChildren(ast)
    #     if hasattr(ast, "privacy_type"):
    #         ast_copy.privacy_type = privacy_type
    #     return ast_copy

    def copy_field(self, field):
        if field is None:
            return None
        elif isinstance(field, str) or isinstance(field, int) or isinstance(field, bool):
            return field
        elif isinstance(field, list):
            return [self.copy_field(e) for e in field]
        else:
            return self.visit(field)

    def visitBytesTypeName(self, ast: ast_module.BytesTypeName):
        return ast_module.BytesTypeName(ast.name)
