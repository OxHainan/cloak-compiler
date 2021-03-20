from parameterized import parameterized_class

from cloak.examples.examples import all_examples
from cloak.tests.utils.test_examples import TestExamples
from cloak.ast.analysis.side_effects import has_side_effects
from cloak.ast.process_ast import get_processed_ast


@parameterized_class(('name', 'example'), all_examples)
class TestSideEffects(TestExamples):

    def has_side_effects(self):
        if self.name in ['Addition', 'Simple']:
            return True
        elif self.name in ['MappingNameConflict', 'Empty']:
            return False
        else:
            return None

    def test_side_effects(self):
        ast = get_processed_ast(self.example.code(), type_check=False, solc_check=False)
        e = has_side_effects(ast)
        if self.has_side_effects() is not None:
            self.assertEqual(e, self.has_side_effects())
