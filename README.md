# Drone Logistic

Drone Logistic is a Spring Boot project that allows for the management of a fleet of up to 10 drones for the purpose of delivering medications.

## Technologies

This project is built using the following technologies:

- Spring Boot
- gradle
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


## Additional Features

- CRUD operations for drones and medications
- API to fetch available drones (i.e. drones that are in `IDLE` state and ready to load medications)
- API to fetch available medications (i.e. medications that are not delivered and not loaded in any drone)
- API to load medications onto drones (changes drone state from `IDLE` to `LOADED` and adds drone ID to medication)
- API to deliver medications by drones (changes medication `isDelivered` field from `false` to `true` and changes drone state to `DELIVERED`)
- Periodic task that runs every 60 seconds to check drone battery levels and store logs in the database
- API to get medication by ID includes a downloadable image URL
- Validations for drone serial number (max 100 characters), weight limit (max 500g), medication name (allowed characters: letters, numbers, `-`, `_`), preventing overloading of drones, and preventing loading of drones with battery level below 25%

## Postman Collection

A Postman collection containing example API requests is included in this repository. To import the collection into Postman, follow these steps:

1. Open Postman and click on the `Import` button in the top left corner.
2. In the `Import` dialog, select the `File` tab and click on `Choose Files`.
3. Navigate to the `postman` directory in this repository and select the JSON file representing the collection.
4. Click on `Open` to import the collection into Postman.

Once imported, you can use the requests in the collection to test the API endpoints provided by this project.


## Getting Started

To run this project locally, follow these steps:

1. Clone this repository to your local machine.
2. Open the project in your preferred IDE and ensure that you have Java 11 installed and selected as the project SDK.
3. Run the project using the IDE's built-in tools or by running the command `./gradlew bootRun` from the project's root directory.
4. The application will start and be available at `http://localhost:8080`.

