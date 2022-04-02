from cloak.cloak_ast.visitor.visitor import AstVisitor
from cloak.cloak_ast.ast import ContractDefinition


class SplitAstVisitor(AstVisitor): 

    def __init__(self, traversal='post', log=False):
        super().__init__(traversal=traversal, log=log)

    def visitContractDefinition(self, ast: ContractDefinition):
        parent_list = []
        for inheritanceSpecifier in ast.inheritanceSpecifiers:
            path = inheritanceSpecifier.path[0]
            parent_list.append(path)
            family_tree[ast.idf.name] = parent_list
        single_contract_code[ast.idf.name] = ast.code()

single_contract_code = {}
family_tree = {}

contract_set = set()
def generate_contract_code(contract):
    if contract in contract_set:
        return ""
    else:
        contract_set.add(contract)
    if contract not in family_tree:
        return single_contract_code[contract]
    code = ""
    for parent_contract in family_tree[contract]:
        code += generate_contract_code(parent_contract) + '\n'
    code += single_contract_code[contract]
    return code

def split_ast(ast):
    sav = SplitAstVisitor()
    sav.visit(ast)
    contract_codes = {}
    for contract in single_contract_code:
        contract_set.clear()
        contract_codes[contract] = generate_contract_code(contract)
    return contract_codes, family_tree