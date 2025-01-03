package pl.put.poznan.buildinginfo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.buildinginfo.logic.BuildingFinder;
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
                    ? BuildingFinder.findComponentByName(building, name).map(BuildingComponent::calculateArea).orElseThrow(() -> new IllegalArgumentException("Component with given name not found"))
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
                    ? BuildingFinder.findComponentByName(building, name).map(BuildingComponent::calculateHeat).orElseThrow(() -> new IllegalArgumentException("Component with given name not found"))
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

            List<Room> roomsExceedingThreshold = BuildingFinder.findRoomsExceedingHeatThreshold(building, threshold);

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

    @RequestMapping(value = "/calculatePersonPerArea", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> calculatePersonPerArea(@RequestBody String buildingJson,
                                                      @RequestParam(value = "name", required = false) String name) {
        try {

            Building building = BuildingParser.parseJson(buildingJson);

            double totalArea = name != null && !name.isEmpty()
                    ? BuildingFinder.findComponentByName(building, name)
                    .map(BuildingComponent::calculateArea)
                    .orElseThrow(() -> new IllegalArgumentException("Component with given name not found"))
                    : building.calculateArea();

            int maxPeople = (int) Math.floor(totalArea / 3.0);

            Map<String, Object> response = new HashMap<>();
            response.put("totalArea", Math.round(totalArea * 100.0) / 100.0);
            response.put("maxPeople", maxPeople);
            return response;
        } catch (Exception e) {

            logger.error("Error processing calculatePersonPerArea", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate person per area");
            return errorResponse;
        }
    }

    @RequestMapping(value = "/calculateCube", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> calculateCube(@RequestBody String buildingJson,
                                             @RequestParam(value = "name", required = false) String name) {
        try {

            Building building = BuildingParser.parseJson(buildingJson);




            double totalCube = name != null && !name.isEmpty()
                    ? BuildingFinder.findComponentByName(building, name)
                    .map(BuildingComponent::calculateCube)
                    .orElseThrow(() -> new IllegalArgumentException("Component with given name not found"))
                    : building.calculateCube();


            Map<String, Object> response = new HashMap<>();
            response.put("totalCube", Math.round(totalCube * 100.0) / 100.0);
            return response;
        } catch (Exception e) {

            logger.error("Error processing calculateCube", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate cube");
            return errorResponse;
        }
    }

    @RequestMapping(value = "/calculateRestrooms", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> calculateRestrooms(@RequestBody String buildingJson,
                                                  @RequestParam(value = "name", required = false) String name) {
        try {

            Building building = BuildingParser.parseJson(buildingJson);

            Map<String, Object> personPerAreaResponse = calculatePersonPerArea(buildingJson, name);

            if (personPerAreaResponse.containsKey("error")) {
                throw new IllegalArgumentException((String) personPerAreaResponse.get("error"));
            }

            int maxPeople = (int) personPerAreaResponse.get("maxPeople");

            int restrooms = (int) Math.ceil(maxPeople / 15.0);

            Map<String, Object> response = new HashMap<>();
            response.put("maxPeople", maxPeople);
            response.put("requiredRestrooms", restrooms);
            return response;
        } catch (Exception e) {

            logger.error("Error processing calculateRestrooms", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate restrooms");
            return errorResponse;
        }
    }

    @RequestMapping(value = "/calculateTotalLight", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> calculateTotalLight(@RequestBody String buildingJson,
                                                   @RequestParam(value = "name", required = false) String name) {
        try {
            Building building = BuildingParser.parseJson(buildingJson);
            double totalLight = name != null && !name.isEmpty()
                    ? BuildingFinder.findComponentByName(building, name).map(BuildingComponent::calculateLight).orElseThrow(() -> new IllegalArgumentException("Component with given name not found"))
                    : building.calculateLight();
            totalLight = Math.round(totalLight * 100.0) / 100.0;
            Map<String, Object> response = new HashMap<>();
            response.put("totalLight", totalLight);
            return response;
        } catch (Exception e) {
            logger.error("Error processing calculateTotalLight", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate total light");
            return errorResponse;
        }
    }

    @RequestMapping(value = "/calculateLighting", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> calculateLighting(@RequestBody String buildingJson,
                                                 @RequestParam(value = "name", required = false) String name) {
        try {

            Building building = BuildingParser.parseJson(buildingJson);

            double lightingPerArea;

            if (name != null && !name.isEmpty()) {

                BuildingComponent component = BuildingFinder.findComponentByName(building, name)
                        .orElseThrow(() -> new IllegalArgumentException("Component with given name not found"));
                lightingPerArea = component.calculateLight() / component.calculateArea();
            } else {

                Map<String, Object> totalLightResponse = calculateTotalLight(buildingJson, null);
                if (totalLightResponse.containsKey("error")) {
                    throw new IllegalArgumentException((String) totalLightResponse.get("error"));
                }
                double totalLight = (double) totalLightResponse.get("totalLight");
                double totalArea = building.calculateArea();
                lightingPerArea = totalLight / totalArea;
            }

            lightingPerArea = Math.round(lightingPerArea * 100.0) / 100.0;

            Map<String, Object> response = new HashMap<>();
            response.put("lightingPerArea", lightingPerArea);
            return response;
        } catch (Exception e) {
            logger.error("Error processing calculateLighting", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate lighting per area");
            return errorResponse;
        }
    }



}
