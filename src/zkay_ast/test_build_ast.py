from parameterized import parameterized_class

from zkay_ast.build_ast import build_ast
from examples.examples import all_examples
from examples.test_examples import TestExamples
from solidity_parser.emit import normalize_code


@parameterized_class(('name', 'example'), all_examples)
class TestBuildAST(TestExamples):

	def test_build_ast(self):
		ast = build_ast(self.example.code())
		self.assertIsNotNone(ast)

	def test_to_ast_and_back(self):
		# ast
		ast = build_ast(self.example.code())
		# back to string
		new_code = str(ast)
		self.assertIn(self.example.name(), new_code)
		new_code = normalize_code(new_code)
		# reference
		reference = normalize_code(self.example.code())
		# check
		self.assertEqual(reference, new_code)
