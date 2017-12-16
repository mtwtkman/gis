FROM gafiatulin/alpine-sbt

RUN mkdir /work
WORKDIR /work
COPY . /work
