pragma solidity ^0.5.0;

// Description: Buy and transfer secret amount of ERC20 tokens
// Domain: Finance
contract ERC20Token {
    uint supply = 0;
    mapping(address!x => uint@x) balances;
    mapping(address => mapping(address => uint)) allowances;
	
	mapping (address => mapping (address!x => uint@x)) pending;
    mapping (address => bool) has_pending;
    mapping (address => bool) registered;

	// ZKP
    constructor(uint s) public {
        supply = s;
        balances[me] = supply;
		registered[me] = true;
        has_pending[me] = false;
    }

    // PUB: Returns the amount which _spender is still allowed to withdraw from _owner
    function allowance(
		address _owner, 
		address _spender
	) public returns (uint)
    {
        return allowances[_owner][_spender];
    }

	// PUB
	function register() public {
        registered[me] = true;
        has_pending[me] = false;
    }

    // PUB: Get the account balances of another account with address _owner
    function balanceOf(
		address _owner
	) public returns (uint) {
		require(me == _owner);
        balances[_owner];
    }

	// PUB: Get the total token supply
    function totalSupply() public returns (uint) {
        return supply;
    }

	// ZKP
    function buy(uint amount) public payable {
        require(registered[me]);
        // amount should actually be computed based on the payed value
        balances[me] = balances[me] + amount;
    }

	// ZKP
    function send_tokens(uint@me v, address receiver) public {
        require(registered[me] && registered[receiver]);
        require(!has_pending[receiver]);
        require(reveal(balances[me] > v, all));
        balances[me] = balances[me] - v;
        pending[me][receiver] = reveal(v, receiver);
        has_pending[receiver] = true;
    }

	// ZKP
	function receive_tokens(address sender) public {
        require(registered[me] && registered[sender]);
        require(has_pending[me]);
        balances[me] = balances[me] + pending[sender][me];
        pending[sender][me] = 0;
        has_pending[me] = false;
    }

    // TEE: Send _value amount of tokens to address _to
    function transfer(address _to, uint@tee _value) public returns (bool) {
        require(balances[me] >= _value);
		balances[me] = balances[me] - _value;
		balances[_to] = balances[_to] + _value;
        return true;
    }
	
    // TEE: Send _value amount of tokens from address _from to address _to
    function transferFrom(
        address _from,
        address _to,
        uint@tee _value
    ) public returns (bool) {
        bool ret = false;
        require(balances[_from] >= _value);
        if (allowances[_from][me] >= _value) {
            balances[_from] = balances[_from] - _value;
            balances[_to] = balances[_to] + _value;
            allowances[_from][me] = allowances[_from][me] - _value;
            ret = true;
        } 
        return ret;
    }

    // TEE: Allow _spender to withdraw from your account, multiple times, up to the
    // _value amount. If this function is called again it overwrites the current
    // allowance with _value
    function approve(
		address _spender, 
		uint@tee _value
	) public returns (bool) {
        // We permit allowances to be larger than balances
        allowances[me][_spender] = _value;
        return true;
    }


}
