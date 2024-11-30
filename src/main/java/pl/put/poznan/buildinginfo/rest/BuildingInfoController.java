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



}


