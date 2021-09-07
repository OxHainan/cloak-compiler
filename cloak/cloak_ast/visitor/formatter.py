from cloak.cloak_ast.visitor.visitor import AstVisitor
from cloak.errors.exceptions import CloakCompilerError
from cloak.cloak_ast.ast import AnnotatedTypeName, Mapping, Array, Identifier


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
    def __init__(self):
        super().__init__()

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
