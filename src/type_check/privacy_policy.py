import json
from typing import Any
from type_check.type_exceptions import TypeException
from type_check import type_pure
from zkay_ast.ast import Mapping, Array, CodeVisitor, StateVariableDeclaration

FUNC_INPUTS = "inputs"
FUNC_READ = "read"
FUNC_MUTATE = "mutate"
FUNC_OUTPUTS = "outputs"

ppv = CodeVisitor()  # for privacy policy


class FunctionPolicy():
    fpolicy = {}

    def __init__(self, function_name: str = ""):
        """
        init function info
        """
        self.fpolicy["type"] = "function"
        self.fpolicy["name"] = function_name

    def get_item(self, item, var):
        """
        Return the element if it's same with the var, else return None
        """
        if not item in ["inputs", "read", "mutate", "outputs"]:
            raise ValueError(f"'{item}' not support now!")

        if not item in self.fpolicy:
            return None
        if not var.idf:
            return None

        for e in self.fpolicy[item]:
            if e["name"] == var.idf.name:
                return e
        return None

    def add_item(self, item, var, key=""):
        if not item in ["inputs", "read", "mutate", "outputs"]:
            raise ValueError(f"'{item}' not support now!")
        if not item in self.fpolicy:
            self.fpolicy[item] = []

        elem = self.get_item(item, var)
        if not elem:
            n_elem = {}
            if var.idf:
                n_elem["name"] = var.idf.name
            if not isinstance(var, StateVariableDeclaration):
                # Ignore the 'type' and 'owner' of state variables
                # since it's already set in 'states'
                n_elem["type"] = type_pure.delete_cloak_annotation(
                    ppv.visit(var.annotated_type.type_name))
                # TODO: handle array
                if isinstance(var.annotated_type.type_name, Mapping):
                    n_elem["owner"] = ppv.visit(var.annotated_type.type_name)
                elif isinstance(var.annotated_type.type_name, Array):
                    n_elem["owner"] = ppv.visit(var.annotated_type)
                else:
                    n_elem["owner"] = ppv.visit(
                        var.annotated_type.privacy_annotation)
            self.fpolicy[item].append(n_elem)
            elem = n_elem

        if isinstance(var, StateVariableDeclaration) \
            and (isinstance(var.annotated_type.type_name, Mapping)
                 or isinstance(var.annotated_type.type_name, Array)):
            if not "keys" in elem:
                elem["keys"] = []
            _k = type_pure.delete_cloak_annotation(key)
            if not _k in elem["keys"]:
                elem["keys"].append(_k)


class PrivacyPolicyEncoder(json.JSONEncoder):

    def default(self, o: Any) -> Any:
        try:
            if isinstance(o, FunctionPolicy):
                return o.fpolicy
            return o.policy
        except TypeError:
            pass
        # Let the base class default method raise the TypeError
        return json.JSONEncoder.default(self, o)


class PrivacyPolicy(json.JSONEncoder):
    policy = {}

    def __init__(self, contract_name: str = ""):
        """
        init contract info
        """
        self.policy["contract"] = contract_name
        self.policy["states"] = []
        self.policy["functions"] = []

    def add_state(self, var):
        item = "states"
        if not item in ["states"]:
            raise ValueError(f"'{item}' not support now!")
        if not item in self.policy:
            self.policy[item] = []
        n_elem = {}
        if var.idf:
            n_elem["name"] = var.idf.name
        n_elem["type"] = type_pure.delete_cloak_annotation(
            ppv.visit(var.annotated_type.type_name))
        # TODO: handle array
        if isinstance(var.annotated_type.type_name, Mapping):
            n_elem["owner"] = ppv.visit(var.annotated_type.type_name)
        elif isinstance(var.annotated_type.type_name, Array):
            n_elem["owner"] = ppv.visit(var.annotated_type)
        else:
            n_elem["owner"] = ppv.visit(var.annotated_type.privacy_annotation)

        self.policy[item].append(n_elem)

    def add_function(self, func_policy: FunctionPolicy):
        """
        add function privacy policy
        """
        item = "functions"
        if not item in self.policy:
            self.policy[item] = []
        self.policy[item].append(func_policy)


if __name__ == "__main__":
    pp = PrivacyPolicy("newContract")
    print(json.dumps(pp, cls=PrivacyPolicyEncoder))
