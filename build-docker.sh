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
# To run docker interactively (mounts the current directory):
# $ path/to/build-docker.sh
#
# To run a specific command withing docker (e.g.):
# $ path/to/build-docker.sh make test

############
# SETTINGS #
############

IMAGE=liam/cloak-compiler

###############
# PREPARATION #
###############
# determine directory containing this script

sysname=`uname  -a`
if [[ $sysname =~ "Darwin" ]];then
    echo "build on mac"
    BASEDIR="$(cd "$(dirname "$0")"; pwd)"
else
    echo "build on linux"
    BASEDIR="$(dirname "$(readlink -f "$0")")"
fi

# create docker image (if it does not yet exist)
make -C "$BASEDIR/install" image

##############
# RUN DOCKER #
##############
# --rm: automatically clean up the container when the container exits
# --workdir: Working directory inside the container
# -v: Bind mount a volume from the host

if [ $# -eq 0 ]; then
	# no arguments supplied
	echo "Running docker interactively..."
	DIR="$(pwd)"
	WORKDIR="/cloak-compiler"
	FLAGS="-v $DIR:$WORKDIR --workdir $WORKDIR"
else
	echo "Running in docker: $@"
	FLAGS="--workdir /cloak-compiler"
fi


sudo docker run \
	-it \
	--rm \
	-v "$BASEDIR":/cloak-compiler \
	$FLAGS \
	$IMAGE \
	"$@"

