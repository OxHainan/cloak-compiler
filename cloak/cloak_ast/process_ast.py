from typing import Tuple, List
from cloak.utils.timer import time_measure

from cloak.compiler.solidity.compiler import check_for_zkay_solc_errors, SolcException
from cloak.config import cfg
from cloak.errors.exceptions import CloakCompilerError, PreprocessAstException, TypeCheckException, AnalysisException, \
    ZkaySyntaxError
from cloak.solidity_parser.parse import SyntaxException
from cloak.type_check.type_setter import set_type
from cloak.type_check.type_checker import check_type
from cloak.type_check.type_exceptions import TypeMismatchException, TypeException, RequireException, ReclassifyException
from cloak.utils.progress_printer import print_step
from cloak.cloak_ast.analysis.alias_analysis import analyze_alias
from cloak.cloak_ast.analysis.call_graph import analyze_call_graph
from cloak.cloak_ast.analysis.circuit_compatibility_checker import check_circuit_compliance
from cloak.cloak_ast.analysis.hybrid_function_detector import detect_hybrid_functions
from cloak.cloak_ast.analysis.loop_checker import check_loops
from cloak.cloak_ast.analysis.return_checker import check_return
from cloak.cloak_ast.analysis.side_effects import compute_modified_sets, check_for_undefined_behavior_due_to_eval_order
from cloak.cloak_ast.ast import AST, SourceUnit, AstException
from cloak.cloak_ast.build_ast import build_ast
from cloak.cloak_ast.pointers.parent_setter import set_parents
from cloak.cloak_ast.pointers.pointer_exceptions import UnknownIdentifierException
from cloak.cloak_ast.pointers.symbol_table import link_identifiers


def get_parsed_ast_and_fake_code(code, solc_check=True) -> Tuple[AST, str]:
    with print_step("Parsing"):
        try:
            ast = build_ast(code)
        except SyntaxException as e:
            raise ZkaySyntaxError(f'\n\nSYNTAX ERROR: {e}')

    from cloak.compiler.solidity.fake_solidity_generator import fake_solidity_code
    fake_code = fake_solidity_code(str(code))
    if solc_check:
        # Solc type checking
        with print_step("Type checking with solc"):
            try:
                check_for_zkay_solc_errors(code, fake_code)
            except SolcException as e:
                raise CloakCompilerError(f'{e}')
    return ast, fake_code


def get_processed_ast(code, parents=True, identifier_link=True, return_check=True, alias_analysis=True, type_check=True, solc_check=True) -> AST:
    ast, _ = get_parsed_ast_and_fake_code(code, solc_check=solc_check)

    # Cloak preprocessing and type checking
    with time_measure("typeCheck"):
        process_ast(ast, parents, identifier_link, return_check, alias_analysis, type_check)

    return ast


def process_ast(ast, parents=True, identifier_link=True, return_check=True, alias_analysis=True, type_check=True):
    with print_step("Preprocessing AST"):
        if parents:
            set_parents(ast)
        if identifier_link:
            try:
                link_identifiers(ast)
            except UnknownIdentifierException as e:
                raise PreprocessAstException(f'\n\nSYMBOL ERROR: {e}')
        try:
            if return_check:
                check_return(ast)
            if alias_analysis:
                analyze_alias(ast)
            analyze_call_graph(ast)
            compute_modified_sets(ast)
            check_for_undefined_behavior_due_to_eval_order(ast)
        except AstException as e:
            raise AnalysisException(f'\n\nANALYSIS ERROR: {e}')
    if type_check:
        with print_step("Checking type"):
            try:
                set_type(ast)
                detect_hybrid_functions(ast)
                check_type(ast)
                check_circuit_compliance(ast)
                check_loops(ast)
            except (TypeMismatchException, TypeException, RequireException, ReclassifyException) as e:
                raise TypeCheckException(f'\n\nCOMPILER ERROR: {e}')


def get_verification_contract_names(code_or_ast) -> List[str]:
    if isinstance(code_or_ast, str):
        ast = get_processed_ast(code_or_ast)
    else:
        ast = code_or_ast
    if not isinstance(ast, SourceUnit):
        raise CloakCompilerError('Invalid AST (no source unit at root)')

    vc_names = []
    for contract in ast.contracts:
        cname = contract.idf.name
        fcts = [fct for fct in contract.function_definitions + contract.constructor_definitions if fct.requires_verification_when_external and fct.has_side_effects]
        vc_names += [cfg.get_verification_contract_name(cname, fct.name) for fct in fcts]
    return vc_names
