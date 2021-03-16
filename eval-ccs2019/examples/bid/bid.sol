pragma solidity ^0.5.0;

// Description: Place bets and claim winnings
// Domain: Gambling
contract Bid {
    final address master;
    uint@master secret;
    mapping(address!x => uint@x) bets;

    bool revealed;
    uint published_secret;
    address winner;

    // ZKP
    constructor(uint@me s) public {
        master = me;
        secret = s;
        revealed = false;
    }

    // ZKP
    function publish_secret() public {
        require(master == me);
        require(!revealed);
        revealed = true;
        published_secret = reveal(secret, all);
    }

    // TEE
    function bet(uint@tee val) public {
        require(me != master);
        require(!revealed);
        // bets[me] = val;
        bets[me] = bets[me] + val;
    }

    // TEE
    function claim_winner() public {
        require(revealed && (me != master));
        require(bets[me] == published_secret);
        winner = me;
        // send money here
        uint@tee balance = 0;
        balance = secret + balance;
    }
}