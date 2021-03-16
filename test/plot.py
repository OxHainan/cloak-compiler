import matplotlib.pyplot as plt
import numpy as np
import matplotlib.dates as mdates
import matplotlib.cbook as cbook
import matplotlib as mpl
from datetime import datetime
import numpy as np
from matplotlib.ticker import FuncFormatter
from brokenaxes import brokenaxes

def formatnum(x, pos):
    return '$%.1f$x$10^{4}$' % (x/10000)
formatter = FuncFormatter(formatnum)

infra_result = {
    "CloakService": {
        "origin": {
            "dpl": {
                "tx": 1225410,
                "exe": 885722
            },
            "txs": [
                {
                    "fn": "workerRegister",
                    "tx": 478236,
                    "exe": 454148
                }
            ]
        }
    }
}


expr_result = {
    "power-grid": {
        "name": "PowerGrid",
        "origin": {
            "dpl": {
                "tx": 212965,
                "exe": 121413
            },
            "txs": [
                {
                    "fn": "register_consumed",
                    "tx": 42690,
                    "exe": 21226
                }, {
                    "fn": "declare_total",
                    "tx": 43281,
                    "exe": 22009
                }
            ]
        },
        "parsed": {
            "dpl": {
                "tx": 883429,
                "exe": 625469
            },
            "txs": [
                {
                    "fn": "register_consumed",
                    "tx": 35566,
                    "exe": 12758
                }, {
                    "fn": "declare_total",
                    "tx": 36643,
                    "exe": 13131
                }
            ]
        }
    },
    "bid": {
        "name": "Bidding",
        "origin": {
            "dpl": {
                "tx": 271075,
                "exe": 161847
            },
            "txs": [
                {
                    "fn": "bet",
                    "tx": 44394,
                    "exe": 22930
                }, {
                    "fn": "claim_winner",
                    "tx": 46555,
                    "exe": 25283
                }
            ]
        },
        "parsed": {
            "dpl": {
                "tx": 762455,
                "exe": 532087
            },
            "txs": [
                {
                    "fn": "bet",
                    "tx": 35560,
                    "exe": 12752
                }, {
                    "fn": "claim_winner",
                    "tx": 55303,
                    "exe": 31727
                }
            ]
        }
    },
    "supply-chain": {
        "name": "SupplyChain",
        "origin": {
            "dpl": {
                "tx": 299364,
                "exe": 186088
            },
            "txs": [
                {
                    "fn": "finishReceivable",
                    "tx": 28861,
                    "exe": 6053
                }
            ]
        },
        "parsed": {
            "dpl": {
                "tx": 618976,
                "exe": 425868
            },
            "txs": [
                {
                    "fn": "finishReceivable",
                    "tx": 36144,
                    "exe": 12504
                }
            ]
        }
    },
    "scores": {
        "name": "Scores",
        "origin": {
            "dpl": {
                "tx": 340455,
                "exe": 214075
            },
            "txs": [
                {
                    "fn": "rateAnswer",
                    "tx": 111187,
                    "exe": 88315
                }, {
                    "fn": "setSolution",
                    "tx": 42897,
                    "exe": 21241
                }, {
                    "fn": "getAverage",
                    "tx": 43094,
                    "exe": 21822
                }
            ]
        },
        "parsed": {
            "dpl": {
                "tx": 1264651,
                "exe": 912491
            },
            "txs": [
                {
                    "fn": "rateAnswer",
                    "tx": 48663,
                    "exe": 22271
                }, {
                    "fn": "setSolution",
                    "tx": 35558,
                    "exe": 12750
                }, {
                    "fn": "getAverage",
                    "tx": 33224,
                    "exe": 10928
                }
            ]
        }
    },
    "insurance": {
        "name": "Insurance",
        "origin": {
            "dpl": {
                "tx": 561967,
                "exe": 392611
            }, "txs": [
                {
                    "fn": "register",
                    "tx": 39301,
                    "exe": 17656
                }, {
                    "fn": "insure_item",
                    "tx": 89633,
                    "exe": 67977
                }, {
                    "fn": "set_stolen",
                    "tx": 45267,
                    "exe": 22203
                }
            ]
        },
        "parsed": {
            "dpl": {
                "tx": 2289164,
                "exe": 1696272
            }, "txs": [
                {
                    "fn": "register",
                    "tx": 43257,
                    "exe": 17185
                }, {
                    "fn": "insure_item",
                    "tx": 50292,
                    "exe": 21468
                }, {
                    "fn": "set_stolen",
                    "tx": 37212,
                    "exe": 12996
                }
            ]
        }
    },
    "erc20": {
        "name": "ERC20Token",
        "origin": {
            "dpl": {
                "tx": 756880,
                "exe": 519476
            }, "txs": [
                {
                    "fn": "transfer",
                    "tx": 27712,
                    "exe": 4904
                }, {
                    "fn": "transferFrom",
                    "tx": 32115,
                    "exe": 7899
                }, {
                    "fn": "approve",
                    "tx": 43413,
                    "exe": 20541
                }
            ]
        },
        "parsed": {
            "dpl": {
                "tx": 1662885,
                "exe": 1201005
            }, "txs": [
                {
                    "fn": "transfer",
                    "tx": 35658,
                    "exe": 12850
                }, {
                    "fn": "transferFrom",
                    "tx": 41324,
                    "exe": 17684
                }, {
                    "fn": "approve",
                    "tx": 36076,
                    "exe": 13140
                }
            ]
        }
    },
    "ox-token": {
        "name": "YunDou",
        "origin": {
            "dpl": {
                "tx": 2448372,
                "exe": 1799228
            }, "txs": [
                {
                    "fn": "transferFrom",
                    "tx": 38478,
                    "exe": 14262
                }, {
                    "fn": "approve",
                    "tx": 43413,
                    "exe": 20541
                }, {
                    "fn": "multiPartyTransfer",
                    "tx": 41268,
                    "exe": 15516
                }
            ]
        },
        "parsed": {
            "dpl": {
                "tx": 3089862,
                "exe": 2285482
            }, "txs": [
                {
                    "fn": "transferFrom",
                    "tx": 41303,
                    "exe": 17663
                }, {
                    "fn": "approve",
                    "tx": 36166,
                    "exe": 13230
                }, {
                    "fn": "multiPartyTransfer",
                    "tx": 41302,
                    "exe": 17662
                }
            ]
        }
    },
    "ox-oracle": {
        "name": "RandomOracle",
        "origin": {
            "dpl": {
                "tx": 2824278,
                "exe": 2093742
            }, "txs": [
                {
                    "fn": "requestRandom",
                    "tx": 73961,
                    "exe": 51153
                }, {
                    "fn": "getVerifiableRandom",
                    "tx": 65352,
                    "exe": 37424
                }
            ]
        },
        "parsed": {
            "dpl": {
                "tx": 1800408,
                "exe": 1331132
            }, "txs": [
                {
                    "fn": "requestRandom",
                    "tx": 29152,
                    "exe": 6600
                }, {
                    "fn": "getVerifiableRandom",
                    "tx": 29150,
                    "exe": 6534
                }
            ]
        }
    },
    "ox-htlc": {
        "name": "HTLC",
        "origin": {
            "dpl": {
                "tx": 57193856,
                "exe": 57193856
            }, "txs": [
                {
                    "fn": "unlock",
                    "tx": 35653,
                    "exe": 14125
                }, {
                    "fn": "newProposal",
                    "tx": 85335,
                    "exe": 85335
                }, {
                    "fn": "setSecret",
                    "tx": 64515,
                    "exe": 62456
                }, {
                    "fn": "setNewProposalTxInfo",
                    "tx": 116663,
                    "exe": 116663
                }, {
                    "fn": "setUnlockState",
                    "tx": 49020,
                    "exe": 49020
                }, {
                    "fn": "deleteProposalID",
                    "tx": 29963,
                    "exe": 29963
                }, {
                    "fn": "setCounterpartyRollbackState",
                    "tx": 33961,
                    "exe": 33961
                }, {
                    "fn": "setLockState",
                    "tx": 48948,
                    "exe": 48948
                }
            ]
        },
        "parsed": {
            "dpl": {
                "tx": 5542779,
                "exe": 4193023
            }, "txs": [
                {
                    "fn": "unlock",
                    "tx": 21961,
                    "exe": 433
                }, {
                    "fn": "newProposal",
                    "tx": 35918,
                    "exe": 12406
                }, {
                    "fn": "setSecret",
                    "tx": 37860,
                    "exe": 14092
                }, {
                    "fn": "setNewProposalTxInfo",
                    "tx": 37927,
                    "exe": 14159
                }, {
                    "fn": "setUnlockState",
                    "tx": 38276,
                    "exe": 14380
                }, {
                    "fn": "deleteProposalID",
                    "tx": 28936,
                    "exe": 6576
                }, {
                    "fn": "setCounterpartyRollbackState",
                    "tx": 28872,
                    "exe": 6576
                }, {
                    "fn": "setLockState",
                    "tx": 28875,
                    "exe": 6579
                }
            ]
        }
    }
}

