from parameterized import parameterized_class

from cloak.examples.examples import all_examples
from cloak.tests.utils.test_examples import TestExamples
from cloak.ast.process_ast import get_processed_ast


@parameterized_class(('name', 'example'), all_examples)
class TestProcessAST(TestExamples):

    def test_process_ast(self):
        ast = get_processed_ast(self.example.code(), type_check=False)
        self.assertIsNotNone(ast)
