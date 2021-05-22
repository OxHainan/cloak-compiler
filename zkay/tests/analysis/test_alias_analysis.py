import re

from parameterized import parameterized_class

from zkay.zkay_ast.visitor.transformer_visitor import AstTransformerVisitor
from zkay.examples.examples import analysis, all_examples
from zkay.tests.utils.test_examples import TestExamples
from zkay.tests.zkay_unit_test import ZkayTestCase
from zkay.zkay_ast.analysis.alias_analysis import alias_analysis
from zkay.zkay_ast.ast import Statement, Comment, BlankLine
from zkay.zkay_ast.build_ast import build_ast
from zkay.zkay_ast.pointers.parent_setter import set_parents
from zkay.zkay_ast.pointers.symbol_table import link_identifiers


class TestAliasAnalysisDetail(ZkayTestCase):

    def test_alias_analysis(self):
        # perform analysis
        ast = build_ast(analysis.code())
        set_parents(ast)
        link_identifiers(ast)
        alias_analysis(ast)

        # generate string, including analysis results
        v = AnalysisCodeVisitor()
        s = v.visit(ast)
        # next statement can be enabled to determine the computed output
        # print(s)

        # check output
        self.maxDiff = None
        self.assertMultiLineEqual(analysis.code(), re.sub(" +\n", "\n", s.code()))


@parameterized_class(('name', 'example'), all_examples)
class TestAliasAnalysis(TestExamples):

    def test_alias_analysis(self):
        # perform analysis
        ast = build_ast(self.example.code())
        set_parents(ast)
        link_identifiers(ast)
        alias_analysis(ast)


class AnalysisCodeVisitor(AstTransformerVisitor):
    def visitStatement(self, ast: Statement):
        return [BlankLine(), Comment(str(ast.before_analysis)), self.visit_children(ast), Comment(str(ast.after_analysis)), BlankLine()]

    def visitBlock(self, ast: Statement):
        return [BlankLine(), Comment(str(ast.before_analysis)), self.visit_children(ast), Comment(str(ast.after_analysis))]
