# all commands involving docker use sudo, which is typically required to use docker commands

TAG := latest
IMAGE := liam/cloak-prerequisites
FULL_IMAGE := liam/cloak-compiler
FULL_CONTAINER := cloak-compiler

# build the prerequisites docker image
.PHONY: image
image:
	sudo docker build -t $(IMAGE):$(TAG) .
	sudo docker history --no-trunc $(IMAGE):$(TAG)

.PHONY: login
login:
	sudo docker login

.PHONY: deploy
deploy: login image
	sudo docker push $(IMAGE):$(TAG)

# build the full docker image
.PHONY: full-image
full-image:
	sudo docker build -t $(FULL_IMAGE):$(TAG) .
	sudo docker history --no-trunc $(FULL_IMAGE):$(TAG)

# launch a docker container using the image, which will provide a shell in the container
.PHONY: run
run: full-image
	sudo docker run -it --rm --name $(FULL_CONTAINER) $(FULL_IMAGE):$(TAG)