layer2_result = [
    {
        "tx_num": 1,
        "l1": {
            "tx": 27712,
            "exe": 4904
        },
        "l2-p2p": {
            "tx": 35658,
            "exe": 12850
        },
        "l2-net": {
            "tx": 35658,
            "exe": 12850
        }
    }, {
        "tx_num": 5,
        "l1": {
            "tx": 27712,
            "exe": 4904
        },
        "l2-p2p": {
            "tx": 7132,
            "exe": 2570
        },
        "l2-net": {
            "tx": 15587,
            "exe": 9489
        }
    }, {
        "tx_num": 10,
        "l1": {
            "tx": 27712,
            "exe": 4904
        },
        "l2-p2p": {
            "tx": 3566,
            "exe": 1285
        },
        "l2-net": {
            "tx": 9018,
            "exe": 5589
        }
    }, {
        "tx_num": 20,
        "l1": {
            "tx": 27712,
            "exe": 4904
        },
        "l2-p2p": {
            "tx": 1783,
            "exe": 643
        },
        "l2-net": {
            "tx": 7232,
            "exe": 4931
        }
    }, {
        "tx_num": 50,
        "l1": {
            "tx": 27712,
            "exe": 4904
        },
        "l2-p2p": {
            "tx": 713,
            "exe": 257
        },
        "l2-net": {
            "tx": 6077,
            "exe": 4705
        }
    }, {
        "tx_num": 100,
        "l1": {
            "tx": 27712,
            "exe": 4904
        },
        "l2-p2p": {
            "tx": 357,
            "exe": 129
        },
        "l2-net": {
            "tx": 5766,
            "exe": 4618
        }
    }
]

