pragma solidity ^0.4.0;

/*contract ERC20_Interface {
  function totalSupply() public constant returns (uint supply);
  function balanceOf(address _owner) public constant returns (uint balances);
  function transfer(address _to, uint _value) public returns (bool success);
  function transferFrom(address _from, address _to, uint _value) public returns (bool success);
  function approve(address _spender, uint _value) public returns (bool success);
  function allowance(address _owner, address _spender) public constant returns (uint remaining);

  event Transfer(address indexed _from, address indexed _to, uint _value);
  event Approval(address indexed _owner, address indexed _spender, uint _value);
}*/

// Description: Buy and transfer secret amount of ERC20 tokens
// Domain: Finance
contract ERC20Token {
    uint supply = 0;
    mapping(address!x => uint@x) balances;
    mapping(address => mapping(address => uint)) allowances;
	
	mapping (address => mapping (address!x => uint@x)) pending;
    mapping (address => bool) has_pending;
    mapping (address => bool) registered;

    constructor(uint s) public {
        supply = s;
        balances[me] = supply;
		registered[me] = true;
        has_pending[me] = false;
    }

	function register() public {
        balances[me] = 0;
        registered[me] = true;
        has_pending[me] = false;
    }

    function buy(uint amount) public payable {
        require(registered[me]);
        // amount should actually be computed based on the payed value
        balances[me] = balances[me] + amount;
    }

    function send_tokens(uint@me v, address receiver) public {
        require(registered[me] && registered[receiver]);
        require(!has_pending[receiver]);
        require(reveal(balances[me] > v, all));
        balances[me] = balances[me] - v;
        pending[me][receiver] = reveal(v, receiver);
        has_pending[receiver] = true;
    }

	function receive_tokens(address sender) public {
        require(registered[me] && registered[sender]);
        require(has_pending[me]);
        balances[me] = balances[me] + pending[sender][me];
        pending[sender][me] = 0;
        has_pending[me] = false;
    }

	// Get the account balances of another account with address _owner
    function balanceOf(address@tee _owner) public returns (uint) {
		require(me == _owner);
        return balances[_owner];
    }

	// Get the total token supply
    function totalSupply() public returns (uint) {
        return supply;
    }

    // Send _value amount of tokens to address _to
    function transfer(address _to, uint@tee _value) public returns (bool) {
        require(reveal(balances[me] >= _value, all));
		balances[me] = balances[me] - _value;
		balances[_to] = balances[_to] + _value;
        return true;
    }
	
    // Send _value amount of tokens from address _from to address _to
	// Should be TEE
    function transferFrom(
        address _from,
        address@tee _to,
        uint@tee _value
    ) public returns (bool) {
        bool ret = false;
        require(reveal(balances[_from] >= _value, all));
        if (
            allowances[_from][me] >= _value
        ) {
            balances[_from] = balances[_from] - _value;
            balances[_to] = balances[_to] + _value;
            allowances[_from][me] = allowances[_from][me] - _value;
            ret = true;
        } 
        return ret;
    }

    // Allow _spender to withdraw from your account, multiple times, up to the
    // _value amount. If this function is called again it overwrites the current
    // allowance with _value
    function approve(address _spender, uint@tee _value) public returns (bool) {
        // We permit allowances to be larger than balances
        allowances[me][_spender] = _value;
        return true;
    }

    // Returns the amount which _spender is still allowed to withdraw from _owner
    function allowance(address _owner, address _spender)
        public
        returns (uint)
    {
        return allowances[_owner][_spender];
    }

}
