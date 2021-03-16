const Promise = require("bluebird");
const crypto = require("crypto");
const jayson = require("jayson");
const utils = require("./utils");
const jsrsasign = require("jsrsasign");

// A single instance ecdsa private key
const curve = "secp256k1";
let ec = new jsrsasign.KJUR.crypto.ECDSA({'curve': curve});
let keypair = ec.generateKeyPairHex();
let private_key = keypair.ecprvhex;
let public_key = keypair.ecpubhex;

let sig = new jsrsasign.KJUR.crypto.Signature({'alg':'SHA1withECDSA'});
sig.init({d: private_key, curve: curve});

let TCF_OPTIONS = {
  // config: "the path of config file, eg. tcs_connector.toml",  // unused
  uri: "http://avalon-listener:1947", // Direct API listener endpoint of Avalon
  address: "", // Unused in direct mode. Address of smart contract, only support in registry mode
  mode: "registry",
  worker_id: "", // Hexcode of worker id, will be filled next
  worker_id_hex: "",
  workload_id: "echo-result",
  in_data_plain: false,
  receipt: false,
  decrypted_output: false,
  requester_signature: false,
};

let WORKER_DETAILS = {
  verification_key: "",
  extended_measurements: [],
  proof_data_type: "",
  proof_data: {},
  verification_key: "",
  encryption_key: "",
  encryption_key_nonce: "",
  encryption_key_signature: "",
  enclave_certificate: "",
  hashing_algorithm: "",
  signing_algorithm: "",
  key_encryption_algorithm: "",
  data_encryption_algorithm: "",
};

let WORK_ORDER_STATUS = {
  SUCCESS : 0,
  FAILED : 1,
  INVALID_PARAMETER_FORMAT_OR_VALUE : 2,
  ACCESS_DENIED : 3,
  INVALID_SIGNATURE : 4,
  PENDING : 5,
  SCHEDULED : 6,
  PROCESSING : 7,
  BUSY : 8,
  INVALID_WORKLOAD : 9,
  UNKNOWN_ERROR : 10,
}

// A single instance client for connecting avalon listener
const tcf_client = jayson.client.http(TCF_OPTIONS["uri"]);
tcf_client.options.headers = {
  "Content-Type": "application/json",
};

let calculate_request_hash = (wo_params, data_item) => {
  let wop_hash = crypto
    .createHash("sha256")
    .update(wo_params.requesterNonce)
    .update(wo_params.workOrderId)
    .update(wo_params.workerId)
    .update(wo_params.workloadId)
    .update(wo_params.requesterId)
    .digest();

  let in_data_hash = crypto
    .createHash("sha256")
    .update(data_item.data)
    .digest(); // ignore unused dataHash and iv here

  let final_hash = crypto
    .createHash("sha256")
    .update(wop_hash)
    .update(in_data_hash)
    .digest(); // ignore unused outData hash

  return final_hash; // in Buffer
};

let load_worker = (worker_data) => {
  WORKER_DETAILS.hasing_algorithm = worker_data["hashingAlgorithm"];
  WORKER_DETAILS.signing_algorithm = worker_data["signingAlgorithm"];
  WORKER_DETAILS.key_encryption_algorithm =
    worker_data["keyEncryptionAlgorithm"];
  WORKER_DETAILS.data_encryption_algorithm =
    worker_data["dataEncryptionAlgorithm"];
  WORKER_DETAILS.verification_key =
    worker_data["workerTypeData"]["verificationKey"];
  WORKER_DETAILS.encryption_key =
    worker_data["workerTypeData"]["encryptionKey"];
  if (
    worker_data["workerTypeData"]["proofData"] != null &&
    worker_data["workerTypeData"]["proofData"] != ""
  ) {
    WORKER_DETAILS.proof_data = JSON.parse(
      worker_data["workerTypeData"]["proofData"]
    );
  }
  // TODO: verify the worker detials before return
  console.log("Worker details updated.");
  return WORKER_DETAILS;
};

// finish worker_order_receipt_create
let work_order_receipt_create = () => {
  work_order_params = {
    workOrderId: work_order_id,
    workerServiceId: worker_service_id,
    workerId: worker_id,
    requesterId: requester_id,
    receiptCreateStatus: receipt_create_status,
    workOrderRequestHash: work_order_request_hash,
    requesterGeneratedNonce: requester_nonce,
    requesterSignature: requester_signature,
    signatureRules: signature_rules,
    receiptVerificationKey: receipt_verification_key,
  };
};

/**
 * Lookup avalable workers in Avalon worker pool
 *
 * @returns {JSON} Response of WorkerLookUp request in JSON
 */
let worker_lookup = async () => {
  work_order_params = {
    workerType: 1, // represent SGX
  };
  const tcf_requester = Promise.promisify(tcf_client.request, {
    multiArgs: true,
    context: tcf_client,
  });
  let response = await tcf_requester("WorkerLookUp", work_order_params)
    .then(
      (response) => {
        // TODO: check response result before return
        return response[0];
      },
      (reject) => {
        console.error(`Rejected by: ${reject}`);
      }
    )
    .catch((err) => {
      console.error(`Error happened in requesting WorkerLookUp: ${err}`);
    });
  console.log("Worker lookup done.");
  return response;
};

/**
 * Retrieve the worker's information
 *
 * @param {String} worker_id
 * @returns {JSON} Response of WorkerRetrieve request in JSON, which
 *  contains the worker's details
 */