ox_token_coaccount = [
    {
        "tx_num": 2,
        "origin": {
            "tx": 228128,
            "exe": 139392
        },
        "cloak": {
            "tx": 111973,
            "exe": 81101
        }
    }, {
        "tx_num": 3,
        "origin": {
            "tx": 271960,
            "exe": 161632
        },
        "cloak": {
            "tx": 134382,
            "exe": 102038
        }
    }, {
        "tx_num": 5,
        "origin": {
            "tx": 359624,
            "exe": 206112
        },
        "cloak": {
            "tx": 179008,
            "exe": 143912
        }
    }, {
        "tx_num": 8,
        "origin": {
            "tx": 491120,
            "exe": 272832
        },
        "cloak": {
            "tx": 246107,
            "exe": 206723
        }
    }
]

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

origin_color = 'salmon'
cloak_color = 'deepskyblue'

def draw_single_deploy_gas_cost():
    labels = ['CloakService.constr', 'registerWorker', 'SupplyChain.constr', 'biddingProcure']

    tx_gas = [
        infra_result["CloakService"]["origin"]["dpl"]["tx"],
        infra_result["CloakService"]["origin"]["txs"][0]["tx"],
        expr_result["supply-chain"]["origin"]["dpl"]["tx"],
        expr_result["supply-chain"]["parsed"]["dpl"]["tx"],
        expr_result["supply-chain"]["origin"]["txs"][0]["tx"],
        expr_result["supply-chain"]["parsed"]["txs"][0]["tx"]
    ]

    x = np.arange(len(labels))  # the label locations
    width = 0.4  # the width of the bars

    fig, ax = plt.subplots()

    bar1 = ax.bar([0, 1], tx_gas[:2], width, color='silver', label='Infra')
    for bar in bar1:
        bar.set_hatch('\\')

    bar2 = ax.bar([2-width/2], tx_gas[2], width, color=origin_color)
    bar3 = ax.bar([2+width/2], tx_gas[3], width, color='lightsteelblue', label='Deploy')
    for bar in bar3:
        bar.set_hatch('\\')
    
    bar4 = ax.bar([3-width/2], tx_gas[4], width, color=origin_color)
    bar5 = ax.bar([3+width/2], tx_gas[5], width, color=cloak_color, label='Transact')
    for bar in bar5:
        bar.set_hatch('\\')


    # Add some text for labels, title and custom x-axis tick labels, etc.
    ax.set_ylabel('Gas', fontsize=12)
    ax.set_xlabel('Transactions', fontsize=12)
    # ax.set_title('Gas cost by group')
    ax.set_xticks(x)
    ax.set_xticklabels(labels)
    ax.legend()
    ax.yaxis.set_major_formatter(formatter)

    def autolabel(rects):
        """Attach a text label above each bar in *rects*, displaying its height."""
        for rect in rects:
            height = rect.get_height()
            ax.annotate('{}'.format(height),
                        xy=(rect.get_x() + rect.get_width() / 2, height),
                        xytext=(0, 3),  # 3 points vertical offset
                        textcoords="offset points",
                        ha='center', va='bottom')
   
    # autolabel(rects1)
    plt.setp(ax.get_xticklabels(), rotation=10, ha="center")
    fig.tight_layout()

    ax.grid(True)
    plt.show()

