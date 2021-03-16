import re

def replace_reveal(cloak_code: str):
    """
    Replace the function 'reveal' in Cloak source code to the value 
    whose ownership is changed.

    @params cloak_code: Cloak source code, which is Solidity with 
            cloak privacy annotation
    """
    reveal_pattern = re.compile('reveal\((.*),\s?\w*\)')
    matched_reveals = reveal_pattern.search(cloak_code)
    new_code = cloak_code

    while matched_reveals:
        new_code = new_code.replace(
            matched_reveals.group(),
            f'{matched_reveals.group(1)}'
            )

        matched_reveals = reveal_pattern.search(new_code)

    return new_code


def replace_me(cloak_code: str):
    """
    Replace the identity 'me' in Cloak source code to 'msg.sender', 
    note that it does not replace '@me'. 

    @params cloak_code: Cloak source code, which is Solidity with 
            cloak privacy annotation    
    """
    if cloak_code == "me":
        return "msg.sender"
        
    me_pattern = re.compile('([^a-z]|@)me([^a-z]|$)')
    matched_me = me_pattern.search(cloak_code)
    new_code = cloak_code

    while matched_me:
        # replace me to msg.sender
        new_code = new_code.replace(
            matched_me.group(), 
            f"{matched_me.group(1)}msg.sender{matched_me.group(2)}")

        matched_me = me_pattern.search(new_code)

    return new_code


def delete_cloak_annotation(cloak_code: str):
    """
    Delete privacy annotation from Cloak source code, return the 
    pure Solidity source code

    @params cloak_code: Cloak source code, which is Solidity with 
            cloak privacy annotation

    """
    anno_pattern = re.compile('@(.*?)[\s\)]')
    matched = anno_pattern.search(cloak_code)
    new_code = cloak_code
    while matched:
        owner_id = matched.group(1)
        new_code = new_code.replace(f"@{owner_id}", '')
        new_code = new_code.replace(f"!{owner_id}", '')            

        matched = anno_pattern.search(new_code)

    new_code = replace_me(new_code)
    new_code = replace_reveal(new_code)
    new_code = new_code.replace("    final ", '    ')

    return new_code


if __name__ == "__main__":
    cloak_code = """
pragma solidity ^0.5.0;

// Description: Record and grade exam answers
// Domain: Teaching
contract Exam {
    // A multiple-choice exam

    final address examinator;
    uint pass_points;

    mapping (uint => uint@examinator) solutions;
    mapping (address => mapping (uint => uint@examinator)) answers;
    mapping (address => uint@examinator) points;
    mapping (address!x => bool@x) passed;
	
    constructor(uint pass) public {
        examinator = me;
        pass_points = pass;
    }

    function set_solution(uint task, uint@me sol) public {
        require(examinator == me);
        solutions[task] = sol;
    }

    function record_answer(uint task, uint@me ans) public {
        answers[me][task] = reveal(ans, examinator);
        passed[me] = false;
        points[me] = 0;
    }

    function grade_task(uint task, address examinee) public {
        require(examinator == me);
        uint@me p;
        p = answers[examinee][task] == solutions[task] ? 1 : 0;
        points[examinee] = points[examinee] + p;
        passed[examinee] = reveal(points[examinee] > pass_points, examinee);
    }
}
"""

    pure_code = delete_cloak_annotation(cloak_code)
    print(pure_code)
