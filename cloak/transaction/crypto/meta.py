cryptoparams = {
    'dummy': {
        'key_bits': 248,
        'cipher_payload_bytes': 31,
        'cipher_chunk_size': 31,
        'symmetric': False,
        'rnd_bytes': 31,
        'rnd_chunk_size': 31,
    },

    'rsa-oaep': {
        'key_bits': 2048,
        'cipher_payload_bytes': 256,
        'cipher_chunk_size': 29,
        'symmetric': False,
        'rnd_bytes': 32,
        'rnd_chunk_size': 16,
    },

    'rsa-pkcs1.5': {
        'key_bits': 2048,
        'cipher_payload_bytes': 256,
        'cipher_chunk_size': 29,
        'symmetric': False,
        'rnd_bytes': 221,  # for 256 - 3 - plainbytes (32 byte plaintext, for now fixed)
        'rnd_chunk_size': 28,
    },

    'ecdh-aes': {
        'key_bits': 253,
        'cipher_payload_bytes': 48, # 128bit iv + 256 bit ciphertext
        'cipher_chunk_size': 24,
        'symmetric': True,
        'rnd_bytes': 0, # included in cipher text
        'rnd_chunk_size': 0,
    },

    'ecdh-chaskey': {
        'key_bits': 253,
        'cipher_payload_bytes': 48, # 128bit iv + 256 bit ciphertext
        'cipher_chunk_size': 24,
        'symmetric': True,
        'rnd_bytes': 0, # included in cipher text
        'rnd_chunk_size': 0,
    }
}
