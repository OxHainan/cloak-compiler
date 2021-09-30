from cloak.cloak_ast.visitor.visitor import AstVisitor
from cloak.errors.exceptions import CloakCompilerError
from cloak.cloak_ast.ast import AnnotatedTypeName, TypeName, Mapping, Array, Identifier
from cloak.cloak_ast import ast as ast_module


# e.g.
#   @all: {"owner": "all"}
#   @tee: {"owner": "tee"}
#   @me: {"owner": "tee"}
#   @a: {"owner": "a"}
#   mapping(address!x => uint@x): {"owner": "mapping", "var": "x", "var_pos": 0}
#   mapping(address => uint@a): {"owner": "mapping", "var": "a", "var_pos": -1}
#   mapping(address!x => mapping(address => uint@x)): {"owner": "mapping", "var": "x", "var_pos": 0}
#   mapping(address => mapping(address!x => uint@x)): {"owner": "mapping", "var": "x", "var_pos": 1}
class OwnerFormatter():
    def visit(self, ast: AnnotatedTypeName):
        if isinstance(ast.type_name, Mapping):
            return self.visitMapping(ast)
        else:
            return {"owner": ast.privacy_annotation.code()}

    def visitMapping(self, ast: AnnotatedTypeName):
        var_pos = -1
        pos = 0
        while isinstance(ast.type_name, Mapping):
            m_type = ast.type_name
            key_variable = getattr(m_type.key_label, "name", None) or m_type.key_label or None
            if key_variable is not None:
                var_pos = pos
            pos += 1
            ast = m_type.value_type
        value = self.visit(ast)
        if value["owner"] == "all":
            return value
        return {"owner": "mapping", "var": value["owner"], "var_pos": var_pos}


# e.g.
#   uint: {"type": "number", "signed": False, "bit_size": 256}
#   address: {"type": "address"}
#   mapping(address => uint): {"type": "mapping", "parameters": [<ADDRESS>, <UINT>]}
#   mapping(address => mapping(address => uint)): {"type": "mapping", "parameters": [<ADDRESS>, <MAPPING>]}
#   uint[]: {"type": "array", "parameters": [<UINT>]}
#   uint[2]: {"type": "array", "parameters": [<UINT>, 2]}
class TypeFormatter(AstVisitor):
    def __init__(self):
        super().__init__()

    def visit(self, ast: TypeName):
        assert isinstance(ast, ast_module.TypeName)
        f = self.get_visit_function(ast.__class__)
        if f is None:
            return {"type": ast.code()}
        return f(ast)

    def visitNumberTypeName(self, ast: ast_module.NumberTypeName):
        return {"type": "number", "signed": ast.signed, "bit_size": ast.elem_bitwidth}

    def visitAddressTypeName(self, ast: ast_module.AddressTypeName):
        return {"type": "address"}

    def visitMapping(self, ast: Mapping):
        res = {"type": "mapping", "key_type": self.visit(ast.key_type), "value_type": self.visit(ast.value_type.type_name)}
        # set depth
        if res["value_type"]["type"] == "mapping":
            res["depth"] = res["value_type"]["depth"] + 1
        else:
            res["depth"] = 1
        return res

    def visitArray(self, ast: ast_module.Array):
        if ast.expr:
            return {"type": "array", "value_type": self.visit(ast.value_type), "len": int(ast.expr.code())}
        return {"type": "array", "value_type": self.visit(ast.value_type)}

