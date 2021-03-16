pragma solidity ^0.5.0;

// Description: Track consumed energy 
// Domain: Energy
contract PowerGrid {
    address master;
    mapping (address => uint) consumed;
    mapping (address => uint) total;

    // PUB
    constructor() public {
        master = msg.sender;
    }

    // TEE
    function register_consumed(uint amount) public {
        consumed[msg.sender] = consumed[msg.sender] + amount;
    }

    // TEE
    function declare_total() public {
        total[msg.sender] = total[msg.sender] + consumed[msg.sender];
    }
}