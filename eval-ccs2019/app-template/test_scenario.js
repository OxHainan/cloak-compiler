const tee_helpers = require("./tee_helpers");
const assert = require('assert');
const { async } = require("q");


// var json;

// function getJson() {
//     var contracts = {};
//     return new Promise(function(resolve) {
//         setTimeout(function() {
//             resolve(contracts);
//         }, 10)
//     })
// }

// async function compute() {
//     var x = await getJson();
//     x.id = "Pro";
//     var x2 = await getJson();
//     x2.json = "jijlajr";
//     console.log(x2);
//     json = x2;
// }

// var testcompute = async function(callback) {
//     // await compute();
//     // console.log(json.json);
// }

var test = async function(callback) {
    tee = "0xaF73A784B8C5dD0377F2B8A2651107037453244A";
    examinator = "0xaF73A784B8C5dD0377F2B8A2651107037453244A";
    args = ["100", tee, examinator, examinator, examinator];
    var ctrt = await tee_helpers.deploy("mix-type", args, examinator);
    // var ctrt = await tee_helpers.deploy("mix-type", [], examinator);
    console.log("Contract is deployed to:" + ctrt.address);
    console.log("Contract is owned by:" + ctrt.owner);
    // var result = await tee_helpers.tx(ctrt, "announcePk", [10], "0xaF73A784B8C5dD0377F2B8A2651107037453244A");
    // console.log("result:" + result);
}

test();