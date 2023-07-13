# Drone Logistic

Drone Logistic is a Spring Boot project that allows for the management of a fleet of up to 10 drones for the purpose of delivering medications.

## Technologies

This project is built using the following technologies:

- Spring Boot
- Java 11
- JPA
- H2 in-memory database

## Features

- Manage a fleet of up to 10 drones
- Drones can be in various states such as `IDLE`, `LOADED`, and `DELIVERED`
- Only `IDLE` drones are available for loading medications
- Only `LOADED` drones are available for delivering medications
- Medications can be in two states: delivered or not delivered
- If a medication is not delivered, it is available to be loaded onto a drone
- Once loaded, a medication is ready to be delivered

## Getting Started

To run this project locally, follow these steps:

1. Clone this repository to your local machine.
2. Open the project in your preferred IDE and ensure that you have Java 11 installed and selected as the project SDK.
3. Run the project using the IDE's built-in tools or by running the command `./mvnw spring-boot:run` from the project's root directory.
4. The application will start and be available at `http://localhost:8080`.

