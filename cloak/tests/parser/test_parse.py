from antlr4.tree.Trees import Trees
from parameterized import parameterized_class

from cloak.examples.examples import all_examples
from cloak.tests.utils.test_examples import TestExamples
from cloak.solidity_parser.parse import MyParser
from cloak.solidity_parser.parse import get_parse_tree


@parameterized_class(('name', 'example'), all_examples)
class TestParse(TestExamples):

    def test_parse(self):
        get_parse_tree(self.example.code())

    def test_to_string(self):
        p = MyParser(self.example.code())
        s = Trees.toStringTree(p.tree, None, p.parser)
        self.assertIsNotNone(s)
        self.assertIn(self.name, s)
