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
import solcx
from contextlib import contextmanager
from copy import deepcopy
from typing import Tuple, List, Type, Dict, Optional, Any, ContextManager

from cloak import my_logging
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
from cloak.cloak_ast.split_ast import split_ast
from cloak.solidity_parser.parse import MyParser
from cloak.cloak_ast.import_ast import import_ast


def compile_cloak_file(input_file_path: str, output_dir: str, put_enable: bool, combined_json: bool, output_contract: bool, **kwargs):
    """
    Parse, type-check and compile the given cloak contract file.

    :param input_file_path: path to the cloak contract file
    :param output_dir: path to a directory where the compilation output should be generated
    :param put_enable: reserve PUT function if true
    :param combined_json: output a combined json file if true
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
        compile_cloak(code, input_file_path, output_dir, put_enable, combined_json, output_contract, **kwargs)


def compile_cloak(code: str, input_file_path: str, output_dir: str, put_enable: bool, combined_json: bool, output_contract: bool, **kwargs):
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
    # cloak_filename = 'contract.cloak'
    # _dump_to_output(code, output_dir, cloak_filename)

    # get ast
    # cloak_ast = build_ast(code)

    # process import
    merged_code = import_ast(input_file_path)
    cloak_ast = build_ast(merged_code)

    # # dump solified code to output
    # _dump_to_output(cloak_ast.code(for_solidity=True), output_dir, "contract.sol")

    # spilt ast for every single contract
    contract_codes, family_tree = split_ast(cloak_ast)

    for contract_name, contract_code in contract_codes.items():
        print('')
        print("Generating code for contract", contract_name)
        # build a new ast for every contract(including inheritance)
        contract_ast = build_ast(contract_code)

        # check with solc 
        check_with_solc(contract_ast) 
        
        # process ast
        process_ast(contract_ast)
        with print_step("Generate privacy policy"):
            contract_ast.generated_policy = format_policy(json.dumps(contract_ast.privacy_policy, cls=PrivacyPolicyEncoder, separators=(',', ':')))

        # Write private contract file
        with print_step('Write private solidity code'):
            output_filename = contract_name + '_private_contract.sol'
            private_ast = build_ast(contract_code)
            pct = PrivateContractTransformer(contract_ast.privacy_policy, contract_name, family_tree)
            pct.visit(private_ast)
            # for code Hash
            contract_ast.private_contract_code = private_ast.code(for_solidity=True)
            private_solidity_code_str = contract_ast.private_contract_code
            if output_contract:
                _dump_to_output(private_solidity_code_str, output_dir, output_filename)

        # Contract transformation
        with print_step("Transforming cloak contract"):
            public_ast = transform_ast(deepcopy(contract_ast), put_enable, contract_name, family_tree)

        # Write public contract file
        with print_step('Write public solidity code'):
            output_filename = contract_name + '_public_contract.sol'
            public_solidity_code_str = public_ast.code(True)
            if output_contract:
                _dump_to_output(public_solidity_code_str, output_dir, output_filename)

        # output contract json
        if combined_json:
            output_json = dict()
            output_json["private"] = solcx.compile_source(private_solidity_code_str, ["abi","bin"])["<stdin>:" + contract_name]
            output_json["public"] = solcx.compile_source(public_solidity_code_str, ["abi","bin"])["<stdin>:" + contract_name]
            output_json["policy"] = json.loads(contract_ast.generated_policy)
            _dump_to_output(json.dumps(output_json, cls=PrivacyPolicyEncoder, indent=2), output_dir, contract_name + '.json')


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

def format_policy(old_policy: str) -> str:
    new_policy = deepcopy(json.loads(old_policy))
    function_json = new_policy["functions"]
    for function in function_json:
        if "read" in function:
            for read in function["read"]:
                read["keys"].sort(key=lambda x: x)
            function["read"].sort(key=lambda x: x['name'])
        if "mutate" in function:
            for mutate in function["mutate"]:
                mutate["keys"].sort(key=lambda x: x)
            function["mutate"].sort(key=lambda x: x['name'])
    new_policy["functions"] = function_json
    return json.dumps(new_policy, separators=(',', ':'))