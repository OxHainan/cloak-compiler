// pragma solidity ^0.5.0;
// contract PowerGrid {
//
// 	final address master;
// 	mapping(address!x => uint@x) consumed;
// 	mapping(address => uint@master) total;
//
// 	constructor() public  {
// 		master = me;
// 	}
//
// 	function init() public  {
// 		consumed[me] = reveal(0, me);
// 	}
//
// 	function register_consumed(uint@me genTeeResult0) public  {
// 		consumed[me] = genTeeResult0;
// 	}
//
// 	function declare_total(uint@me genTeeResult0) public  {
// 		total[me] = genTeeResult0;
// 	}
// }

contract PowerGrid {
    CloakService cloakVerifier;
    // Verify_init Verify_init_var;
    address master;
    mapping(address => uint256) consumed;
    mapping(address => uint256) total;

    uint256 codeHash;
    uint256 policyHash;
    uint256 funcHash;

    constructor(CloakService _cloakVerifier) public {
        cloakVerifier = _cloakVerifier;
        master = msg.sender;
    }

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

    function syncState(
        uint256[] memory report,
        uint256 _master,
        address[] memory consumed_key,
        uint256[] memory consumed_value,
        address[] memory total_key,
        uint256[] memory total_value
    ) public {
        require(consumed_key.length == consumed_value.length);
        require(total_key.length == total_value.length);

        uint256 len = 1 + consumed_key.length + total_key.length;
        uint256 idx = 0;

        uint256[] memory oldState = new uint256[](len);
        oldState[0] = uint256(master);
        idx++;
        for (uint256 i = 0; i < consumed_key.length; i++) {
            oldState[idx + i] = consumed[consumed_key[i]];
        }
        idx += consumed_key.length;

        for (uint256 i = 0; i < total_key.length; i++) {
            oldState[idx + i] = (total[total_key[i]]);
        }
        idx += total_key.length;

        uint256[] memory result = new uint256[](len);
        idx = 0;
        result[0] = _master;
        idx++;
        for (uint256 i = 0; i < consumed_key.length; i++) {
            result[idx + i] = consumed_value[i];
        }
        idx += consumed_key.length;

        for (uint256 i = 0; i < total_key.length; i++) {
            result[idx + i] = total_value[i];
        }
        idx += total_key.length;

        require(
            cloakVerifier.verify(
                report,
                codeHash,
                policyHash,
                funcHash,
                oldState,
                result
            )
        );

        for (uint256 i = 0; i < consumed_key.length; i++) {
            consumed[consumed_key[i]] = consumed_value[i];
        }

        for (uint256 i = 0; i < total_key.length; i++) {
            total[total_key[i]] = total_value[i];
        }
    }

    function register_consumed(
        uint256[] memory report,
        address[] memory consumed_key,
        uint256[] memory consumed_value
    ) public {
        uint256 funcHash = 0x0;
        require(consumed_key.length == consumed_value.length);

        uint256[] memory oldState = new uint256[](1);
        for (uint256 i = 0; i < consumed_key.length; i++) {
            oldState[i] = consumed[consumed_key[i]];
        }

        uint256[] memory result = new uint256[](1);
        for (uint256 i = 0; i < consumed_key.length; i++) {
            result[i] = consumed_value[i];
        }

        require(
            cloakVerifier.verify(
                report,
                codeHash,
                policyHash,
                funcHash,
                oldState,
                result
            )
        );
        // change state
        consumed[msg.sender] = consumed_value[0];
    }

    function declare_total(
        uint256[] memory report,
        address[] memory total_key,
        uint256[] memory total_value
    ) public {
        uint256 funcHash = 0x0;
        require(total_key.length == total_value.length);

        uint256[] memory oldState = new uint256[](1);
        for (uint256 i = 0; i < total_key.length; i++) {
            oldState[i] = total[total_key[i]];
        }

        uint256[] memory result = new uint256[](1);
        for (uint256 i = 0; i < total_key.length; i++) {
            result[i] = total_value[i];
        }

        require(
            cloakVerifier.verify(
                report,
                codeHash,
                policyHash,
                funcHash,
                oldState,
                result
            )
        );
        // change state
        total[msg.sender] = total_value[0];
    }
}