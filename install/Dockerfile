FROM debian:10
ARG INSTALL_CLOAK=0

#############
# LIBSNARK #
#############
# the relevant dependencies to obtain and compile libsnarks
RUN apt-get update && apt-get install -y \
		git \
		build-essential \
		cmake \
		libgmp-dev \
		pkg-config \
		libssl-dev \
		libboost-dev \
		libboost-program-options-dev \
		wget \
	&& apt-get clean && rm -rf /var/lib/apt/lists/*

########
# JAVA #
########
# required to install default-jdk-headless
RUN mkdir -p /usr/share/man/man1
RUN apt-get update && apt-get install -y \
		default-jdk-headless \
	&& apt-get clean && rm -rf /var/lib/apt/lists/*

##########
# PYTHON #
##########
RUN apt-get update && apt-get install -y \
		python3-pip \
	&& apt-get clean && rm -rf /var/lib/apt/lists/*


###################
# CLOAK (OPTIONAL) #
###################
RUN if [ "$INSTALL_CLOAK" = "1" ]; then \
		git clone https://github.com/OxHainan/cloak-compiler.git && \
			cd cloak && \
			python3 setup.py sdist && \
			pip3 install --no-binary cloak dist/cloak-*.tar.gz; \
	fi
