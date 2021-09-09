from cloak.cloak_ast.visitor.visitor import AstVisitor
from cloak.errors.exceptions import CloakCompilerError
from cloak.cloak_ast.ast import AnnotatedTypeName, TypeName, Mapping, Array, Identifier
from cloak.cloak_ast import ast as ast_module


# e.g.
#   @all: {"type": "all"}
#   @tee: {"type": "tee"}
#   @me: {"type": "tee"}
#   @a: {"type": "a"}
#   mapping(address!x => uint@x):
#       {"type": "mapping", "key_variable": "x", "value": {"type": "x"}}
#   mapping(address => uint@a):
#       {"type": "mapping", "key_variable": "", "value": {"type": "a"}}
#   mapping(address!x => mapping(address => uint@x)):
#       {"type": "mapping", "key_variable": "x", "value": <NESTED MAPPING OWNER>}
class OwnerFormatter():
    def visit(self, ast: AnnotatedTypeName):
        if isinstance(ast.type_name, Mapping):
            return self.visitMapping(ast.type_name)
        elif isinstance(ast.type_name, Array):
            raise CloakCompilerError("format owner failed, don't support array owner")
        else:
            return {"type": ast.privacy_annotation.code()}

    def visitMapping(self, ast: Mapping):
        key_variable = getattr(ast.key_label, "name", None) or ast.key_label or ""
        return {"type": "mapping", "key_variable": key_variable, "value": self.visit(ast.value_type)}


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
            raise CloakCompilerError("format type failed, don't support {}", ast.code())
        return f(ast)

    def visitNumberTypeName(self, ast: ast_module.NumberTypeName):
        return {"type": "number", "signed": ast.signed, "bit_size": ast.elem_bitwidth}

    def visitAddressTypeName(self, ast: ast_module.AddressTypeName):
        return {"type": "address"}

    def visitMapping(self, ast: Mapping):
        return {"type": "mapping", "parameters": [self.visit(ast.key_type), self.visit(ast.value_type.type_name)]}

