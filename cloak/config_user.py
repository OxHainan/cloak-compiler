"""
This module defines the cloak options which are configurable by the user via command line arguments.

The argument parser in :py:mod:`.__main__` uses the docstrings, type hints and _values for the help
 strings and the _values fields for autocompletion

WARNING: This is one of the only cloak modules that is imported before argcomplete.autocomplete is called. \
For performance reasons it should thus not have any import side-effects or perform any expensive operations during import.
"""
from typing import Any, Union

from appdirs import AppDirs


def _check_is_one_of(val: str, legal_vals):
    if val not in legal_vals:
        raise ValueError(f'Invalid config value {val}, must be one of {legal_vals}')


def _type_check(val: Any, t):
    if not isinstance(val, t):
        raise ValueError(f'Value {val} has wrong type (expected {t})')


class UserConfig:
    def __init__(self):
        self._appdirs = AppDirs('zkay', appauthor=False, version=None, roaming=True)

        # User configuration
        # Each attribute must have a type hint and a docstring for correct help strings in the commandline interface.
        # If 'Available Options: [...]' is specified, the options are used for autocomplete suggestions.

        # Global defaults
        self._blockchain_service_address: str = ''
        self._indentation: str = ' ' * 4
        self._opt_solc_optimizer_runs: int = 50
        self._opt_eval_constexpr_in_circuit: bool = True
        self._log_dir: str = self._appdirs.user_log_dir
        self._verbosity: int = 1


    @property
    def blockchain_service_address(self) -> str:
        """
        Address of the deployed pki contract.

        Must be specified for backends other than w3-eth-tester.
        This library can be deployed using ``cloak deploy-service``.
        """
        return self._blockchain_service_address

    @blockchain_service_address.setter
    def blockchain_service_address(self, val: str):
        _type_check(val, str)
        self._blockchain_service_address = val


    @property
    def indentation(self) -> str:
        """Specifies the identation which should be used for the generated code output."""
        return self._indentation

    @indentation.setter
    def indentation(self, val: str):
        _type_check(val, str)
        self._indentation = val

    @property
    def opt_solc_optimizer_runs(self) -> int:
        """SOLC: optimize for how many times to run the code"""
        return self._opt_solc_optimizer_runs

    @opt_solc_optimizer_runs.setter
    def opt_solc_optimizer_runs(self, val: int):
        _type_check(val, int)
        self._opt_solc_optimizer_runs = val

    @property
    def opt_eval_constexpr_in_circuit(self) -> bool:
        """
        If true, literal expressions are folded and the result is baked into the circuit as a constant
        (as opposed to being evaluated outside the circuit and the result being moved in as an additional circuit input)
        """
        return self._opt_eval_constexpr_in_circuit

    @opt_eval_constexpr_in_circuit.setter
    def opt_eval_constexpr_in_circuit(self, val: bool):
        _type_check(val, bool)
        self._opt_eval_constexpr_in_circuit = val

    @property
    def log_dir(self) -> str:
        """Path to default log directory."""
        return self._log_dir

    @log_dir.setter
    def log_dir(self, val: str):
        _type_check(val, str)
        import os
        if not os.path.exists(val):
            os.makedirs(val)
        self._log_dir = val

    @property
    def verbosity(self) -> int:
        """
        If 0, no output
        If 1, normal output
        If 2, verbose output

        This includes for example snark key- and proof generation output and
        information about intermediate transaction simulation steps.
        """
        return self._verbosity

    @verbosity.setter
    def verbosity(self, val: int):
        _type_check(val, int)
        self._verbosity = val
