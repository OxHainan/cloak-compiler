import os

# get relevant paths
from utils.run_command import run_command

# could also be 'solc'
# solc = 'solcjs'
solc = 'solc'  # solc-0.4.24


def compile_solidity_code(code: str, output_directory: str):
	if not os.path.exists(output_directory):
		os.makedirs(output_directory)

	source_name = 'code.sol'
	file_path = os.path.join(output_directory, source_name)
	with open(file_path, "w") as f:
		f.write(code)

	return compile_solidity_to_bin(output_directory, source_name)


def compile_solidity_to_bin(path: str, source_file: str, output_directory: str = None):
	if not output_directory:
		output_directory = path
	output_directory = os.path.abspath(output_directory)
	return run_command([solc, '--bin', '--overwrite',  '-o', output_directory, source_file], path)

def compile_solidity_to_combined_json(path: str, source_file: str, output_directory: str = None):
	if not output_directory:
		output_directory = path
	output_directory = os.path.abspath(output_directory)
	return run_command([solc, '--combined-json', 'bin,hashes', '-o', output_directory, source_file], path)