from utils.timer import time_measure
from zkay_ast.analysis.alias_analysis import alias_analysis as a
from zkay_ast.build_ast import build_ast
from zkay_ast.pointers.parent_setter import set_parents
from zkay_ast.pointers.symbol_table import link_identifiers as link
from zkay_ast.visitor.return_checker import check_return as r
from type_check.type_setter import privacy_type_set as p
from type_check.type_checker import type_check as t


def get_processed_ast(code, output_directory: str, parents=True, link_identifiers=True, check_return=True, alias_analysis=True, type_check=True):
	with time_measure("typeCheck"):
		ast = build_ast(code)
		process_ast(ast, output_directory, parents, link_identifiers, check_return, alias_analysis, type_check)
	return ast


def process_ast(ast, output_directory, parents=True, link_identifiers=True, check_return=True, alias_analysis=True, type_check=True):
	if parents:
		set_parents(ast)
	if link_identifiers:
		link(ast)
	if check_return:
		r(ast)
	if alias_analysis:
		a(ast)
	if type_check:
		p(ast, output_directory)
		t(ast)
