import os

from examples.scenarios import ScenarioGenerator

script_dir = os.path.dirname(os.path.realpath(__file__))

g = ScenarioGenerator(script_dir, 'scores.sol', {'examinator': 10, 'student': 20, 'tee': 10})

# run functions
g.run_function('constructor', 'examinator', [100])
# TODO: add run_function for exec_tee1, exec_mpc1 and exec_zkp1
g.run_function('exec_tee', 'tee', [200])
g.run_function('exec_zkp', 'examinator', [100])
g.run_function('set_solution', 'examinator', [1, 12])
g.run_function('set_solution', 'examinator', [2, 13])
# g.run_function('record_answer', 'student', [1, 12])
# g.run_function('record_answer', 'student', [2, 14])
# g.run_function('grade_task', 'examinator', [1, 'student'])
# g.run_function('grade_task', 'examinator', [2, 'student'])

g.finalize()
