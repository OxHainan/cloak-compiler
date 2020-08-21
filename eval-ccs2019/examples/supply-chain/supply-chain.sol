pragma solidity ^0.4.0;

// Description: Track and audit cash receipts
// Domain: Retail
contract SupplyChain {
	final address business;
    mapping(uint => uint@business) in_receipts;
    mapping(uint => uint@business) out_receipts;
    uint@business income;
    mapping(address!x => uint@x) balances;
	mapping(address!x => 
	    mapping(address => uint@x)) receivables;

	constructor() public {
        business = me;
        income = 0;
    }
    // ZKP
    function recordReceivable(address owner, 
        uint@me value) {
        receivables[owner][me] = reveal(value, owner);
    }
    // TEE
    function finishReceivable(address owner, 
        uint@tee value) public returns (bool) {
		bool ret = false;
        if (balances[me] >= value 
			&& receivables[owner][me] >= value) 
		{
		    receivables[owner][me] 
		        = receivables[owner][me] - value;
		    balances[me] = balances[me] - value; 
		    ret = true;
        }
        return ret;
    }
	// ZKP
	function give_receipt(uint id, uint@me amount) public {
        require(business == me);
        out_receipts[id] = amount;
        income = income + amount;
    }
	// ZKP
    function receive_receipt(uint id, uint@me amount) public {
        in_receipts[id] = reveal(amount, business);
    }
	// ZKP
    function check(uint id) public {
        require(business == me);
        require(reveal(in_receipts[id] == out_receipts[id], all));
    }
}
