import os

from examples.scenarios import ScenarioGenerator

script_dir = os.path.dirname(os.path.realpath(__file__))

g = ScenarioGenerator(script_dir, 'scores.sol', {'examinator': 10, 'student': 20, 'tee': 30})

# run functions
g.run_function('constructor', 'examinator', [11, 22])
g.run_function('getAverage', 'tee')
g.run_function('setSolution', 'examinator', [1, 12])
# g.run_function('setSolution', 'examinator', [2, 13])
# g.run_function('record_answer', 'student', [1, 12])
# g.run_function('record_answer', 'student', [2, 14])
# g.run_function('grade_task', 'examinator', [1, 'student'])
# g.run_function('grade_task', 'examinator', [2, 'student'])

g.finalize()
