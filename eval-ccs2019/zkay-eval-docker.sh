#/bin/bash

###########
# PURPOSE #
###########
# Run the docker image
#
# Prerequisites:
# - docker

#########
# USAGE #
#########
# $ ./cloak-eval-docker.sh

############
# SETTINGS #
############

IMAGE=cloak-eval

###############
# PREPARATION #
###############
# determine directory containing this script
BASEDIR="$(dirname "$(readlink -f "$0")")"

# create docker image (if it does not yet exist)
make -C "$BASEDIR/docker" image

##############
# RUN DOCKER #
##############
# --rm: automatically clean up the container when the container exits
# --workdir: Working directory inside the container
# -v: Bind mount a volume from the host

sudo docker run \
	-it \
	--rm \
	-v "$BASEDIR/..":/cloak-compiler \
	--workdir /cloak-compiler/eval-ccs2019 \
	$IMAGE \
	make eval

