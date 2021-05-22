from unittest import TestCase
from abc import ABCMeta

from cloak.config import cfg


class CloakTestCase(TestCase, metaclass=ABCMeta):
    def __init__(self, methodName: str = ...) -> None:
        super().__init__(methodName)
        self.old_was_unit_test = None

    def setUp(self) -> None:
        self.old_was_unit_test = cfg.is_unit_test
        cfg._is_unit_test = True
        super().setUp()

    def tearDown(self) -> None:
        super().tearDown()
        cfg._is_unit_test = self.old_was_unit_test
