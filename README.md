# BuildingInfo

![Release Date](https://img.shields.io/badge/release-November%202024-blue)

A Java Spring Boot application for calculating and analyzing building information such as area, heating, lighting, and more. The project supports both REST API and command-line interface (CLI) usage.

## Features

- Parse building structure from JSON
- Calculate total area, heating, cube (volume), and lighting for buildings, levels, or rooms
- Find rooms with high heating per cubic meter
- Calculate maximum occupancy and required restrooms
- REST API endpoints for all calculations
- Command-line interface for interactive use

## Project Structure

- `src/main/java/pl/put/poznan/buildinginfo/app/` - Application entry points (REST and CLI)
- `src/main/java/pl/put/poznan/buildinginfo/logic/` - Core logic, parsing, and utilities
- `src/main/java/pl/put/poznan/buildinginfo/logic/entities/` - Building, Level, Room entities
- `src/main/java/pl/put/poznan/buildinginfo/rest/` - REST controllers
- `src/main/resources/` - Configuration and example building JSON
- `examples/` - Example building JSON files
- `src/test/` - Unit tests

## How to Build

Make sure you have Java 11 and Maven installed.

```sh
mvn clean package
```

## How to Run

### REST API

Start the Spring Boot application:

```sh
mvn spring-boot:run
```

The REST API will be available at `http://localhost:8080/`. Example endpoints:

- `POST /info`
- `POST /calculateArea`
- `POST /calculateHeat`
- `POST /calculateCube`
- `POST /calculateRestrooms`
- `POST /calculateTotalLight`
- `POST /calculateLighting`
- `POST /highRoomHeating`

Send a JSON body representing the building structure (see [examples/example1.json](examples/example1.json)).

### Command-Line Interface

Run the CLI application:

```sh
java -cp target/io-project-architecture-1.7-final.jar pl.put.poznan.buildinginfo.app.BuildingInfoCMD
```

Follow the interactive menu to perform calculations.

## Example JSON

See [examples/example1.json](examples/example1.json) for the expected input format.

## Running Tests

```sh
mvn test
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.