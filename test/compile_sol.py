
from compiler.solidity.compiler import compile_solidity_to_bin
from utils.timer import time_measure
import my_logging

def compile_contract(contract_name):
    try:
        with time_measure(f"{contract_name} verifier"):
            compile_solidity_to_bin("/cloak-compiler/test", f"/cloak-compiler/eval-ccs2019/examples/{contract_name}/compiled/{contract_name}.sol")

        with time_measure(f"{contract_name} target function"):
            compile_solidity_to_bin("/cloak-compiler/test", f"/cloak-compiler/eval-ccs2019/examples/{contract_name}/compiled/pured-{contract_name}.sol")
    except Exception as e:
        raise e

if __name__ == "__main__":
    # create log directory
    log_file = my_logging.get_log_file(filename='time_solc', parent_dir="/cloak-compiler/test", include_timestamp=False, label=None)
    my_logging.prepare_logger(log_file)

    contracts_list = [
        "power-grid",
        "insurance",
        "bid",
        "supply-chain",
        "scores",
        "erc20",
        "ox-token",
        "ox-oracle",
        "ox-htlc"
    ]
    for c in contracts_list:
        compile_contract(c)