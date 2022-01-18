pragma solidity ^0.8.0;

// Description: Record and grade exam answers
// Domain: Teaching
contract Scores {
    // A exam with functions in different privacy type

    final address examinator;
    uint passPoints;
	uint@tee avgScore;
    uint@tee totalPoints;
    uint@tee totalExaminees;

    mapping (uint => uint@examinator) solutions;
    mapping (address => mapping (uint => uint@examinator)) answers;
    mapping (address => uint@examinator) points;
    mapping (address!x => bool@x) passed;
	
	// PUB
    constructor(
		uint pass,
		uint point
	) {
        examinator = me;
        passPoints = pass;
    }

    // CT
    function recordAnswer(
		uint task, 
		uint@me ans
	) public {
        answers[me][task] = reveal(ans, examinator);
        passed[me] = false;
        points[me] = 0;
    }

    // CT
    function gradeTask(
		uint task, 
		address examinee
	) public {
        require(examinator == me);
        uint@me p;
        p = answers[examinee][task] == solutions[task] ? 1 : 0;
        points[examinee] = points[examinee] + p;
        passed[examinee] = reveal(points[examinee] > passPoints, examinee);
    }

    // TEE
    function setSolution(
		uint task, 
		uint@tee sol
	) public {
        require(examinator == me);
        solutions[task] = sol;
    }

    // TEE
    function getAverage() public returns (uint) {
        // avgScore = totalPoints / totalExaminees;
        avgScore = totalPoints - totalExaminees;
        return avgScore;
    }

	// TEE
    function rateAnswer(
		uint task, 
		address examinee
	) public {
        require(examinator == me);
        uint@me p;
        p = answers[examinee][task] == solutions[task] ? 1 : 0;
        points[examinee] = points[examinee] + p;
        passed[examinee] = points[examinee] > passPoints;
        totalPoints = totalPoints + p;
        totalExaminees = totalExaminees + 1;
    }

}
