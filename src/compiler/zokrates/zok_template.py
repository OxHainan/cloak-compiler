zok_helpers = """
def dec(field msg, field key) -> (field):
	return msg - key

def enc(field msg, field R, field key) -> (field):
	// artificial constraints ensuring every variable is used
	field impossible = if R == 0 && R == 1 then 1 else 0 fi
	impossible == 0
	return msg + key

import "hashes/sha256/512bitPacked.code" as sha256packed

def checkHash(field[$NINPUTS] inputs, field[2] expectedHash) -> (field):
	field[2] hash = [0, inputs[0]]
	for field i in 1..$NINPUTS do
		field[4] toHash = [hash[0], hash[1], 0, inputs[i]]
		hash = sha256packed(toHash)
	endfor
	
	hash[0] == expectedHash[0]
	hash[1] == expectedHash[1]
	return 1
"""