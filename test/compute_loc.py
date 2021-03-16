
import my_logging

contracts_info = {
    "power-grid": {
        "states": {
            "single": 1,
            "mapping": 2
        },
        "functions": [
            {
                "name": "register_consumed",
                "inputs": 1,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 1,
                        "keys": 1
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 1,
                        "keys": 1
                    }
                },
                "outputs": 0
            },
            {
                "name": "declare_total",
                "inputs": 0,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 1,
                        "keys": 1
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 1,
                        "keys": 1
                    }
                },
                "outputs": 0
            }
        ]
    },
    "bid": {
        "states": {
            "single": 5,
            "mapping": 1
        },
        "functions": [
            {
                "name": "bet",
                "inputs": 1,
                "read": {
                    "single": 2,
                    "mapping": {
                        "nums": 1,
                        "keys": 1
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 1,
                        "keys": 1
                    }
                },
                "outputs": 0
            },
            {
                "name": "claim_winner",
                "inputs": 0,
                "read": {
                    "single": 4,
                    "mapping": {
                        "nums": 1,
                        "keys": 1
                    }
                },
                "mutate": {
                    "single": 1,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "outputs": 0
            }
        ]
    },
    "supply-chain": {
        "states": {
            "single": 2,
            "mapping": 4
        },
        "functions": [
            {
                "name": "finishReceivable",
                "inputs": 2,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 2,
                        "keys": 2
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 2,
                        "keys": 2
                    }
                },
                "outputs": 1
            }
        ]
    },
    "scores": {
        "states": {
            "single": 5,
            "mapping": 4
        },
        "functions": [
            {
                "name": "setSolution",
                "inputs": 2,
                "read": {
                    "single": 1,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 1,
                        "keys": 1
                    }
                },
                "outputs": 0
            },
            {
                "name": "getAverage",
                "inputs": 0,
                "read": {
                    "single": 2,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "mutate": {
                    "single": 1,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "outputs": 0
            },
            {
                "name": "rateAnswer",
                "inputs": 2,
                "read": {
                    "single": 4,
                    "mapping": {
                        "nums": 3.5, # +0.5 for multi level mapping 
                        "keys": 3.5
                    }
                },
                "mutate": {
                    "single": 2,
                    "mapping": {
                        "nums": 2,
                        "keys": 2
                    }
                },
                "outputs": 0
            }
        ]
    },
    "insurance": {
        "states": {
            "single": 4,
            "mapping": 6
        },
        "functions": [
            {
                "name": "register",
                "inputs": 0,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 2,
                        "keys": 2
                    }
                },
                "outputs": 0
            },
            {
                "name": "set_stolen",
                "inputs": 3,
                "read": {
                    "single": 1,
                    "mapping": {
                        "nums": 0, 
                        "keys": 0
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 1.5, # +0.5 for multi level mapping 
                        "keys": 1.5
                    }
                },
                "outputs": 0
            },
            {
                "name": "insure_item",
                "inputs": 2,
                "read": {
                    "single": 1,
                    "mapping": {
                        "nums": 1,
                        "keys": 1
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 8.5,
                        "keys": 8.5
                    }
                },
                "outputs": 0
            }
        ]
    },
    "erc20": {
        "states": {
            "single": 1,
            "mapping": 5
        },
        "functions": [
            {
                "name": "transfer",
                "inputs": 2,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 1,
                        "keys": 1
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 1,
                        "keys": 2
                    }
                },
                "outputs": 1
            },
            {
                "name": "transferFrom",
                "inputs": 3,
                "read": {
                    "single": 1,
                    "mapping": {
                        "nums": 2.5,  # +0.5 
                        "keys": 0
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 2.5, # +0.5 for multi level mapping 
                        "keys": 3.5
                    }
                },
                "outputs": 1
            },
            {
                "name": "approve",
                "inputs": 2,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 1.5,
                        "keys": 1.5
                    }
                },
                "outputs": 1
            }
        ]
    },
    "ox-token": {
        "states": {
            "single": 15,
            "mapping": 10
        },
        "functions": [
            {
                "name": "transferFrom",
                "inputs": 3,
                "read": {
                    "single": 2,
                    "mapping": {
                        "nums": 2.5,
                        "keys": 3.5
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 2.5,
                        "keys": 3.5
                    }
                },
                "outputs": 1
            },
            {
                "name": "approve",
                "inputs": 2,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 1.5,
                        "keys": 1.5
                    }
                },
                "outputs": 1
            },
            {
                "name": "multiPartyTransfer",
                "inputs": 11,
                "read": {
                    "single": 2,
                    "mapping": {
                        "nums": 2.5,
                        "keys": 3.5
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 2.5,
                        "keys": 3.5
                    }
                },
                "outputs": 1
            }
        ]
    },
    "ox-oracle": {
        "states": {
            "single": 14,
            "mapping": 5
        },
        "functions": [
            {
                "name": "requestRandom",
                "inputs": 2,
                "read": {
                    "single": 1,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "outputs": 1
            },
            {
                "name": "getVerifiableRandom",
                "inputs": 10,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "outputs": 1
            }
        ]
    },
    "ox-htlc": {
        "states": {
            "single": 14,
            "mapping": 7
        },
        "functions": [
            {
                "name": "boolToString",
                "inputs": 1,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "outputs": 1
            },
            {
                "name": "unlock",
                "inputs": 2,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 3,
                        "keys": 13
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "outputs": 1
            },
            {
                "name": "hashMatched",
                "inputs": 2,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "outputs": 1
            },
            {
                "name": "rightTimelock",
                "inputs": 2,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "outputs": 1
            },
            {
                "name": "getSender",
                "inputs": 1,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 3,
                        "keys": 4
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "outputs": 1
            },
            {
                "name": "getReceiver",
                "inputs": 1,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 3,
                        "keys": 4
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "outputs": 1
            },
            {
                "name": "getAmount",
                "inputs": 1,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 3,
                        "keys": 4
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "outputs": 1
            },
            {
                "name": "newProposal",
                "inputs": 10,
                "read": {
                    "single": 0,
                    "mapping": {
                        "nums": 2,
                        "keys": 3
                    }
                },
                "mutate": {
                    "single": 0,
                    "mapping": {
                        "nums": 0,
                        "keys": 0
                    }
                },
                "outputs": 1
            }
        ]
    }
}

