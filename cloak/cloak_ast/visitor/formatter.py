from cloak.cloak_ast.visitor.visitor import AstVisitor
from cloak.errors.exceptions import CloakCompilerError
from cloak.cloak_ast.ast import AnnotatedTypeName, TypeName, Mapping, Array, Identifier
from cloak.cloak_ast import ast as ast_module


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

    def visitMapping(self, ast: Mapping):
        res = {"type": "mapping", "key_type": self.visit(ast.key_type), "value_type": self.visit(ast.value_type.type_name)}
        # set depth
        if res["value_type"]["type"] == "mapping":
            res["depth"] = res["value_type"]["depth"] + 1
        else:
            res["depth"] = 1
        return res

    def visitAddressPayableTypeName(self, ast: ast_module.AddressPayableTypeName):
        return {"type": "address", "payable": True}

    def visitArray(self, ast: ast_module.Array):
        if ast.expr:
            return {"type": "array", "value_type": self.visit(ast.value_type), "len": int(ast.expr.code())}
        return {"type": "array", "value_type": self.visit(ast.value_type)}

    def visitBytesTypeName(self, ast: ast_module.BytesTypeName):
        if ast.len:
            return {"type": "bytes", "len": ast.len}
        return {"type": "bytes"}

