var cloaktee = require('./gen-nodejs/cloak_tee');
var thrift = require('thrift');
const assert = require('assert');
var format = require('string-format');
var Web3 = require('web3');
const fs = require('fs');
var solc = require('solc');

const privKey = '0x67b107fc84ed572bc214a2b7649b3f33668dd50ea740e203696a42086ff9d168';

// Init thrift and TEE Client
var transport = thrift.TBufferedTransport;
var protocol = thrift.TBinaryProtocol;

var connection = thrift.createConnection("avalon-evm4ccf", 9090, {
    transport: transport,
    protocol: protocol
});

connection.on('error', function (err) {
    assert(false, err);
});

var tee_client = thrift.createClient(cloaktee, connection);


// Init Web3
const web3 = new Web3('http://localhost:8545');

var get_deploy_tee_tx = async (ctrt_ins, args) => {
    var abi = ctrt_ins.abi;
    const ctrt_obj = new web3.eth.Contract(abi);

    const deploy_tx = ctrt_obj.deploy({
        data: ctrt_ins.bytecode,
        arguments: args
    });
    
    tx_json = {
        "from": ctrt_ins.owner,
        "data": deploy_tx.encodeABI(),
        "gas": await deploy_tx.estimateGas(),
        "chainId": 0
    };

    // console.log("Transaction: " + JSON.stringify(tx_json) + "\n");

    return tx_json;
}

var get_call_tee_tx = async (ctrt_ins, fname, args, sender) => {
    // var fhash;
    // for (var f in ctrt_ins["hashes"]) {
    //     if (f.indexOf(fname) != -1) {
    //         fhash = ctrt_ins["hashes"][f];
    //         break;
    //     }
    // }
    var abi = JSON.parse(ctrt_ins["abi"]);
    const ctrt_obj = new web3.eth.Contract(abi, ctrt_ins.address);
    f_call = eval(format('ctrt_obj.methods.{}', fname));
    if(typeof(f_call) != "function") {
        throw new Error("Don't found function with name: {}".format(fname));
    } 
    const f_call_tx = f_call(args);

    tx_json = {
        "from": sender,
        "to": ctrt_ins.address,
        "value": 1000,
        "data": f_call_tx.encodeABI(),
        "gas": await f_call_tx.estimateGas()
    };

    return tx_json;
}


module.exports = {
    file_name: null,

    // deploy contract to TEE by rpc and get contract address
    deploy: async function (file_name, args, sender) {
        let ctrt_ins = {};
        ctrt_ins.owner = sender;
        // Get contract name
        let contract_name;
        let name_arr = file_name.split('-');
        for (var i=0; i<name_arr.length; i++) {
            if (i == 0) {
                contract_name = ""
            }
            let part = '';
            if (name_arr[i].length > 1) {
                part = name_arr[i].charAt(0).toUpperCase() + name_arr[i].slice(1);
            } else {
                part = name_arr[i].charAt[0];
            }
            contract_name += part;
        }
        ctrt_ins.name = contract_name;

        // compile solidity source code
        file_path = format('eval-ccs2019/examples/{}/compiled/pured-{}.sol', file_name, file_name);
        const source = fs.readFileSync(file_path, 'utf8');
        
        const input = {
            language: 'Solidity',
            sources: {},
            settings: {
                outputSelection: {
                    '*': {
                        '*': ['*'],
                    },
                },
            },
        };
        input.sources[file_path] = {content: source};

        const solcOut = JSON.parse(solc.compile(JSON.stringify(input)));
        const contractFile = solcOut.contracts[file_path][ctrt_ins.name];
        ctrt_ins.bytecode = contractFile.evm.bytecode.object;
        ctrt_ins.abi = contractFile.abi;

        let tx_json = await get_deploy_tee_tx(ctrt_ins, args);

        let signed_tx_json = await web3.eth.accounts.signTransaction(tx_json, privKey);
        rawTx = signed_tx_json.rawTransaction;

        const createReceipt = await tee_client.send_transaction(rawTx);
        ctrt_ins.address = createReceipt.contractAddress;
        console.log(`Tx successfull with hash: ${ctrt_ins.address}`);

        return ctrt_ins;
    },

    // deploy contract to TEE by rpc and get contract address
    tx: async function (ctrt_ins, fname, args, sender) {
        tx_json = await get_call_tee_tx(ctrt_ins, fname, args, sender);
        tx_json_str = JSON.stringify(tx_json);

        var tx_result = await tee_client.send_transaction(tx_json_str);

        return tx_result;
    }

};

