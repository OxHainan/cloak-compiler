#!/usr/bin/env python3
# PYTHON_ARGCOMPLETE_OK
from typing import Any
import argcomplete, argparse
import os

from argcomplete.completers import FilesCompleter, DirectoriesCompleter

from cloak.config_user import UserConfig


def parse_config_doc():
    import textwrap
    from typing import get_type_hints
    __ucfg = UserConfig()

    docs = {}
    for name, prop in vars(UserConfig).items():
        if name.startswith('_') or not isinstance(prop, property):
            continue
        t = get_type_hints(prop.fget)['return']
        doc = prop.__doc__
        choices = None
        if hasattr(__ucfg, f'_{name}_values'):
            choices = getattr(__ucfg, f'_{name}_values')
        default_val = getattr(__ucfg, name)
        docs[name] = (
            f"type: {t}\n\n"
            f"{textwrap.dedent(doc).strip()}\n\n"
            f"Default value: {default_val}", t, default_val, choices)
    return docs


def parse_arguments():
    class ShowSuppressedInHelpFormatter(argparse.RawTextHelpFormatter):
        def add_usage(self, usage, actions, groups, prefix=None):
            if usage is not argparse.SUPPRESS:
                actions = [action for action in actions if action.metavar != '<cfg_val>']
                args = usage, actions, groups, prefix
                self._add_item(self._format_usage, args)

    main_parser = argparse.ArgumentParser(prog='cloak')
    cloak_files = ('cloak', 'sol')
    cloak_package_files = ('zkp', )
    config_files = ('json', )

    msg = 'Path to local configuration file (defaults to "config.json" in cwd). ' \
          'This file (if it exists), overrides settings defined in the global configuration.'
    main_parser.add_argument('--config-file', default='config.json', metavar='<config_file>', help=msg).completer = FilesCompleter(config_files)

    # Shared 'config' parser
    config_parser = argparse.ArgumentParser(add_help=False)
    msg = 'These parameters can be used to override settings defined (and documented) in config_user.py'
    cfg_group = config_parser.add_argument_group(title='Configuration Options', description=msg)

    # Expose config_user.py options via command line arguments, they are supported in all parsers
    cfg_docs = parse_config_doc()

    def add_config_args(parser, arg_names):
        for name in arg_names:
            doc, t, defval, choices = cfg_docs[name]

            if t is bool:
                if defval:
                    parser.add_argument(f'--no-{name.replace("_", "-")}', dest=name, help=doc, action='store_false')
                else:
                    parser.add_argument(f'--{name.replace("_", "-")}', dest=name, help=doc, action='store_true')
            elif t is int:
                parser.add_argument(f'--{name.replace("_", "-")}', type=int, dest=name, metavar='<cfg_val>', help=doc)
            else:
                arg = parser.add_argument(f'--{name.replace("_", "-")}', dest=name, metavar='<cfg_val>', help=doc,
                                          choices=choices)
                if name.endswith('dir'):
                    arg.completer = DirectoriesCompleter()
    add_config_args(cfg_group, cfg_docs.keys())

    solc_version_help = 'cloak defaults to the latest installed\n' \
          'solidity version supported by the current cloak version.\n\n' \
          'If you need to use a particular minor release (e.g. because \n' \
          'the latest release is broken or you need determinism for testing)\n' \
          'you can specify a particular solc version (e.g. v0.5.12) via this argument.\n' \
          'Note: An internet connection is required if the selected version is not installed'

    subparsers = main_parser.add_subparsers(title='actions', dest='cmd', required=True)

    # 'compile' parser
    compile_parser = subparsers.add_parser('compile', parents=[config_parser], help='Compile a cloak contract.', formatter_class=ShowSuppressedInHelpFormatter)
    msg = 'The directory to output the compiled contract to. Default: Current directory'
    compile_parser.add_argument('-o', '--output', default=os.getcwd()+'/build', help=msg, metavar='<output_directory>').completer = DirectoriesCompleter()
    compile_parser.add_argument('input', help='The cloak source file', metavar='<cloak_file>').completer = FilesCompleter(cloak_files)
    compile_parser.add_argument('--log', action='store_true', help='enable logging')
    compile_parser.add_argument('--solc-version', help=solc_version_help, metavar='<cfg_val>')
    compile_parser.add_argument('--put-enable', action='store_true', help='enable PUT and reserve public function')
    compile_parser.add_argument('--combined-json', action='store_true', help='output a single json document containing information compiled by solc')
    compile_parser.add_argument('--contract', action='store_true', help='output public contract and private contract')

    # 'check' parser
    typecheck_parser = subparsers.add_parser('check', parents=[config_parser], help='Only type-check, do not compile.', formatter_class=ShowSuppressedInHelpFormatter)
    typecheck_parser.add_argument('input', help='The cloak source file', metavar='<cloak_file>').completer = FilesCompleter(cloak_files)
    typecheck_parser.add_argument('--solc-version', help=solc_version_help, metavar='<cfg_val>')

    # 'solify' parser
    msg = 'Output solidity code which corresponds to cloak code with all privacy features and comments removed, ' \
          'useful in conjunction with analysis tools which operate on solidity code.)'
    solify_parser = subparsers.add_parser('solify', parents=[config_parser], help=msg, formatter_class=ShowSuppressedInHelpFormatter)
    solify_parser.add_argument('input', help='The cloak source file', metavar='<cloak_file>').completer = FilesCompleter(cloak_files)

    subparsers.add_parser('version', help='Display cloak version information')
    subparsers.add_parser('update-solc', help='Install latest compatible solc version (requires internet connection)')

    # parse
    argcomplete.autocomplete(main_parser, always_complete_options=False)
    a = main_parser.parse_args()
    return a


