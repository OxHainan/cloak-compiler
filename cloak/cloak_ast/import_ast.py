from cloak.cloak_ast.visitor.visitor import AstVisitor
from cloak.cloak_ast.ast import ImportDirective, ContractDefinition, PragmaDirective
from cloak.cloak_ast.build_ast import build_ast


class ImportAstVisitor(AstVisitor): 
    def __init__(self, input_file_path='', traversal='post', log=False):
        super().__init__(traversal=traversal, log=log)
        self.import_files = []
        self.import_contract_dict = dict()
        self.unit_dict = dict()
        self.input_file_path = input_file_path

    def visitImportDirective(self, ast: ImportDirective):
        path = str(ast.path)
        path = path.replace('"', '')
        if path[0] == '.':
            absolute_path = self.get_file_dir(self.input_file_path) + path[1:]
        self.import_files.insert(0, absolute_path)
        for symbol, alias in ast.aliases.items():
            if self.import_contract_dict.get(absolute_path) == None:
                self.import_contract_dict[absolute_path] = set()
            self.import_contract_dict[absolute_path].add((symbol, alias))

        if ast.unitAlias != None:
            self.unit_dict[absolute_path] = str(ast.unitAlias)

    def get_file_dir(self, input_file_path):
        return input_file_path[0:input_file_path.rfind("/")]


class RenameAstVisitor(AstVisitor):
    def __init__(self, input_file_path='', symbol_contracts=[], unic_dict=dict(), traversal='post', log=False):
        super().__init__(traversal=traversal, log=log)
        self.rename_contracts = dict()
        for symbol_contract in symbol_contracts:
            if symbol_contract[1] != '':
                self.rename_contracts[symbol_contract[0]] = symbol_contract[1]
        self.input_file_path = input_file_path
        self.unic_dict = unic_dict

    def visitContractDefinition(self, ast: ContractDefinition):
        # rename contract name by alias
        if ast.idf.name in self.rename_contracts:
            ast.idf.name = self.rename_contracts[ast.idf.name]
        # change contract name format from 'unit.contract' to 'unit_contract'
        if self.input_file_path in self.unic_dict:
            ast.idf.name = self.unic_dict[self.input_file_path] + "_" + ast.idf.name

class ImportProcessor:
    def __init__(self):
        self.code_list = []
    
    def process_file(self, input_file_path, import_contracts, unit_dict):
        # get import relation
        code = ''
        with open(input_file_path, 'r') as f:
            code = f.read()
        cloak_ast = build_ast(code)
        rav = RenameAstVisitor(input_file_path, import_contracts, unit_dict)
        rav.visit(cloak_ast)
        iav = ImportAstVisitor(input_file_path)
        iav.visit(cloak_ast)

        # remove useless code(import, unimported contract, progrma)
        useless_contract = lambda u: isinstance(u, ContractDefinition) and (len(import_contracts) != 0 and not self.in_contract_or_alias(u.idf.name, import_contracts, 0))
        cloak_ast.units[:] = filter(lambda u: not useless_contract(u), cloak_ast.units)
        import_command = lambda u: isinstance(u, ImportDirective)
        cloak_ast.units[:] = filter(lambda u: not import_command(u), cloak_ast.units)
        pragma_command = lambda u: isinstance(u, PragmaDirective)
        cloak_ast.units[:] = filter(lambda u: not pragma_command(u), cloak_ast.units)
        new_code = cloak_ast.code()
        if new_code not in self.code_list:
            self.code_list.insert(0, new_code)
        # process next import file
        for import_file in iav.import_files:
            self.process_file(import_file, iav.import_contract_dict[import_file] if iav.import_contract_dict.get(import_file) != None else [], iav.unit_dict)
    
    def in_contract_or_alias(self, elem, contract_or_alias, index):
        for contract, alias in contract_or_alias:
            if elem == contract or elem == alias:
                return True
        return False

def import_ast(init_file):
    ip = ImportProcessor()
    ip.process_file(init_file, [], dict())
    # merge code
    merged_code = ''
    for code in ip.code_list:
        merged_code += code
    return merged_code

