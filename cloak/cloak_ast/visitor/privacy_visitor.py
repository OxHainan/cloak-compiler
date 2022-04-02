from cloak.cloak_ast.visitor.visitor import AstVisitor
from cloak.cloak_ast.ast import StateVariableDeclaration, ConstructorOrFunctionDefinition, Mapping, ContractDefinition, IndexExpr
from cloak.policy.privacy_policy import FunctionPolicy, PrivacyPolicy, FUNC_INPUTS, FUNC_READ, FUNC_MUTATE, FUNC_OUTPUTS


class PrivacyTypeVisitor(AstVisitor): 

    def __init__(self, traversal='post', log=False):
        super().__init__(traversal=traversal, log=log)
        self.privacy_policy = PrivacyPolicy()
        self.state_dict = {}
        self.state_queue = []

    def visitStateVariableDeclaration(self, ast: StateVariableDeclaration):
        self.state_queue.append(ast.idf.name)
        self.privacy_policy.add_state(ast)

    def visitConstructorOrFunctionDefinition(self, ast: ConstructorOrFunctionDefinition):
        new_fp = FunctionPolicy(ast.name, ast.privacy_type)

        for v in ast.parameters:
            new_fp.add_item(FUNC_INPUTS, v)

        for v in ast.read_values:
            if isinstance(v.target, StateVariableDeclaration) \
                    and isinstance(v.target.annotated_type.type_name, Mapping) \
                    and isinstance(v[2], IndexExpr):
                top_idx_expr, map_idx_expr, map_key = self.privacy_policy.get_visited_map_and_key(v[2])
                if new_fp.add_item(FUNC_READ, map_idx_expr.target, map_key):
                    new_fp.read_values.append(map_idx_expr.target)

        for v in ast.modified_values:
            if isinstance(v.target, StateVariableDeclaration):
                if not v.target.annotated_type.type_name.is_primitive_type():
                    if isinstance(v[2], IndexExpr):
                        top_idx_expr, map_idx_expr, map_key = self.privacy_policy.get_visited_map_and_key(v[2])
                        if new_fp.add_item(FUNC_MUTATE, map_idx_expr.target, map_key):
                            new_fp.mutate_values.append(map_idx_expr.target)

        for p in ast.return_parameters:
            new_fp.add_item(FUNC_OUTPUTS, p)

        ast.function_policy = new_fp
        self.privacy_policy.add_function(ast.function_policy)

    def visitContractDefinition(self, ast: ContractDefinition):
        self.state_dict[ast.idf.name] = self.state_queue
        self.state_queue = []
        self.privacy_policy.policy["contract"] = ast.idf.name
        ast.get_related_sourceuint().privacy_policy = self.privacy_policy


def generate_policy(ast):
    # generate privacy policy
    ptv = PrivacyTypeVisitor()
    ptv.visit(ast)
    ast.privacy_policy.cal_slot()
    ast.privacy_policy.sort_states()
