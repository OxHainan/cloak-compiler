var cloaktee = require('./gen-nodejs/CloakTee');
var thrift = require('thrift');
const assert = require('assert');
var format = require('string-format');
var nodeCmd = require('node-cmd');
var Promise = require('bluebird');

var transport = thrift.TBufferedTransport;
var protocol = thrift.TBinaryProtocol;

var connection = thrift.createConnection("localhost", 9090, {
    transport: transport,
    protocol: protocol
});

connection.on('error', function (err) {
    assert(false, err);
});

// Create a Calculator client with the connection
var tee_client = thrift.createClient(cloaktee, connection);

var get_deploy_tee_tx = function (ctrt_json, args, sender) {
    tx_json = {
        "from": sender,
        "value": 1000,
        "data": {
            "bin": ctrt_json["bin"],
            "params": args
        }
    };
    tx_json_str = JSON.stringify(tx_json);
    return tx_json_str;
}

var get_call_tee_tx = function (ctrt_json, fname, args, sender) {
    var fhash;
    for (var f in ctrt_json["hashes"]) {
        if (f.indexOf(fname) != -1) {
            fhash = ctrt_json["hashes"][f];
            break;
        }
    }
    tx_json = {
        "from": sender,
        "to": ctrt_json.address,
        "value": 1000,
        "data": {
            "bin": ctrt_json["bin"],
            "method": fhash,
            "params": args
        }
    };
    tx_json_str = JSON.stringify(tx_json);
    return tx_json_str;
}


module.exports = {
    contract_name: null,

    // deploy contract to TEE by rpc and get contract address
    deploy: async function (contract_name, args, sender) {
        var ctrt_ins = {
            "owner": sender
        };
        const getAsync = Promise.promisify(nodeCmd.get, { multiArgs: true, context: nodeCmd });
        var data = await getAsync(format('solc --combined-json bin,hashes {}', format('../compiled/{}.sol', contract_name))).then(data => {
            return data[0];
        }).catch(err => {
            console.log("Fail to seal data by TEE " + err);
        });

        parse_data = JSON.parse(data);

        for (var ctrt in parse_data['contracts']) {
            if (ctrt.indexOf(contract_name + ".sol") != -1) {
                ctrt_ins.bin = parse_data['contracts'][ctrt]["bin"];
                ctrt_ins.hashes = parse_data['contracts'][ctrt]["hashes"];
                tx_json = get_deploy_tee_tx(ctrt_ins, args, sender);

                const seal_data = Promise.promisify(tee_client.seal_data, { multiArgs: true, context: tee_client });
                var sealed_tx = await seal_data(tx_json).then(data => {
                    return data[0];
                }).catch(err => {
                    console.log("Fail to seal data by TEE " + err);
                });

                const send_transaction = Promise.promisify(tee_client.send_transaction, { multiArgs: true, context: tee_client });
                var sealed_addr = await send_transaction(sealed_tx).then(data => {
                    return data[0];
                }).catch(err => {
                    console.log("Fail to send deployment transaction " + err);
                });

                const reveal_data = Promise.promisify(tee_client.reveal_data, { multiArgs: true, context: tee_client });
                ctrt_ins.address = "" + await reveal_data(sealed_addr).then(data => {
                    return data[0];
                }).catch(err => {
                    console.log("Fail to reveal data by TEE " + err);
                });
                ctrt_ins.address = ctrt_ins.address.replace("\u0000\u0000\u0000\u0000\u0000\u0000", '');
            }
        }
        return ctrt_ins;
    },

    // deploy contract to TEE by rpc and get contract address
    tx: async function (ctrt_ins, fname, args, sender) {
        tx_json = get_call_tee_tx(ctrt_ins, fname, args, sender);

        const seal_data = Promise.promisify(tee_client.seal_data, { multiArgs: true, context: tee_client });
        var sealed_tx = await seal_data(tx_json).then(data => {
            return data[0];
        }).catch(err => {
            console.log("Fail to seal data by TEE " + err);
        });

        const send_transaction = Promise.promisify(tee_client.send_transaction, { multiArgs: true, context: tee_client });
        var sealed_result = await send_transaction(sealed_tx).then(data => {
            return data[0];
        }).catch(err => {
            console.log("Fail to send call_function transaction " + err);
        });

        const reveal_data = Promise.promisify(tee_client.reveal_data, { multiArgs: true, context: tee_client });
        revealed_result = await reveal_data(sealed_result).then(data => {
            return data[0];
        }).catch(err => {
            console.log("Fail to reveal data by TEE " + err);
        });

        return revealed_result;
    }


};

