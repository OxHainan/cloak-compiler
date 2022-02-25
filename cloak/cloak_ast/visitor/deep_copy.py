import inspect
from typing import TypeVar

from cloak.cloak_ast.ast import AST, Expression, Statement, UserDefinedTypeName
from cloak.cloak_ast.pointers.parent_setter import set_parents
from cloak.cloak_ast.pointers.symbol_table import link_identifiers
from cloak.cloak_ast import ast as ast_module
from copy import deepcopy

T = TypeVar('T')


def deep_copy(ast: T, with_types=False, with_analysis=False) -> T:
    """
    :param ast:
    :param with_types: (optional)
    :return: a deep copy of `ast`
    Only parents and identifiers are updated in the returned ast (e.g., inferred types are not preserved)
    """
    assert isinstance(ast, AST)
    ast_copy = deepcopy(ast)
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
    _copy_ast_fields(old_ast, new_ast)
    if old_ast.parent is not None:
        set_parents(new_ast)
        link_identifiers(new_ast)


def _copy_ast_fields(ast, ast_copy):
    ast_copy.line = ast.line
    ast_copy.column = ast.column
    ast_copy.modified_values = ast.modified_values
    ast_copy.read_values = ast.read_values