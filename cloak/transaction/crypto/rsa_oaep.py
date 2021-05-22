from typing import Tuple, List

from Crypto.Cipher import PKCS1_OAEP
from Crypto.Hash import SHA256
from Crypto.PublicKey import RSA

from cloak.config import cfg
from cloak.transaction.crypto.rsa_base import RSACrypto, PersistentLocals


class RSAOAEPCrypto(RSACrypto):
    def _enc(self, plain: int, _: int, target_pk: int) -> Tuple[List[int], List[int]]:
        pub_key = RSA.construct((target_pk, self.default_exponent))

        encrypt = PersistentLocals(PKCS1_OAEP.new(pub_key, hashAlgo=SHA256).encrypt)
        cipher_bytes = encrypt(plain.to_bytes(32, byteorder='big'))
        cipher = self.pack_byte_array(cipher_bytes, cfg.cipher_chunk_size)

        rnd_bytes = encrypt.locals['ros']
        rnd = self.pack_byte_array(rnd_bytes, cfg.rnd_chunk_size)

        return cipher, rnd

    def _dec(self, cipher: Tuple[int, ...], sk: RSA.RsaKey) -> Tuple[int, List[int]]:
        decrypt = PersistentLocals(PKCS1_OAEP.new(sk, hashAlgo=SHA256).decrypt)
        plain = int.from_bytes(decrypt(self.unpack_to_byte_array(cipher, cfg.cipher_chunk_size, cfg.cipher_bytes_payload)), byteorder='big')

        rnd_bytes = decrypt.locals['seed']
        rnd = self.pack_byte_array(rnd_bytes, cfg.rnd_chunk_size)

        return plain, rnd
