contract SupplyChain {
    address[] parties;
    uint256[] bids;

    function biddingProcure(uint256 bid) public {
        parties.push(msg.sender);
        bids.push(bid);
    }

    function settleProcure() 
    public 
    returns (address winner, uint256 sPrice) {
        uint256 mPrice = bids[0];
        sPrice = bids[0];
        winner = parties[0];
        for (uint256 i = 1; i < parties.length; i++) {
            if (bids[i] < mPrice) {
                winner = parties[i];
                sPrice = mPrice;
                mPrice = bids[i];
            } else if (bids[i] < sPrice) {
                sPrice = bids[i];
            }
        }
    }
}
