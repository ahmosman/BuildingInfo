package pl.put.poznan.buildinginfo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.buildinginfo.logic.BuildingParser;
import pl.put.poznan.buildinginfo.logic.entities.Building;
import pl.put.poznan.buildinginfo.logic.entities.BuildingComponent;
import pl.put.poznan.buildinginfo.logic.entities.Level;
import pl.put.poznan.buildinginfo.logic.entities.Room;

import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for providing building information and calculations.
 */
@RestController
public class BuildingInfoController {

    private static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);

    /**
     * Endpoint to retrieve detailed information about a building.
     *
     * @param buildingJson JSON string representing the building structure
     * @return A string representation of the building object or an error message
     */
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

    /**
     * Endpoint to calculate the total area of a building or a specific component.
     *
     * @param buildingJson JSON string representing the building structure
     * @param name         (Optional) Name of the specific component to calculate the area for
     * @return A map containing the total area or an error message
     */
    @RequestMapping(value = "/calculateArea", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> calculateArea(@RequestBody String buildingJson, @RequestParam(value = "name", required = false) String name) {
        try {
            Building building = BuildingParser.parseJson(buildingJson);

            double totalArea = name != null && !name.isEmpty()
                    ? findComponentByName(building, name).map(BuildingComponent::calculateArea).orElseThrow(() -> new IllegalArgumentException("Component with given name not found"))
                    : building.calculateArea();

            totalArea = Math.round(totalArea * 100.0) / 100.0;

            Map<String, Object> response = new HashMap<>();
            response.put("totalArea", totalArea);
            return response;
        } catch (Exception e) {
            logger.error("Error processing calculateArea", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate area");
            return errorResponse;
        }
    }

    /**
     * Endpoint to calculate the total heating demand of a building or a specific component.
     *
     * @param buildingJson JSON string representing the building structure
     * @param name         (Optional) Name of the specific component to calculate the heating for
     * @return A map containing the total heating or an error message
     */
    @RequestMapping(value = "/calculateHeat", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> calculateHeat(@RequestBody String buildingJson, @RequestParam(value = "name", required = false) String name) {
        try {
            Building building = BuildingParser.parseJson(buildingJson);

            double totalHeat = name != null && !name.isEmpty()
                    ? findComponentByName(building, name).map(BuildingComponent::calculateHeat).orElseThrow(() -> new IllegalArgumentException("Component with given name not found"))
                    : building.calculateHeat();

            totalHeat = Math.round(totalHeat * 100.0) / 100.0;

            Map<String, Object> response = new HashMap<>();
            response.put("totalHeat", totalHeat);
            return response;
        } catch (Exception e) {
            logger.error("Error processing calculateHeat", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate heat");
            return errorResponse;
        }
    }

    /**
     * Endpoint to find rooms with heating per cubic meter exceeding a given threshold.
     *
     * @param buildingJson JSON string representing the building structure
     * @param threshold    The heating threshold per cubic meter
     * @return A map containing rooms exceeding the threshold or an error message
     */
    @RequestMapping(value = "/highRoomHeating", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> highRoomHeating(@RequestBody String buildingJson, @RequestParam(value = "threshold") double threshold) {
        try {
            Building building = BuildingParser.parseJson(buildingJson);

            List<Room> roomsExceedingThreshold = findRoomsExceedingHeatThreshold(building, threshold);

            Map<String, Object> response = new HashMap<>();
            response.put("roomsExceedingThreshold", roomsExceedingThreshold.stream()
                    .map(room -> Map.of(
                            "name", room.getName(),
                            "cube", room.getCube(),
                            "heating", room.getHeating(),
                            "heatPerCube", Math.round((room.getHeating() / room.getCube()) * 100.0) / 100.0
                    ))
                    .collect(Collectors.toList()));
            return response;
        } catch (Exception e) {
            logger.error("Error processing highRoomHeating", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate heat");
            return errorResponse;
        }
    }

    /**
     * Helper method to recursively find a component by name in the building hierarchy.
     *
     * @param component The root component to start the search
     * @param name      The name of the component to find
     * @return An {@link Optional} containing the component if found, otherwise empty
     */
    private Optional<BuildingComponent> findComponentByName(BuildingComponent component, String name) {
        if (component.getName().equalsIgnoreCase(name)) {
            return Optional.of(component);
        }
        for (BuildingComponent child : component.getComponents()) {
            Optional<BuildingComponent> found = findComponentByName(child, name);
            if (found.isPresent()) {
                return found;
            }
        }
        return Optional.empty();
    }

    /**
     * Helper method to find rooms with heating per cubic meter exceeding a given threshold.
     *
     * @param component The root component to start the search
     * @param threshold The heating threshold per cubic meter
     * @return A list of rooms exceeding the threshold
     */
    private List<Room> findRoomsExceedingHeatThreshold(BuildingComponent component, double threshold) {
        List<Room> roomsExceedingThreshold = new ArrayList<>();

        if (component instanceof Room) {
            Room room = (Room) component;
            double heatPerCube = room.getHeating() / room.getCube();
            if (heatPerCube > threshold) {
                roomsExceedingThreshold.add(room);
            }
        } else {
            for (BuildingComponent child : component.getComponents()) {
                roomsExceedingThreshold.addAll(findRoomsExceedingHeatThreshold(child, threshold));
            }
        }

        return roomsExceedingThreshold;
    }
}
