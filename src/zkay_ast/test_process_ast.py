from parameterized import parameterized_class

from zkay_ast.process_ast import get_processed_ast
from examples.examples import all_examples
from examples.test_examples import TestExamples


@parameterized_class(('name', 'example'), all_examples)
class TestProcessAST(TestExamples):

	def test_process_ast(self):
		ast = get_processed_ast(self.example.code(), type_check=False)
		self.assertIsNotNone(ast)
