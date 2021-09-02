# cloak-compiler
cloak-compiler is an implementation of [Cloak Language](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/develop-cloak-smart-contract/cloak-language.html).

## What is Cloak?
Cloak is a pluggable and configurable framework for developing and deploying confidential smart contracts. To this end, Cloak allows users to specify privacy invariants (what is private data and to who is the data private) in a declarative way. Then, it automatically generate runtime with verifiably enforced privacy and deploy it to the existing EVM-enabled platforms (e.g., Ethereum) and TEE devices to enable the confidential smart contract.

The key capability of Cloak is to allow developers to implement and deploy practical solutions to Multi-Party Transaction (MPT) problems, i.e., to transact with secret functions parameters and states owned by different parties by simply specifying it.

In our evaluation on both examples and real-world applications, developers manage to deploy business services on blockchain in a concise manner by only developing Cloak smart contracts, whose size is less than 30% of the deployed ones, and the gas cost of deployed MPTs reduced by 19%.

The Cloak is an ongoing project aiming to become a chain-agnostic privacy infrastructure of the blockchain ecology. We are always calling for talented, self-motivated developers, researchers or students excited about our vision. Let us make it together.

## Warning

This is a prototype implementation not intended for use in production. 

## Usage
* [Install cloak-compiler](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/started/quick-start.html#installation)
* [Cloak Example](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/started/quick-start.html#cloak-by-examples)
* [Compile Cloak Contract](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/started/quick-start.html#compile-cloak-contract)
* [Use cloak-client to process Cloak transaction](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/started/quick-start.html#cloak-web3)
* [Full Document](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/index.html)

## Contributing
Please see the [Call for Contributing](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/started/contribute.html)

## Related works
cloak is implemented based on a research work, [zkay](https://github.com/eth-sri/zkay.git).