def main():
    # parse arguments
    a = parse_arguments()

    from cloak.config_version import Versions

    if a.cmd == 'version':
        print(Versions.CLOAK_VERSION)
        return

    if a.cmd == 'update-solc':
        try:
            import solcx
            solcx.install_solc_pragma(Versions.CLOAK_SOLC_VERSION_COMPATIBILITY.expression)
        except Exception as e:
            print(f'ERROR: Error while updating solc\n{e}')
        return

    from pathlib import Path

    import cloak.frontend as frontend
    from cloak import my_logging
    from cloak.config import cfg
    from cloak.utils.helpers import read_file, save_to_file
    from cloak.errors.exceptions import CloakCompilerError
    from cloak.my_logging.log_context import log_context
    from cloak.utils.progress_printer import fail_print, success_print
    from cloak.cloak_ast.process_ast import get_processed_ast

    # Load configuration files
    try:
        cfg.load_configuration_from_disk(a.config_file)
    except Exception as e:
        with fail_print():
            print(f"ERROR: Failed to load configuration files\n{e}")
            exit(42)

    # Support for overriding any user config setting via command line
    # The evaluation order for configuration loading is:
    # Default values in config.py -> Site config.json -> user config.json -> local config.json -> cmdline arguments
    # Settings defined at a later stage override setting values defined at an earlier stage
    override_dict = {}
    for name in vars(UserConfig):
        if name[0] != '_' and hasattr(a, name):
            val = getattr(a, name)
            if val is not None:
                override_dict[name] = val
    cfg.override_defaults(override_dict)

    # Solc version override
    if hasattr(a, 'solc_version') and a.solc_version is not None:
        try:
            cfg.override_solc(a.solc_version)
        except ValueError as e:
            with fail_print():
                print(f'Error: {e}')
            exit(10)
    print(f'Using solc version {Versions.SOLC_VERSION}')

    input_path = Path(a.input)
    if not input_path.exists():
        with fail_print():
            print(f'Error: input file \'{input_path}\' does not exist')
        exit(1)

    # if a.cmd == 'check':
    #     # only type-check
    #     print(f'Type checking file {input_path.name}:')

    #     code = read_file(str(input_path))
    #     try:
    #         get_processed_ast(code)
    #     except CloakCompilerError as e:
    #         with fail_print():
    #             print(f'{e}')
    #         exit(3)
    # elif a.cmd == 'solify':
    #     was_unit_test = cfg.is_unit_test
    #     cfg._is_unit_test = True  # Suppress other output
    #     try:
    #         _, fake_code = get_parsed_ast_and_fake_code(read_file(str(input_path)))
    #         print(fake_code)
    #     except CloakCompilerError as e:
    #         with fail_print():
    #             print(f'{e}')
    #         exit(3)
    #     finally:
    #         cfg._is_unit_test = was_unit_test
    #     exit(0)
    elif a.cmd == 'compile':
        # create output directory
        output_dir = Path(a.output).absolute()
        if not output_dir.exists():
            os.makedirs(output_dir)
        elif not output_dir.is_dir():
            with fail_print():
                print(f'Error: \'{output_dir}\' is not a directory')
            exit(2)

        # Enable logging
        if a.log:
            log_file = my_logging.get_log_file(filename='compile', include_timestamp=False, label=None)
            my_logging.prepare_logger(log_file)

        # only type-check
        print(f'Compiling file {input_path.name}:')

        # compile
        with log_context('inputfile', os.path.basename(a.input)):
            try:
                frontend.compile_cloak_file(str(input_path), str(output_dir), a.put_enable, a.combined_json, a.contract)
            except CloakCompilerError as e:
                with fail_print():
                    print(f'{e}')
                exit(3)
    else:
        raise NotImplementedError(a.cmd)

    with success_print():
        print("Finished successfully")


if __name__ == '__main__':
    main()
