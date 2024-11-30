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


