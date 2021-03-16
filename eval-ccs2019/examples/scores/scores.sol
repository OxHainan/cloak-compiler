pragma solidity ^0.5.0;

// Description: Record and grade exam answers
// Domain: Teaching
contract Scores {
    // A exam with functions in different privacy type

    final address _examinator;
    uint _passPoints;
	uint@tee _avgScore;
    uint@tee _totalPoints;
    uint@tee _totalExaminees;

    mapping (uint => uint@_examinator) solutions;
    mapping (address => mapping (uint => uint@_examinator)) answers;
    mapping (address => uint@_examinator) points;
    mapping (address!x => bool@x) passed;
	
	// ZKP
    constructor(
		uint pass,
		uint point
	) public {
        _examinator = me;
        _passPoints = pass;
		_avgScore = point;
    }

    // ZKP
    function recordAnswer(
		uint task, 
		uint@me ans
	) public {
        answers[me][task] = reveal(ans, _examinator);
        passed[me] = false;
        points[me] = 0;
    }

    // ZKP
    function gradeTask(
		uint task, 
		address examinee
	) public {
        require(_examinator == me);
        uint@me p;
        p = answers[examinee][task] == solutions[task] ? 1 : 0;
        points[examinee] = points[examinee] + p;
        passed[examinee] = reveal(points[examinee] > _passPoints, examinee);
    }

    // TEE
    function setSolution(
		uint task, 
		uint@tee sol
	) public {
        require(_examinator == me);
        solutions[task] = sol;
    }

    // TEE
    function getAverage() public returns (uint) {
        _avgScore = _totalPoints / _totalExaminees;
    }

	// TEE
    function rateAnswer(
		uint task, 
		address examinee
	) public {
        require(_examinator == me);
        uint@me p;
        p = answers[examinee][task] == solutions[task] ? 1 : 0;
        points[examinee] = points[examinee] + p;
        passed[examinee] = points[examinee] > _passPoints;
        _totalPoints = _totalPoints + p;
        _totalExaminees = _totalExaminees + 1;
    }

}