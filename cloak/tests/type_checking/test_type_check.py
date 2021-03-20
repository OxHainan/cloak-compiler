from parameterized import parameterized_class

from cloak.examples.examples import all_examples, type_error_examples, final_use_before_write
from cloak.tests.utils.test_examples import TestExamples
from cloak.tests.zkay_unit_test import ZkayTestCase
from cloak.type_check.type_checker import type_check
from cloak.type_check.type_exceptions import TypeException
from cloak.ast.process_ast import get_processed_ast


@parameterized_class(('name', 'example'), all_examples)
class TestTypeCheck(TestExamples):

    def test_type_check(self):
        ast = get_processed_ast(self.example.code(), type_check=False)
        # type check
        type_check(ast)


@parameterized_class(('name', 'example'), type_error_examples)
class TestNoTypeCheck(TestExamples):

    def test_no_type_check(self):
        ast = get_processed_ast(self.example.code(), type_check=False, solc_check=False)
        # type check
        with self.assertRaises(TypeException):
            type_check(ast)


class TestFinal(ZkayTestCase):

    def test_final(self):
        ast = get_processed_ast(final_use_before_write.code(), type_check=False, solc_check=False)

        with self.assertRaises(TypeException):
            type_check(ast)
