"""
This module stores the solidity library contracts used by zkay contracts
"""
import os
from textwrap import dedent

from cloak.config import cfg
from cloak.utils.helpers import read_file

def get_service_contract() -> str:
    """Return all verification contract libraries combined into single string"""
    code = ''
    code += read_file(os.path.join(os.path.dirname(os.path.realpath(__file__)), 'CloakService.sol'))
            
    return f'{code}'
