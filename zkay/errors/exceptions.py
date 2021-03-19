"""
This module contains the definitions of all exceptions which may be publicly raised by zkay
"""


class CloakCompilerError(Exception):
    """
    Error during compilation
    """
    pass


class ZkaySyntaxError(CloakCompilerError):
    """
    Error during parsing / AST construction"
    """


class PreprocessAstException(CloakCompilerError):
    """
    Error during ast pre-processing"
    """
    pass


class AnalysisException(CloakCompilerError):
    """
    Error during ast analysis"
    """
    pass


class TypeCheckException(CloakCompilerError):
    """
    Error during type checking"
    """
    pass