def compute_policy_loc(c_name):
    loc = 4
    loc += (contracts_info[c_name]["states"]["single"] + contracts_info[c_name]["states"]["mapping"])*5
    loc += len(contracts_info[c_name]["functions"])*12
    for func in contracts_info[c_name]["functions"]:  # can repeat
        loc += func["inputs"]*5
        loc += (func["read"]["single"] + func["mutate"]["single"])*3
        loc += (func["read"]["mapping"]["nums"] + func["mutate"]["mapping"]["nums"])*5
        loc += (func["read"]["mapping"]["keys"] + func["mutate"]["mapping"]["keys"])*1
        loc += func["outputs"]*5

    return loc

def compute_tee_added_loc(c_name):
    loc = 33
    loc += contracts_info[c_name]["states"]["single"]*3   # param, oldState and assign
    loc += contracts_info[c_name]["states"]["mapping"]*12
    loc += len(contracts_info[c_name]["functions"])*13
    for func in contracts_info[c_name]["functions"]:  # do not repeat
        # loc += (func["read"]["single"])*2  # param, oldState
        loc += (func["mutate"]["single"])*3  # param, oldState and assign
        # loc += (func["read"]["mapping"]["nums"])*6  # param, require, oldState loop
        loc += (func["mutate"]["mapping"]["nums"])*10   # param, require, oldState loop and write loop
    
    return loc

if __name__ == "__main__":
    # create log directory
    log_file = my_logging.get_log_file(filename='added_loc', parent_dir="/cloak-compiler/test", include_timestamp=False, label=None)
    my_logging.prepare_logger(log_file)

    contracts_list = [
        "power-grid",
        "bid",
        "supply-chain",
        "scores",
        "insurance",
        "erc20",
        "ox-token",
        "ox-oracle",
        "ox-htlc"
    ]

    for c in contracts_list:
        tee_added = compute_tee_added_loc(c)
        policy = compute_policy_loc(c)
        print(f"{c}: {tee_added}, {policy}")