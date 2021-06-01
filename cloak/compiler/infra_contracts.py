import os

script_dir = os.path.dirname(os.path.realpath(__file__))
pki_contract_filename = 'PKI.sol'
pki_contract_template = os.path.join(script_dir, pki_contract_filename)

cloak_service_contract_filename = "tee_Verify_Service.sol"
cloak_service_template = os.path.join(script_dir, cloak_service_contract_filename)
