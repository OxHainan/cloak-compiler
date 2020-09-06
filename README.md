# cloak: A Blockchain Privacy Language

cloak is a programming language which enables
automatic compilation of intuitive data privacy specifications to NIZK, TEE enabled
private smart contracts.

cloak is based on a research work, [zkay](https://github.com/eth-sri/zkay.git).

## Warning

This is a prototype implementation not intended for use in production. In
particular, it uses "dummy" encryption `Enc(v,R,k)=v+k`, which is **insecure**.

## Install

### Using Docker

The simplest way to run cloak is using docker. After installing docker, the docker image can be run
as follows:

```bash
/path/to/cloak$ ./cloak-docker.sh
(base) root@ae09e165bd19:/cloak_host$
```

This command mounts the directory `cloak` from your host as `/cloak_host`
within the docker container. You can run `cloak-docker.sh` also from any other directory `d` on your host.
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
# run docker container
/path/to/cloak$ ./cloak-docker.sh
# run tests within docker
(base) root@ae09e165bd19:/cloak_host$ cd src
(base) root@ae09e165bd19:/cloak_host$ make test
```

If all tests pass, your cloak installation is likely set up correctly.
Note that running all unit tests _may take several hours_.

## Type-Check Contracts

To type-check a cloak file `test.cloak` in `/path/to/contract` without compiling it, run:

```bash
# run docker container
/path/to/contract$ /path/to/cloak-docker.sh
# run compilation
(base) root@ff2ddb8da49c:/contract_host$ python3 /cloak/src/main.py test.cloak --type-check
```

## Compile Contracts

To compile and type-check a cloak file `test.cloak` in `/path/to/contract`, run:

```bash
# run docker container
/path/to/contract$ /path/to/cloak-docker.sh
# run compilation
(base) root@ff2ddb8da49c:/contract_host$ python3 /cloak/src/main.py test.cloak
```

The output comprises the transformed cloak contract, the contracts for proof verification,
and the proof circuits in ZoKrates' domain-specific language. By default, it is placed
in the current working directory. A different output directory can be specified using
the `--output` command line argument.

Note that the compilation _may take a couple of minutes_.

## Transform and Run Transactions

To run a specific sequence of transactions (i.e., a _scenario_) for the `exam`
example contract, run:

```bash
# run docker container
/path/to/eval-ccs-2019$ ../cloak-docker.sh
# compile contract (omit if already compiled)
(base) root@ff2ddb8da49c:/eval-ccs-2019_host$ python3 "$CLOAKSRC/main.py" --output ./examples/exam/compiled ./examples/exam/exam.sol
# transform scenario
(base) root@ff2ddb8da49c:/eval-ccs-2019_host$ ./generate-scenario.sh ./examples/exam
# run scenario
(base) root@ff2ddb8da49c:/eval-ccs-2019_host$ ./examples/exam/scenario/runner.sh
```

To transform and run your own transactions, you may follow analogous steps. In
particular, see [scenario.py](./eval-ccs2019/examples/exam/scenario.py) for the
specification of the scenario ran by the above code.

## Run Evaluation from CCS 2019

To reproduce the evaluation results from the paper, run:

```bash
/path/to/cloak/eval-ccs2019$ ./cloak-eval-docker.sh
```

Note that running this command _may take several hours_ and requires docker
to be installed.
