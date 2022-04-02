from __future__ import annotations

from cloak.cloak_ast import ast
from cloak.cloak_ast.ast import ConstructorOrFunctionDefinition
from cloak.cloak_ast.visitor.transformer_visitor import AstTransformerVisitor
from cloak.compiler.privacy.transformation.cloak_contract_transformer import CloakTransformer
from cloak.policy.privacy_policy import PrivacyPolicy
from cloak.config import cfg

class PrivateContractTransformer(AstTransformerVisitor):
    """
    1. TODO: remove the @owner annotations.
    2. add get_states/set_states function for tee
    """

    def __init__(self, pp: PrivacyPolicy, depoly_constract: str, family_tree: dict, log=False):
        self.pp = pp
        self.depoly_constract = depoly_constract
        self.family_tree = family_tree
        super().__init__(log)

    def visitSourceUnit(self, su: ast.SourceUnit):
        su.privacy_policy = self.pp
        cts = CloakTransformer(False, self.depoly_constract, self.family_tree)
        for cd in su.contracts:
            # remove constructor
            is_constructor = lambda u: isinstance(u, ConstructorOrFunctionDefinition) and u.is_constructor
            cd.units[:] = filter(lambda u: not is_constructor(u), cd.units)
            cd.extra_tail_parts += [
                cts.get_states(su, cd, False),
                cts.set_states(su, cd, False),
            ]