package pl.put.poznan.buildinginfo.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.buildinginfo.logic.BuildingParser;
import pl.put.poznan.buildinginfo.logic.entities.Building;
import pl.put.poznan.buildinginfo.logic.entities.Level;
import pl.put.poznan.buildinginfo.logic.entities.Room;

import java.util.ArrayList;
import java.util.List;

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
}