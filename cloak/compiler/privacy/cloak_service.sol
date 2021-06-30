// 版权所有 © 2020 牛津（海南）区块链研究院

contract CloakService {
    enum WorkerStatus {NULL, ACTIVE, OFFLINE, DECOMMISSIONED, COMPROMISED}
    address public teeAddr;

    struct Worker {
        uint256 status;
        uint256 workerType;
        bytes32 organizationId;
        bytes32[] appTypeIds;
        string details;
    }

    // WorkerID -> Worker
    mapping(bytes32 => Worker) private m_workers;

    // workerType -> organizationId -> appTypeId -> WorkerID[]
    mapping(uint256 => mapping(bytes32 => mapping(bytes32 => bytes32[]))) m_workersDB;

    constructor() public {}

    function workerRegister(
        bytes32 workerID,
        uint256 workerType,
        bytes32 organizationId,
        bytes32[] memory appTypeIds,
        string memory details
    ) public {
        Worker storage worker = m_workers[workerID];

        require(worker.status == uint256(WorkerStatus.NULL));
        worker.status = uint256(WorkerStatus.ACTIVE);
        worker.workerType = workerType;
        worker.organizationId = organizationId;
        worker.appTypeIds = appTypeIds;
        worker.details = details;

        require(workerType != uint256(0));
        require(organizationId != bytes32(0));
        m_workersDB[uint256(0)][bytes32(0)][bytes32(0)].push(workerID);
        m_workersDB[workerType][bytes32(0)][bytes32(0)].push(workerID);
        m_workersDB[uint256(0)][organizationId][bytes32(0)].push(workerID);
        m_workersDB[workerType][organizationId][bytes32(0)].push(workerID);

        for (uint256 p = 0; p < appTypeIds.length; ++p) {
            require(appTypeIds[p] != bytes32(0));
            m_workersDB[uint256(0)][bytes32(0)][appTypeIds[p]].push(workerID);
            m_workersDB[workerType][bytes32(0)][appTypeIds[p]].push(workerID);
            m_workersDB[uint256(0)][organizationId][appTypeIds[p]].push(
                workerID
            );
            m_workersDB[workerType][organizationId][appTypeIds[p]].push(
                workerID
            );
        }
    }

    function workerUpdate(bytes32 workerID, string memory details) public {
        require(m_workers[workerID].status != uint256(WorkerStatus.NULL));
        m_workers[workerID].details = details;
    }

    function workerSetStatus(bytes32 workerID, uint256 status) public {
        require(m_workers[workerID].status != uint256(WorkerStatus.NULL));
        require(status != uint256(WorkerStatus.NULL));
        m_workers[workerID].status = status;
    }

    function workerLookUp(
        uint256 workerType,
        bytes32 organizationId,
        bytes32 appTypeId
    )
        public
        view
        returns (
            uint256 totalCount,
            uint256 lookupTag,
            bytes32[] memory ids
        )
    {
        return workerLookUpNext(workerType, organizationId, appTypeId, 0);
    }

    function workerLookUpNext(
        uint256 workerType,
        bytes32 organizationId,
        bytes32 appTypeId,
        uint256 /*lookUpTag*/
    )
        public
        view
        returns (
            uint256 totalCount,
            uint256 newLookupTag,
            bytes32[] memory ids
        )
    {
        bytes32[] storage matchs =
            m_workersDB[workerType][organizationId][appTypeId];
        return (matchs.length, 0, matchs);
    }

    function workerRetrieve(bytes32 workerID)
        public
        view
        returns (
            uint256 status,
            uint256 workerType,
            bytes32 organizationId,
            bytes32[] memory appTypeIds,
            string memory details
        )
    {
        Worker storage worker = m_workers[workerID];
        return (
            worker.status,
            worker.workerType,
            worker.organizationId,
            worker.appTypeIds,
            worker.details
        );
    }

    modifier onlyTEE(address tx_origin) {
        // check tx.origin is TEE
        require(true);
        _;
    }

    function verify(
        uint256[3] memory report,
        uint256 codeHash,
        uint256 policyHash,
        uint256 oldStateHash
    ) public view onlyTEE(tx.origin) returns (bool) {
        // check tx.origin is TEE
        if (report.length < 3) {
            return false;
        }

        if (
            report[0] == codeHash &&
            report[1] == policyHash &&
            report[2] == oldStateHash
        ) {
            return true;
        }

        return false;
    }

    function getHash(uint256[] memory preimage) public pure returns (uint256) {
        // start with just the first element
        bytes32 hash = bytes32(preimage[0]);

        // add one value after the other to the hash
        for (uint256 i = 1; i < preimage.length; i++) {
            bytes memory packed = abi.encode(hash, preimage[i]);
            hash = sha256(packed);
        }

        uint256 hash_int = uint256(hash);
        return hash_int;
    }

    // TODO: add tee proof
    function setTEEAddress() public {
        teeAddr = msg.sender;
    }

    function getTEEAddress() public view returns (address) {
        return teeAddr;
    }

}
