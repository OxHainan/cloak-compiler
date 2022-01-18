"""
This module exposes functionality to compile and package cloak code
"""
import importlib
import json
import os
import re
import shutil
import sys
import tempfile
import zipfile
from contextlib import contextmanager
from copy import deepcopy
from typing import Tuple, List, Type, Dict, Optional, Any, ContextManager

from cloak import my_logging
from cloak.compiler.privacy import library_contracts
from cloak.compiler.privacy.transformation.cloak_contract_transformer import transform_ast
from cloak.compiler.privacy.transformation.private_contract_transformer import PrivateContractTransformer
from cloak.compiler.solidity.compiler import check_compilation
from cloak.config import cfg
from cloak.utils.helpers import read_file, lines_of_code, without_extension
from cloak.utils.progress_printer import print_step
from cloak.utils.timer import time_measure
from cloak.cloak_ast.process_ast import process_ast, check_with_solc
from cloak.cloak_ast.build_ast import build_ast
from cloak.policy.privacy_policy import PrivacyPolicyEncoder
from cloak.type_check.type_pure import delete_cloak_annotation


def compile_cloak_file(input_file_path: str, output_dir: str, put_enable : bool, **kwargs):
    """
    Parse, type-check and compile the given cloak contract file.

    :param input_file_path: path to the cloak contract file
    :param output_dir: path to a directory where the compilation output should be generated
    :raise CloakCompilerError: if any compilation stage fails
    """
    code = read_file(input_file_path)

    # log specific features of compiled program
    my_logging.data('originalLoc', lines_of_code(code))
    m = re.search(r'\/\/ Description: (.*)', code)
    if m:
        my_logging.data('description', m.group(1))
    m = re.search(r'\/\/ Domain: (.*)', code)
    if m:
        my_logging.data('domain', m.group(1))

    # compile
    with time_measure('compileFull'):
        compile_cloak(code, input_file_path, output_dir, put_enable, **kwargs)


def compile_cloak(code: str, input_file_path: str, output_dir: str, put_enable: bool, **kwargs):
    """
    Parse, type-check and compile the given cloak code.

    Note: If a SolcException is raised, this indicates a bug in cloak
          (i.e. cloak produced solidity code which doesn't compile, without raising a CloakCompilerError)

    :param code: cloak code to compile
    :param output_dir: path to a directory where the compilation output should be generated
    :raise CloakCompilerError: if any compilation stage fails
    """

    # input_file_dir, filename = os.path.split(input_file_path)
    # Copy cloak code to output
    cloak_filename = 'contract.cloak'
    _dump_to_output(code, output_dir, cloak_filename)

    # get ast
    cloak_ast = build_ast(code)

    # # dump solified code to output
    # _dump_to_output(cloak_ast.code(for_solidity=True), output_dir, "contract.sol")

    # check with solc
    check_with_solc(cloak_ast)

    # process ast
    process_ast(cloak_ast)

    with print_step("Generate privacy policy"):
        cloak_ast.generated_policy = json.dumps(cloak_ast.privacy_policy, cls=PrivacyPolicyEncoder, indent=2)
        _dump_to_output(cloak_ast.generated_policy, output_dir, f'policy.json')

    # Write private contract file
    with print_step('Write private solidity code'):
        output_filename = 'private_contract.sol'
        private_ast = build_ast(code)
        PrivateContractTransformer(cloak_ast.privacy_policy).visit(private_ast)
        # for code Hash
        cloak_ast.private_contract_code = private_ast.code(for_solidity=True)
        solidity_code_output = _dump_to_output(cloak_ast.private_contract_code, output_dir, output_filename)

    # Contract transformation
    with print_step("Transforming cloak contract"):
        ast = transform_ast(deepcopy(cloak_ast), put_enable)
        ast.policy_path = os.path.join(output_dir, "policy.json")

    # # Dump libraries
    # with print_step("Write library contract files"):
    #     _dump_to_output(library_contracts.get_service_contract(), output_dir, f'{cfg.service_contract_name}.sol')

    # Write public contract file
    with print_step('Write public solidity code'):
        output_filename = 'public_contract.sol'
        solidity_code_output = _dump_to_output(ast.code(True), output_dir, output_filename)


def _dump_to_output(content: str, output_dir: str, filename: str, dryrun_solc=False) -> str:
    """
    Dump 'content' into file 'output_dir/filename' and optionally check if it compiles error-free with solc.

    :raise SolcException: if dryrun_solc is True and there are compilation errors
    :return: dumped content as string
    """

    path = os.path.join(output_dir, filename)
    with open(path, 'w') as f:
        f.write(content)
    if dryrun_solc:
        check_compilation(path, show_errors=False)
    return content
