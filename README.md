<img  width="280" src="https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/_static/logo.png" alt="cloak-logo" align="left">

<h1 align="center">
    <a>
    Cloak Compiler
  </a>
</h1>

<p align="center">
  <a href="https://github.com/OxHainan/cloak-complier/blob/cloak/LICENSE">
    <img src="https://img.shields.io/badge/license-Apache%202-blue" alt="Cloak TEE is released under the Apache license." />
  </a>
  <a href="https://circleci.com/gh/OxHainan/cloak-compiler">
    <img src="https://circleci.com/gh/OxHainan/cloak-compiler/tree/master.svg?style=shield" alt="Current CircleCI build status." />
  </a>
  <a href="https://www.codefactor.io/repository/github/oxhainan/cloak-compiler">
    <img src="https://www.codefactor.io/repository/github/oxhainan/cloak-compiler/badge" alt="CodeFactor." />
  </a>
  <a href="https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/started/contribute.html">
    <img src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg" alt="PRs welcome!" />
  </a>
</p>

Cloak-compiler is an implementation of [Cloak Language](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/develop-cloak-smart-contract/cloak-language.html).

## What is Cloak?
Cloak is a pluggable and configurable framework for developing and deploying confidential smart contracts. To this end, Cloak allows users to specify privacy invariants (what is private data and to who is the data private) in a declarative way. Then, it automatically generate runtime with verifiably enforced privacy and deploy it to the existing EVM-enabled platforms (e.g., Ethereum) and TEE devices to enable the confidential smart contract.

The key capability of Cloak is to allow developers to implement and deploy practical solutions to Multi-Party Transaction (MPT) problems, i.e., to transact with secret functions parameters and states owned by different parties by simply specifying it.

In our evaluation on both examples and real-world applications, developers manage to deploy business services on blockchain in a concise manner by only developing Cloak smart contracts, whose size is less than 30% of the deployed ones, and the gas cost of deployed MPTs reduced by 19%.

The Cloak is an ongoing project aiming to become a chain-agnostic privacy infrastructure of the blockchain ecology. We are always calling for talented, self-motivated developers, researchers or students excited about our vision. Let us make it together.

## ❗️ Warning

Cloak is an ongoing project. The security of our implementation has not been systematically reviewed yet! Do not use Cloak in a productive system or to process sensitive confidential data now. We will keep working on Cloak, making it cool and practical step-by-step. 

## 📖 Usage
* [Install cloak-compiler](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/started/quick-start.html#installation)
* [Cloak Example](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/started/quick-start.html#cloak-by-examples)
* [Compile Cloak Contract](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/started/quick-start.html#compile-cloak-contract)
* [Use cloak-client to process Cloak transaction](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/started/quick-start.html#cloak-web3)
* [Full Document](https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/index.html)

## 📖 Documentation

The full documentation for Cloak can found on our [Cloak documentation][cloak-docs]

[cloak-docs]: https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/#

## 👏 How to Contribute

The main purpose of this repository is to continue evolving Cloak TEE core. We want to make contributing to this project as easy and transparent as possible, and we are grateful to the community for contributing bug fixes and improvements. 
Read below to learn how you can take part in improving Cloak TEE.

### [Code of Conduct][code]

Cloak TEE has adopted a Code of Conduct that we expect project participants to adhere to.
Please read the [full text][code] so that you can understand what actions will and will not be tolerated.

[code]: https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/started/contribute.html#documentation-style-guide

### [Contributing Guide][contribute]

Read our [**Call for Contributions**][contribute] to learn about our development process, how to propose bugfixed and improvements, and how to build and test your changes to Cloak.

[contribute]: https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/started/contribute.html#all-contributions-counts

### [Open Source Roadmap][roadmap]

You can learn more about our vision for Cloak Networks in the [**Roadmap**][roadmap].

[roadmap]: https://oxhainan-cloak-docs.readthedocs-hosted.com/en/latest/roadmap/index.html#roadmap

### Submit Issues

If you find a bug or have some new idea, please submit it to [**issues**][issues]. This is a great place to get started, gain experience,
and get familiar with our contribution process.

[issues]: https://github.com/OxHainan/cloak-compiler/issues

## Related works
cloak is implemented based on a research work, [zkay](https://github.com/eth-sri/zkay.git).
