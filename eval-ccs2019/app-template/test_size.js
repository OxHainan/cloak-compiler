
var format = require('string-format');
var solc = require('solc');
var Web3 = require('web3');
const fs = require('fs');

owner = "0xAc0C3Ccd3D24B34Fa551D36FDA974ba1ce336acE";
const privKey = '0x67b107fc84ed572bc214a2b7649b3f33668dd50ea740e203696a42086ff9d168';

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
        "gas": await deploy_tx.estimateGas()
    };

    console.log("Transaction: " + JSON.stringify(tx_json) + "\n");

    return tx_json;
}


let deploy = async function (file_name, args, sender) {
    var ctrt_ins = {};

    ctrt_ins.owner = sender;
    // ctrt_ins.name = file_name.charAt(0).toUpperCase() + file_name.slice(1)
    ctrt_ins.name = "HTLC";

    file_path = format('eval-ccs2019/examples/{}/{}.sol', file_name, file_name);
    const source = fs.readFileSync(file_path, 'utf8');
    sol_name = file_name+".sol"
    
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
    input.sources[sol_name] = {content: source};

    const tempFile = JSON.parse(solc.compile(JSON.stringify(input)));
    const contractFile = tempFile.contracts[sol_name][ctrt_ins.name];
    ctrt_ins.bytecode = contractFile.evm.bytecode.object;
    ctrt_ins.abi = contractFile.abi;
    var tx_json = await get_deploy_tee_tx(ctrt_ins, args);

    var signed_tx_json = await web3.eth.accounts.signTransaction(tx_json, privKey);
    rawTx = signed_tx_json.rawTransaction;

    const createReceipt = await web3.eth.sendSignedTransaction(rawTx);
    ctrt_ins.address = createReceipt.contractAddress;
    console.log(`Tx successfull with hash: ${ctrt_ins.address}`);

    return ctrt_ins;
}


testTeeHelper = async () => {
    // args = [1];
    args = [];
    let ctrt = await deploy("ox-htlc", args, owner);

    console.log("Contract is deployed to:" + ctrt.address);
    console.log("Contract is owned by:" + ctrt.owner);
    // let result = await tee_helpers.tx(ctrt, "getAverage", [10], ctrt.owner);
    // console.log("result:" + result);
}


testTeeHelper();
