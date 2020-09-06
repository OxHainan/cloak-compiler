var helpers = require('./helpers.js.js');
var tee_helpers = require('./tee_helpers.js.js')  // Added

module.exports = async function(callback) {
    // gives 10 example accounts
    let accounts = await web3.eth.getAccounts();
    
    // set contract name
    // EXAMPLE:
    // helpers.contract_name = "Reviews"
	helpers.contract_name = "Scores";
	tee_helpers.contract_name = "Scores";    // Added

    // remember accounts
    // EXAMPLE: r1 = accounts[0];
	var examinator = accounts[0];
	var student = accounts[1];
	var tee = accounts[2];
    
    // get hold of the contract and the deployed instance
    var pki = artifacts.require("PublicKeyInfrastructure");
    let genPublicKeyInfrastructure = await pki.deployed();
    let genPublicKeyInfrastructure_tee = await tee_helpers.deploy("pki", [], accounts[0]);

    // Added
    // let bn256g2_tee = await tee_helpers.deploy("BN256G2", [], accounts[0]);
    // let pairing_tee = await tee_helpers.deploy("Pairing", [], accounts[0]);
    let Verify_exec_zkp_var_tee = await tee_helpers.deploy("Verify_exec_zkp"+"_verifier", [], accounts[0]);
    let Verify_set_solution_tee = await tee_helpers.deploy("Verify_set_solution"+"_verifier", [], accounts[0]);

    // fetch contract
    // EXAMPLE:
    // var contract = artifacts.require("Reviews");
    var contract = artifacts.require("Scores");

    // announce public keys
    // EXAMPLE: await helpers.tx(genPublicKeyInfrastructure, "announcePk", [10], pc);
	await helpers.tx(genPublicKeyInfrastructure, "announcePk", [10], examinator);
	await helpers.tx(genPublicKeyInfrastructure, "announcePk", [20], student);
    await helpers.tx(genPublicKeyInfrastructure, "announcePk", [10], tee);
    
    // Added
    await tee_helpers.tx(genPublicKeyInfrastructure_tee, "announcePk", [10], examinator);
    await tee_helpers.tx(genPublicKeyInfrastructure_tee, "announcePk", [20], student);
    await tee_helpers.tx(genPublicKeyInfrastructure_tee, "announcePk", [10], tee);

    // load the deployed verifiers
    // EXAMPLE: var verify_registerPaper = artifacts.require("Verify_registerPaper");
	var Verify_exec_zkp_var = artifacts.require("Verify_exec_zkp");
	var Verify_set_solution_var = artifacts.require("Verify_set_solution");
    // EXAMPLE: let verify_registerPaper_instance = await verify_registerPaper.deployed();
	Verify_exec_zkp_var = await Verify_exec_zkp_var.deployed();
	Verify_set_solution_var = await Verify_set_solution_var.deployed();

    // deploy contract
    // EXAMPLE:
    // let contract_instance = await helpers.deploy_x(web3, contract, [r1, r2, r3, pki_instance.address, verify_registerPaper_instance.address, verify_recordReview_instance.address, verify_decideAcceptance_instance.address], pc);
	// constructor(100)
	args = [100, tee, genPublicKeyInfrastructure.address, Verify_exec_zkp_var.address, Verify_set_solution_var.address];
	let contract_instance = await helpers.deploy_x(web3, contract, args, examinator);

    // Added
    args = ["100", tee, genPublicKeyInfrastructure_tee.address, Verify_exec_zkp_var_tee.address, Verify_set_solution_var_tee.address];
    let contract_instance_tee = await tee_helpers.deploy("scores", args, examinator);
    
    // run transactions
    // EXAMPLE:
    // args = [1334, ['0x199059f62797c622254b6b9cb914f1813cec435e1b718d20d63b8adef9cb3315', '0x2fe71de7ec153b8852869ac9d23520f2935830a3375a01fb6955047b68bc648d', '0x2b69f59203315192756144d6d104ac2f13c20c8037f7a9426aecc107d2eb9b0f', '0x29cba7a2f4c4c186da09adf68b36b50d383b01bedafcd842a684277d223d9045', '0x2e1bab6f4ea47aef607faacb56987f2ccb453778778d883ed2238316d0465d4a', '0x016f5a2233b004d36a3076d062af848d3f564223dbdfc0c00bcecf4fff2d1f26', '0x10b29bde099cd8ebafd67bbc62588b61e4e87da88bd9369a9cd1e8f7cad142e5', '0x1813507e817cb4015629f39ae4b8cb2f39215d894e0edfe9a9d9dcae31f9bf8a'], [1244]];
    // await helpers.tx(contract_instance, "registerPaper", args, author);
	// exec_tee(200)
	args = [210];
    // await helpers.tx(contract_instance, "exec_tee", args, tee);
    // Added
    await tee_helpers.tx(contract_instance_tee, "exec_tee", args, tee);

	// exec_zkp(100)
	args = [110, ['0x24f1cbad5faca80b1dbd03f09d811667852b0436f34162b3768a48c1d0fa1573', '0x2632896d4666306c1f8c6b702fbdea2efd5a28ec6bdff89743faee0695694ec3', '0x091ebd3120f28dbfe1d790a4ee69205801ead9f18f0fc0ec388fdc04f45645cb', '0x2d3895318127305a1d1ceeafcff967413d557c732057196bb324dba81541f6ba', '0x0e8693e76408a0924e4991d8a893b7a1bd17673c358edcd276baf8b727c815bc', '0x0d20a0ad6ec0f2298fa9b010c7054ecb269ddb48969f743f1defa7ecf0542647', '0x2777977799aa2085ebf8a747c1e0aa78216ea36495e68c84804ff084c7ad3537', '0x0384ad2c7f4b0a034f4922663110c844b7fc65fa4696d00d29af9579ae31a8b3'], [110]];
	await helpers.tx(contract_instance, "exec_zkp", args, examinator);

	// set_solution(1, 12)
	args = [1, 22, ['0x24bbb27489e387893bea7471c9f9309ea424d3887891a0515f121728379dedb5', '0x01f26539d70594a6c0e1e7029ad2a1500708d0e75745a9efc0efc53e06f36517', '0x25552d49f23b6ad447627710dfcd9f0bfd20bebbeb69156547454f5555960243', '0x06566206870f6a38209c39881958c0de56eab538c97f2202c31a4960380e23a7', '0x102a4dd0b4ca2554c6423343af14a6c3be1c4ff7ad7bb24bff1e5ce21f685540', '0x12b7855413951159367d769af30b4f919ed72ebf015a90c39266e64a06763d66', '0x2c3a23957a1fda490152bf3844d089d5950f04c78e7b8d8f04bcb2c50fdf84c8', '0x03cac28d0a03045671f31b3262ba87b5681e883fce12686eccfdc813674f3437']];
	await helpers.tx(contract_instance, "set_solution", args, examinator);

	// set_solution(2, 13)
	args = [2, 23, ['0x14716aa9606fa2202375fbeea5105db789c0f36d79ad55db3bb91d3c10b56eb1', '0x10be9100652c9aaa5f6f53e249b39fc2950d9e6d51a3ca224f6d7f24bad85a76', '0x1b6f73eb7d87d8871481fcae4cea094b64dc4640069850578fd9c2b813c06317', '0x152255e30c008678be440ec6434425a5afc03d54acfdac32e468e5806f936831', '0x08430d9d5dcf57ad6bdf7675b47de7a8a0d2300975ee82ba8db3a6c07367b58d', '0x0bb0ffb370251853f467dd994a2dacf4413ffcbaddb1d3b19e510cfaab48e9b5', '0x14953341f6e9878ae0fb1a0230ed8621af1a3e2765a9b674bdf7e2d3933c9eec', '0x0c7389ad0438c2af667e8dbc04da135d58e12243c545572c19ad286088c348a0']];
	await helpers.tx(contract_instance, "set_solution", args, examinator);
    
    console.log(">> end of scenario")
}
