FROM openjdk:8-jre-alpine

ENV APPLICATION_USER ktor
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
WORKDIR /app

RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY ./build/libs/bath.jar /app/bath.jar