def draw_all_deploy_gas_cost():
    orgin_deploy_gas = []
    parsed_deploy_gas = []
    contract_name = []

    for c in contracts_list:
        orgin_deploy_gas.append(expr_result[c]["origin"]["dpl"]["tx"])
        parsed_deploy_gas.append(expr_result[c]["parsed"]["dpl"]["tx"])
        contract_name.append(expr_result[c]["name"])

    x = np.arange(len(contracts_list))  # the label locations
    width = 0.4  # the width of the bars

    fig, (ax, ax2) = plt.subplots(2, 1, sharex=True)
    ax.bar(x-width/2, orgin_deploy_gas, width, color=origin_color, label='Origin')
    ax.bar(x+width/2, parsed_deploy_gas, width, color=cloak_color, label='Transformed')
    ax.vlines(5.5, 0, 60000000, linewidth=1, colors = "black", linestyles = "--")
    
    ax2.bar(x-width/2, orgin_deploy_gas, width, color=origin_color, label='Origin')
    ax2.bar(x+width/2, parsed_deploy_gas, width, color=cloak_color, label='Transformed')
    ax2.vlines(5.5, 0, 60000000, linewidth=1, colors = "black", linestyles = "--")

    ax.set_ylim(55000000, 60000000)
    ax2.set_ylim(0, 7000000)
    ax.spines['bottom'].set_visible(False)
    ax2.spines['top'].set_visible(False)

    # ax.xaxis.tick_top()
    # ax.tick_params(labeltop='off')  # don't put tick labels at the top
    ax2.xaxis.tick_bottom()

    # Add some text for labels, title and custom x-axis tick labels, etc.
    ax2.set_ylabel('Gas', fontsize=12)
    ax2.set_xlabel('Contracts', fontsize=12)
    ax2.set_xticks(x)
    ax2.set_xticklabels(contract_name)

    ax.grid(True)
    ax2.grid(True)
    ax.yaxis.set_major_formatter(formatter)
    ax2.yaxis.set_major_formatter(formatter)

    ax.legend()
    plt.setp(ax2.get_xticklabels(), rotation=30, ha="center")
    fig.tight_layout()

    plt.show()

def draw_all_txs_gas_cost():
    x_origin = []
    y_origin = []
    origin_colors = []
    origin_area = []

    x_parsed = []
    y_parsed = []
    parsed_colors = []
    parsed_area = []
 
    area_size = 30
    contract_name = []

    for i, c_name in enumerate(contracts_list):
        contract_name.append(expr_result[c_name]["name"])
        for tx in expr_result[c_name]["origin"]["txs"]:
            x_origin.append(i)
            y_origin.append(tx["tx"])
            origin_colors.append(0.3)
            origin_area.append(area_size)

        for tx in expr_result[c_name]["parsed"]["txs"]:
            x_parsed.append(i)
            y_parsed.append(tx["tx"])
            parsed_colors.append(0.67)
            parsed_area.append(area_size)
    
    fig, ax = plt.subplots()
    ax.scatter(x_origin, y_origin, s=origin_area, color=origin_color, alpha=0.5, label="Origin")
    ax.scatter(x_parsed, y_parsed, s=parsed_area, color=cloak_color, alpha=0.5, label="Transformed")
    ax.yaxis.set_major_formatter(formatter)

    ax.set_xlabel('Contracts', fontsize=12)

    ax.set_xticks(np.arange(len(contract_name)))
    ax.set_xticklabels(contract_name)
    ax.set_ylabel('Gas', fontsize=12)

    # ax.set_title('Gas cost of contract transactions')
    ax.grid(True)
    plt.setp(ax.get_xticklabels(), rotation=30, ha="center")
    ax.legend()
    
    fig.tight_layout()
    plt.show()

