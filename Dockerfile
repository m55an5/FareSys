FROM gradle:jdk-alpine AS gradlebuild

COPY ./ /usr/src/
WORKDIR /usr/src/
USER root
RUN gradle build

FROM openjdk:8-jre-alpine3.9

COPY --from=gradlebuild  /usr/src/build/libs/FareSys-1.0.0.jar ./demo.jar
USER root

ENTRYPOINT ["/bin/sh", "-c", "java -jar demo.jar taps.csv"]
