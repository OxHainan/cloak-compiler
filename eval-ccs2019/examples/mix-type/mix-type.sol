pragma solidity ^0.5.0;

// Description: Record and grade exam answers
// Domain: Teaching
contract MixType {
    // A exam with functions in different privacy type

    final address examinator;
    uint pass_points;
    uint@tee balance;

    mapping (uint => uint@examinator) solutions;
    mapping (address => mapping (uint => uint@examinator)) answers;
    mapping (address => uint@examinator) points;
    mapping (address!x => bool@x) passed;
	
    constructor(uint pass) public {
        examinator = me;
        pass_points = pass;
    }

    // should be TEE
    function exec_tee(uint@me amount) public {
        require(tee == me);
        balance = balance + amount;
    }

    // // should be MPC
    // function exec_mpc(uint@me amount) public {
    //     balance = balance + amount;
    // }

    // should be ZKP
    function exec_zkp(uint@me amount2) public {
        balance = reveal(amount2, tee);
    }

    // should be ZKP
    function set_solution(uint task, uint@me sol) public {
        require(examinator == me);
        solutions[task] = sol;
    }

    // // should be ZKP
    // function record_answer(uint task, uint@me ans) public {
    //     answers[me][task] = reveal(ans, examinator);
    //     passed[me] = false;
    //     points[me] = 0;
    // }

    // // should be ZKP
    // function grade_task(uint task, address examinee) public {
    //     require(examinator == me);
    //     uint@me p;
    //     p = answers[examinee][task] == solutions[task] ? 1 : 0;
    //     points[examinee] = points[examinee] + p;
    //     passed[examinee] = reveal(points[examinee] > pass_points, examinee);
    // }

}