# definition of hash function needed for interaction with zokrates

hash_function = """
function get_hash(uint[] memory preimage) public pure returns (uint128[2] memory) {
    // start with just the first element
    bytes32 hash = bytes32(preimage[0]);
    
    // add one value after the other to the hash
    for (uint i=1; i<preimage.length; i++) {
        bytes memory packed = abi.encode(hash, preimage[i]);
        hash = sha256(packed);
    }
    
    // split result into 2 parts (needed for zokrates)
    uint hash_int = uint(hash);
    uint128 part0 = uint128(hash_int / 0x100000000000000000000000000000000);
    uint128 part1 = uint128(hash_int);
    return [part0, part1];
}
"""

tee_function = """    
modifier onlyMaster() {
    require(msg.sender == master);
    _;
}

function setCodeHash(uint256 _codeHash) external onlyMaster {
    codeHash = _codeHash;
}

function setPolicyHash(uint256 _policyHash) external onlyMaster {
    policyHash = _policyHash;
}

function setTeeAppHash(uint256 _teeAppHash) external onlyMaster {
    teeAppHash = _teeAppHash;
}"""