def draw_l2_gas_cost():
    # Fixing random state for reproducibility
    x = []
    l1_tx = []
    l2_p2p_tx = []
    l2_net_tx = []
    
    fig, ax = plt.subplots()

    for tx in layer2_result:
        x.append(tx["tx_num"])
        l1_tx.append(tx["l1"]["tx"])
        l2_p2p_tx.append(tx["l2-p2p"]["tx"])
        l2_net_tx.append(tx["l2-net"]["tx"])
        
    ax.plot(x, l1_tx, '-o', ms=7, alpha=0.5, color=origin_color, label="Layer-1")
    ax.plot(x, l2_net_tx, '-o', ms=7, alpha=0.5, color='palevioletred', label="Layer-2 Network")
    ax.plot(x, l2_p2p_tx, '-o', ms=7, alpha=0.5, color=cloak_color, label="Layer-2 P2P")

    ax.vlines(2, 0, 35000, linewidth=1, colors = "black", linestyles = "--")
    # x.append(2)
    # ax.set_xticks(x)
    # ax.set_xticklabels(x)

    ax.grid()
    ax.yaxis.set_major_formatter(formatter)

    ax.set_xlabel('ERC20Token.transfer transactions', fontsize=12)
    ax.set_ylabel('Gas', fontsize=12)

    ax.annotate('{}'.format(2),
                        xy=(4, 0),
                        xytext=(0, -3),  # 3 points vertical offset
                        textcoords="offset points",
                        ha='center', va='bottom')

    # ax.set_title('Gas cost of parsed contract with layer 2', fontsize=12)
    fig.tight_layout()
    ax.grid(True)
    ax.legend()
    plt.show()


def draw_multiparty_token_gas_cost():
    # Fixing random state for reproducibility
    x = []
    l1_mpt_tx = []
    l2_mpt_tx = []
    
    fig, ax = plt.subplots()

    for tx in ox_token_coaccount:
        x.append(tx["tx_num"])
        l1_mpt_tx.append(tx["origin"]["tx"])
        l2_mpt_tx.append(tx["cloak"]["tx"])
        
    ax.plot(x, l1_mpt_tx, '-o', ms=7, lw=1, alpha=0.5, color=origin_color, label="2-n Without Cloak")
    ax.plot(x, l2_mpt_tx, '-o', ms=7, lw=1, alpha=0.5, color=cloak_color, label="2-n With Cloak")

    ax.set_xticks(x)
    ax.set_xticklabels(x)
    ax.grid()
    
    ax.yaxis.set_major_formatter(formatter)
    
    ax.set_xlabel('Participants of co-managed account', fontsize=12)
    ax.set_ylabel('Gas', fontsize=12)

    # ax.set_title('Gas cost of parsed contract with layer 2', fontsize=12)
    fig.tight_layout()
    ax.grid(True)
    ax.legend()
    plt.show()

def compute_txs_reduce_rate():
    x_origin = []
    y_origin = []
    origin_colors = []
    origin_area = []

    x_parsed = []
    y_parsed = []
    parsed_colors = []
    parsed_area = []
 
    area_size = 30
    contract_name = []

    for i, c_name in enumerate(contracts_list):
        contract_name.append(expr_result[c_name]["name"])
        for tx in expr_result[c_name]["origin"]["txs"]:
            x_origin.append(i)
            y_origin.append(tx["tx"])
            origin_colors.append(0.3)
            origin_area.append(area_size)

        for tx in expr_result[c_name]["parsed"]["txs"]:
            x_parsed.append(i)
            y_parsed.append(tx["tx"])
            parsed_colors.append(0.67)
            parsed_area.append(area_size)
    
    rates = []
    for i in range(len(y_origin)):
        r = y_parsed[i]/y_origin[i]
        rates.append(r)
    rates.sort()
    print(rates)
    print(np.mean(rates))
    print(len(rates))

if __name__ == "__main__":
    # draw_single_deploy_gas_cost()
    # draw_all_deploy_gas_cost()
    # draw_all_txs_gas_cost()
    # draw_l2_gas_cost()
    # draw_multiparty_token_gas_cost()
    compute_txs_reduce_rate()
