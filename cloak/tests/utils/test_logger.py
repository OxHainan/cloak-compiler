import json
import warnings

from cloak import my_logging
from cloak.tests.cloak_unit_test import CloakTestCase
from cloak.utils.helpers import read_file

default_log_file = my_logging.get_log_file(label='TestLogger')


class TestLogger(CloakTestCase):
    def test_logger(self):
        # ignore warnings
        warnings.simplefilter("ignore")

        # log something
        log_file = default_log_file + '_basic_test'
        my_logging.prepare_logger(log_file)
        my_logging.info("ABCD")
        my_logging.shutdown()

        # check logfile
        success = 'ABCD' in read_file(log_file + '_info.log')
        self.assertTrue(success)

    def test_data(self):
        log_file = default_log_file + '_data_test'
        my_logging.prepare_logger(log_file)
        my_logging.data('key', 2)
        my_logging.info('ABCD')
        my_logging.shutdown()

        # check
        content = read_file(log_file + '_data.log')
        d = json.loads(content)
        self.assertEqual(d['key'], 'key')
        self.assertEqual(d['value'], 2)
        self.assertTrue('ABCD' not in content)