let worker_retrieve = async (worker_id) => {
  const tcf_requester = Promise.promisify(tcf_client.request, {
    multiArgs: true,
    context: tcf_client,
  });
  let response = await tcf_requester("WorkerRetrieve", { workerId: worker_id })
    .then(
      (response) => {
        // TODO: check response result before return
        return response[0];
      },
      (reject) => {
        console.error(`Rejected by: ${reject}`);
      }
    )
    .catch((err) => {
      console.error(`Error happened in requesting WorkerRetrieve: ${err}`);
    });
  return response;
};

/**
 * Init Avalon TCF client before communication.
 *
 */
let init = async () => {
  // Init worker info
  let worker_lookup_result = await worker_lookup();
  if (worker_lookup_result.result != null) {
    TCF_OPTIONS.worker_id = worker_lookup_result.result.ids[0];
  } else {
    console.error("Fail to lookup worker: ", worker_lookup_result.error);
  }

  // Retrieve and init worker details
  let worker_retrieve_result = await worker_retrieve(TCF_OPTIONS.worker_id);
  if (worker_retrieve_result.result != null) {
    WORKER_DETAILS = load_worker(worker_retrieve_result.result.details);
  } else {
    console.error("Fail to lookup worker: ", worker_retrieve_result.error);
  }
};

test = "test";

module.exports = {
  TCF_OPTIONS: TCF_OPTIONS,
  WORKER_DETAILS: WORKER_DETAILS,
  WORK_ORDER_STATUS: WORK_ORDER_STATUS,

  init: init,

  work_order_submit: async (workorder_id, in_data) => {
    let session_key = crypto.randomBytes(32);
    let session_iv = crypto.randomBytes(12);
    encrypted_session_key = crypto.publicEncrypt(
      {
        key: WORKER_DETAILS.encryption_key,
        padding: crypto.constants.RSA_PKCS1_OAEP_PADDING,
      },
      session_key
    );

    work_order_params = {
      responseTimeoutMSecs: 6000,
      payloadFormat: "JSON-RPC",
      resultUri: "",
      notifyUri: "",
      workOrderId: workorder_id.toString("hex"),
      workerId: TCF_OPTIONS.worker_id,
      workerEncryptionKey: utils.stringToHex(
        WORKER_DETAILS.encryption_key
      ),
      workloadId: utils.stringToHex(TCF_OPTIONS.workload_id),
      requesterId: crypto.randomBytes(32).toString("hex"),
      dataEncryptionAlgorithm: "AES-GCM-256",
      encryptedSessionKey: encrypted_session_key.toString("hex"),
      sessionKeyIv: session_iv.toString("hex"),
      requesterNonce: crypto.randomBytes(16).toString("hex"),
      encryptedRequestHash: "",
      requesterSignature: "",
      inData: [],
    };

    // Add "inData", suppose in_data is a plain_text string,
    // encryptedDataEncryptionKey default to be null, so we need to
    // encrypt each data by session_key
    const data_cipher = crypto.createCipheriv(
      "aes-256-gcm",
      session_key,
      session_iv
    );
    let encrypted = Buffer.from(data_cipher.update(in_data), "utf-8");
    data_cipher.final();
    let tag = data_cipher.getAuthTag();
    final_data_cipher = Buffer.concat([encrypted, tag]);

    data_item = {
      index: 0,
      data: final_data_cipher.toString("base64"),
    };

    // Suppose "inData" to one item here for simple
    work_order_params.inData = [data_item];

    // Add "encryptedRequestHash" with MAC encryption
    let final_hash = calculate_request_hash(work_order_params, data_item);
    const cipher = crypto.createCipheriv(
      "aes-256-gcm",
      session_key,
      session_iv
    );
    encrypted = Buffer.from(cipher.update(final_hash), "utf-8");
    cipher.final();
    tag = cipher.getAuthTag();
    final_request_cipher = Buffer.concat([encrypted, tag]);
    work_order_params.encryptedRequestHash = final_request_cipher.toString(
      "hex"
    );

    // Add "requesterSignature" and "verifyingKey"
    // shocked by empty of these two fields
    // sig.updateString(final_hash);
    // request_sig = utils.hexToBase64(sig.sign());
    // work_order_params.requesterSignature = request_sig;
    // work_order_params.verifyingKey = jsrsasign.KEYUTIL.getPEM(public_key);

    console.log(work_order_params);

    const tcf_requester = Promise.promisify(tcf_client.request, {
      multiArgs: true,
      context: tcf_client,
    });
    let response = await tcf_requester("WorkOrderSubmit", work_order_params)
      .then((response) => {
        console.log(response);
        return response[0];
      })
      .catch((err) => {
        console.error(`Error happened in requesting WorkOrderSubmit: ${err}`);
      });
    return response;
  },

  work_order_get_result: async (workorder_id) => {
    if (workorder_id == null || workorder_id == "") {
      return;
    }
    // workorder id should either be Buffer or hex encoded String
    if (workorder_id instanceof Buffer) {
      workorder_id = workorder_id.toString("hex");
    } else if (!workorder_id instanceof String) {
      return;
    }
    work_order_params = {
      workOrderId: workorder_id,
    };
    const tcf_requester = Promise.promisify(tcf_client.request, {
      multiArgs: true,
      context: tcf_client,
    });
    let response = await tcf_requester("WorkOrderGetResult", work_order_params)
      .then((response) => {
        // TODO: check response result before return
        return response[0];
      })
      .catch((err) => {
        console.error(`Error happened in requesting WorkOrderGetResult: ${err}`);
      });
    return response;
  },
};
