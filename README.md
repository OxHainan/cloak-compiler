# <font face="Copperplate" size="6">Cloak</font>: <font size="5">A Framework of the Development of Blockchain Confidential Smart Contract</font>

cloak is a programming language which enables
automatic compilation of intuitive data privacy specifications to NIZK, TEE enabled
private smart contracts.

cloak is implemented based on a research work, [zkay](https://github.com/eth-sri/zkay.git).

## Warning

This is a prototype implementation not intended for use in production. 

## Install

### Using Docker

The simplest way to run cloak is using docker. After installing docker, the docker image can be run
as follows:

```bash
/path/to/cloak$ ./build-docker.sh
/cloak-compiler$
```

This command mounts the directory `cloak` from your host as `/cloak-compiler`
within the docker container. You can run `build-docker.sh` also from any other directory `d` on your host.
In this case, `d` is mounted as `/d_host` inside the container.
This allows you to operate on files from your host machine.

### Directly On Host

As an alternative to docker, you may install cloak on your host directly. To this end, follow
the instructions in the [Dockerfile](./install/Dockerfile) marked by `To install on host`.

Below we show how to test your cloak installation, and how to type-check and
compile cloak contracts from _within the docker container_. However, the
respective commands can similarly be _run directly on the host_ after having
installed cloak properly.

## Unit Tests

To run all unit tests of cloak, run:

```bash
# run tests within docker
/cloak-compiler$ python3 -m unittest discover --verbose cloak
```

If all tests pass, your cloak installation is likely set up correctly.
Note that running all unit tests _may take several hours_.

## Type-Check Contracts

To type-check a cloak file `test.cloak` in `/path/to/contract` without compiling it, run:

```bash
# type check within docker
/contract_host$ python3 cloak/__main__.py check test.cloak
```

## Compile Contracts

To compile and type-check a cloak file `test.cloak` in `/path/to/contract`, run:

```bash
# run compilation within docker
/contract_host$ python3 cloak/__main__.py compile [-o "<output_dir>"] test.cloak
```