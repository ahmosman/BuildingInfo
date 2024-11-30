package pl.put.poznan.buildinginfo.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.buildinginfo.logic.BuildingParser;
import pl.put.poznan.buildinginfo.logic.entities.Building;
import pl.put.poznan.buildinginfo.logic.entities.Level;
import pl.put.poznan.buildinginfo.logic.entities.Room;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class BuildingInfoController {

    private static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);

    @RequestMapping(path = "/info", method = RequestMethod.POST, produces = "application/json")
    public String getInfo(@RequestBody String buildingJson) {
        try {

            Building building = BuildingParser.parseJson(buildingJson);

            logger.debug("Building object info: " + building.toString());

            return building.toString();
        } catch (Exception e) {
            logger.error("Error processing building JSON", e);
            return "{\"error\":\"Failed to process building JSON\"}";
        }
    }

    @RequestMapping(value = "/highRoomHeating/{heatingThreshold}", method = RequestMethod.POST, produces = "application/json")
    public List<Map<String, Object>> highRoomHeating(@PathVariable("heatingThreshold") double heatingThreshold, @RequestBody String buildingJson) {
        try {
            // Parse the building JSON input to create a Building object
            Building building = BuildingParser.parseJson(buildingJson);

            // List to store rooms that exceed the heating threshold
            List<Map<String, Object>> highHeatingRooms = new ArrayList<>();

            // Iterate through all levels of the building
            for (Level level : building.getLevels()) {
                // Iterate through all rooms on the current level
                for (Room room : level.getRooms()) {
                    // Calculate heating consumption per cubic meter for the room
                    double heatingPerCube = room.getCube() > 0 ? room.getHeating() / room.getCube() : 0.0;

                    // Check if the calculated value exceeds the specified threshold
                    if (heatingPerCube > heatingThreshold) {
                        // Prepare details of the room that exceeds the threshold
                        Map<String, Object> roomDetails = new HashMap<>();
                        roomDetails.put("roomId", room.getId());
                        roomDetails.put("heatingPerCube", Math.round(heatingPerCube * 100.0) / 100.0);

                        // Add the room details to the result list
                        highHeatingRooms.add(roomDetails);
                    }
                }
            }

            // Return the list of rooms exceeding the heating threshold
            return highHeatingRooms;

        } catch (Exception e) {
            // Log the error and return an empty list if an exception occurs
            logger.error("Error processing highRoomHeating", e);
            return Collections.emptyList();
        }
    }

    @RequestMapping(value = "/roomArea/{roomId}", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> roomArea(@PathVariable("roomId") String roomId, @RequestBody String buildingJson) {
        try {
            // Parsing the building JSON
            Building building = BuildingParser.parseJson(buildingJson);

            // Searching for the room with the given ID
            Optional<Room> roomOptional = building.getLevels().stream()
                    .flatMap(level -> level.getRooms().stream()) // Get all rooms from the levels
                    .filter(room -> room.getId().equals(roomId))  // Filter by room ID
                    .findFirst();                                // Get the first matching room (if any)

            // If the room is found
            if (roomOptional.isPresent()) {
                Room room = roomOptional.get();
                double roomArea = room.getArea(); // Get the area of the room

                // Rounding the result to two decimal places
                roomArea = Math.round(roomArea * 100.0) / 100.0;

                // Preparing the response
                Map<String, Object> response = new HashMap<>();
                response.put("roomArea", roomArea);

                return response; // The map will be automatically converted to JSON
            } else {
                // If the room with the given ID does not exist
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Room not found");
                return errorResponse;
            }

        } catch (Exception e) {
            logger.error("Error processing roomArea", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to process roomArea");
            return errorResponse;
        }
    }

    @RequestMapping(value = "/levelArea", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> levelArea(@RequestBody String buildingJson) {
        try {
            // Parsing the building JSON
            Building building = BuildingParser.parseJson(buildingJson);

            // Calculating the total area for each level
            Map<String, Double> levelArea = building.getLevels().stream()
                    .collect(Collectors.toMap(
                            Level::getId, // The key is the level ID
                            level -> {
                                // Summing up the areas of all rooms on the level
                                double totalArea = level.getRooms().stream()
                                        .mapToDouble(Room::getArea) // Get the area of each room
                                        .sum();
                                // Rounding to 2 decimal places
                                return Math.round(totalArea * 100.0) / 100.0;
                            }
                    ));

            // Preparing the response
            Map<String, Object> response = new HashMap<>();
            response.put("levelArea", levelArea);

            return response;

        } catch (Exception e) {
            logger.error("Error processing levelArea", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate level area");
            return errorResponse;
        }
    }

    @RequestMapping(value = "/buildingArea", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> buildingArea(@RequestBody String buildingJson) {
        try {
            // Parsing the building JSON
            Building building = BuildingParser.parseJson(buildingJson);

            // Calculating the total area for the building
            double totalBuildingArea = building.getLevels().stream()
                    .mapToDouble(level -> level.getRooms().stream()
                            .mapToDouble(Room::getArea) // Get the area of each room in the level
                            .sum())
                    .sum();

            // Rounding the total building area to 2 decimal places
            totalBuildingArea = Math.round(totalBuildingArea * 100.0) / 100.0;

            // Preparing the response
            Map<String, Object> response = new HashMap<>();
            response.put("totalBuildingArea", totalBuildingArea);

            return response;

        } catch (Exception e) {
            logger.error("Error processing buildingArea", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate building area");
            return errorResponse;
        }
    }

@RequestMapping(value = "/roomHeating/{roomId}", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> roomHeating(@PathVariable("roomId") String roomId, @RequestBody String buildingJson) {
        try {
            // Parsing the building JSON
            Building building = BuildingParser.parseJson(buildingJson);

            // Searching for the room with the given ID
            Optional<Room> roomOptional = building.getLevels().stream()
                    .flatMap(level -> level.getRooms().stream()) // Get all rooms from the levels
                    .filter(room -> room.getId().equals(roomId))  // Filter by room ID
                    .findFirst();                                // Get the first matching room (if any)

            // If the room is found
            if (roomOptional.isPresent()) {
                Room room = roomOptional.get();
                double heatingPerCube = room.getCube() > 0 ? room.getHeating() / room.getCube() : 0.0; // Calculate heating per cubic meter

                // Rounding the result to two decimal places
                heatingPerCube = Math.round(heatingPerCube * 100.0) / 100.0;

                // Preparing the response
                Map<String, Object> response = new HashMap<>();
                response.put("heatingPerCube", heatingPerCube);

                return response; // The map will be automatically converted to JSON
            } else {
                // If the room with the given ID does not exist
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Room not found");
                return errorResponse;
            }

        } catch (Exception e) {
            logger.error("Error processing roomHeating", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to process roomHeating");
            return errorResponse;
        }
    }




    @RequestMapping(value = "/roomHeatingPerLevel", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> roomHeatingPerLevel(@RequestBody String buildingJson) {
        try {
            // Parsing the building JSON
            Building building = BuildingParser.parseJson(buildingJson);

            // Calculating the average heating per cubic meter for each level
            Map<String, Double> averageHeatingPerLevel = building.getLevels().stream()
                    .collect(Collectors.toMap(
                            Level::getId, // The key is the level ID
                            level -> {
                                // Calculating total heating and total volume
                                double totalHeatingPerCube = level.getRooms().stream()
                                        .mapToDouble(room -> room.getHeating() / room.getCube()) // Heating per cubic meter
                                        .sum();
                                double totalCube = level.getRooms().stream()
                                        .mapToDouble(Room::getCube) // Total volume
                                        .sum();

                                // Calculating the average heating per cubic meter and rounding it to 2 decimal places
                                double averageHeating = totalCube > 0 ? totalHeatingPerCube / level.getRooms().size() : 0.0;
                                return Math.round(averageHeating * 100.0) / 100.0; // Rounding to 2 decimal places
                            }
                    ));

            // Preparing the response
            Map<String, Object> response = new HashMap<>();
            response.put("averageHeatingPerLevel", averageHeatingPerLevel);

            return response;

        } catch (Exception e) {
            logger.error("Error processing roomHeatingPerLevel", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate average heating per level");
            return errorResponse;
        }
    }




    @RequestMapping(value = "/roomHeatingPerBuilding", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> roomHeatingPerBuilding(@RequestBody String buildingJson) {
        try {
            // Parsing the building JSON
            Building building = BuildingParser.parseJson(buildingJson);

            // Calculating total heating and total volume in the building
            double totalHeatingPerCube = building.getLevels().stream()
                    .flatMap(level -> level.getRooms().stream()) // Getting all rooms in the building
                    .mapToDouble(room -> room.getHeating() / room.getCube()) // Heating per cubic meter for each room
                    .sum();

            double totalCube = building.getLevels().stream()
                    .flatMap(level -> level.getRooms().stream()) // Getting all rooms in the building
                    .mapToDouble(Room::getCube) // Total volume for each room
                    .sum();

            // Calculating the average heating per cubic meter for the entire building
            double averageHeatingForBuilding = totalCube > 0 ? totalHeatingPerCube / building.getLevels().stream()
                    .mapToDouble(level -> level.getRooms().size()).sum() : 0.0;

            // Rounding the average heating per cubic meter to 2 decimal places
            averageHeatingForBuilding = Math.round(averageHeatingForBuilding * 100.0) / 100.0;

            // Preparing the response
            Map<String, Object> response = new HashMap<>();
            response.put("averageHeatingForBuilding", averageHeatingForBuilding);

            return response;

        } catch (Exception e) {
            logger.error("Error processing roomHeatingPerBuilding", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate average heating for building");
            return errorResponse;
        }
    }

}