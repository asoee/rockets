Rockets
=======

## Description

Small service for receiving messages from rockets, and exposing current state via json rest api.

Implemented as a spring boot application.

## Requirements

- Openjdk 17

## API

By default the application will listen on port 8080

it exposes 2 rest/json apis:

### messages

Rocket messages are received on a single endpoint: `/messages`

### rockets

Rocket information is exposed on 2 endpoints:

- `/rockets` returns a list of all known channels
- `/rockets/<channelID>` returns the current state of the rocket specified by that channel id. 

## Structure

This project uses a gradle multimodule build, with the following modules:

| Directory             | Description                                                 |
|-----------------------|-------------------------------------------------------------|
| modules/main          | The main application module bootstrap and configuration     |
 | modules/persistence   | Persistence layer using spring data JPA/Hibernate           | 
 | modules/service       | Service layers with business logic                          | 
 | modules/rest/messages | json rest api for receiving rocket status messages          | 
 | modules/rest/rockets  | json rest api for exposing current rocket state information | 


## Development

### Building

Build executable fat jar:

    ./gradlew bootJar

This will generate a `rocket-<version>.jar` file in modules/main/build/libs

### Testing

Run unit tests:

    ./gradlew test

### Running

Run the application directly from gradle:

    ./gradlew bootRun

