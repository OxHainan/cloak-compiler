/* Copyright 2019 iExec Blockchain Tech
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

pragma solidity ^0.5.0;

contract WorkerRegistry {
    enum WorkerStatus {NULL, ACTIVE, OFFLINE, DECOMMISSIONED, COMPROMISED}

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
    }

    function verify(
        uint256[] report,
        uint256 codeHash,
        uint256 policyHash,
        uint256 funcHash,
        uint256[] oldState,
        uint256[] result
    ) public view onlyTEE(tx.origin) returns (bool) {
        // check tx.origin is TEE
        if (report.length < 5) {
            return false;
        }

        if (
            report[0] == codeHash &&
            report[1] == policyHash &&
            report[2] == funcHash &&
            report[3] == get_hash(oldState) &&
            report[4] == get_hash(result)
        ) {
            return ture;
        }

        return false;
    }

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

}
