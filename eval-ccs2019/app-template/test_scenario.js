const tee_helpers = require("./tee_helpers");
const tcs = require("./tcs_client");
var Web3 = require('web3');
const Web3HttpProvider = require('web3-providers-http');


let testWeb3Cloak = async () => {
    const options = {
        keepAlive: true,
        timeout: 20000, // milliseconds,
        headers: [{name: 'Access-Control-Allow-Origin', value: '*'}],
        withCredentials: false
    };
    
    var cloakProvider = new Web3HttpProvider('avalon-evm4ccf:9090', options);
    const web3 = new Web3(cloakProvider);
    web3.extend({
        property: 'cloak',
        methods: [{
            name: 'sendTransaction',
            call: 'sendTransaction',
            params: 1
        }]
    });
    console.log(web3);
    // Failed by difference between HTTP jrpc and rpc
    let result = await web3.cloak.sendTransaction("testString");
    console.log(result);
}

let testTeeHelper = async () => {
    owner = "0xAc0C3Ccd3D24B34Fa551D36FDA974ba1ce336acE";
    args = [];
    let ctrt = await tee_helpers.deploy("ox-htlc", args, owner);
    console.log("Contract is deployed to:" + ctrt.address);
    console.log("Contract is owned by:" + ctrt.owner);
    // let result = await tee_helpers.tx(ctrt, "getAverage", [10], ctrt.owner);
    console.log("result:" + result);
}

let testTCSClient = async () => {
    let in_data = "Hello";
    await tcs.init();

    console.log(tcs.tcf_options);
    console.log(tcs.worker_details);

    workorder_id = crypto.randomBytes(32);
    let wo_submit_result = await tcs.work_order_submit(workorder_id, in_data);
    console.log(wo_submit_result);

    let wo_get_result_result = await tcs.work_order_get_result(workorder_id);
    console.log(wo_get_result_result);
    // Avalon could return json successfully with error message 
    // or result, we need to handle this by ourself
    if (wo_get_result_result.result != null) {
        console.log(wo_get_result_result.result);
    } else if(wo_get_result_result.error != null) {
        console.log(wo_get_result_result.error);
    } else {
        console.error(wo_get_result_result);
    }
}

// testWeb3Cloak();

testTeeHelper();

// testTCSClient();