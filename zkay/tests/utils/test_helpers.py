from zkay.tests.zkay_unit_test import ZkayTestCase
from zkay.utils.helpers import lines_of_code

example_code = """pragma solidity ^0.6.0;

// Description: Record medical statistics on patients
// Domain: Healthcare
contract MedStats {
    final address hospital;
    uint@hospital count;
    mapping(address!x => bool@x) risk;
    
    constructor() public {
        hospital = me;
        count = 0;
    }
    
    function record(address pat, bool@me r) public {
        require(hospital == me);
        risk[pat] = reveal(r, pat);
        count = count + (r ? 1 : 0);
    }
    
    function check(bool@me r) public {
        require(reveal(r == risk[me], all));
    }
}"""


class TestHelpers(ZkayTestCase):

    def test_lines_of_code(self):
        loc = lines_of_code(example_code)
        self.assertEqual(loc, 22)
