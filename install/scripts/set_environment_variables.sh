#!/bin/bash

###########
# PURPOSE #
###########
# Set environment variables necessary to run cloak


# Directory containing this script.
#
# When running `prepare-conda.sh`, this line is
# automatically replaced by a hard-coded path
# BASEDIR=$(dirname "$(readlink -f "$0")")

BASEDIR=$(dirname "$(readlink -f "$BASH_SOURCE")")

# cloak
export CLOAKDIR="$BASEDIR/../.."
export CLOAKSRC="$CLOAKDIR/src"
export PYTHONPATH="$CLOAKSRC"


# zokrates
export WITH_LIBSNARK=1
export ZOKRATES_ROOT="$BASEDIR/../ZoKrates"
export ZOKRATES_HOME="$ZOKRATES_ROOT/zokrates_home"
