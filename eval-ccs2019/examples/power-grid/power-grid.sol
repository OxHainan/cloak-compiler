pragma solidity ^0.5.0;

// Description: Track consumed energy 
// Domain: Energy
contract PowerGrid {
    final address master;
    mapping (address!x => uint@x) consumed;
    mapping (address => uint@master) total;

    // PUB
    constructor() public {
        master = me;
    }

    // ZKP
    function init() public {
        consumed[me] = 0;
    }

    // TEE
    function register_consumed(uint@tee amount) public {
        consumed[me] = consumed[me] + amount;
    }

    // TEE
    function declare_total() public {
        total[me] = total[me] + consumed[me];
    }
}