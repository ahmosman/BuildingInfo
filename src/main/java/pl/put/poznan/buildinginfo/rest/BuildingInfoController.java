package pl.put.poznan.buildinginfo.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.buildinginfo.logic.BuildingParser;
import pl.put.poznan.buildinginfo.logic.entities.Building;
import pl.put.poznan.buildinginfo.logic.entities.BuildingComponent;
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

    @RequestMapping(value = "/calculateArea", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> calculateArea(@RequestBody String buildingJson, @RequestParam(value = "name", required = false) String name) {
        try {
            // Parsowanie JSON budynku
            Building building = BuildingParser.parseJson(buildingJson);

            double totalArea = 0.0;

            if (name != null && !name.isEmpty()) {
                // Przeszukiwanie hierarchii w poszukiwaniu komponentu z dopasowanym name
                Optional<BuildingComponent> foundComponent = findComponentByName(building, name);

                if (foundComponent.isPresent()) {
                    // Obliczamy powierzchnię dla znalezionego komponentu
                    totalArea = foundComponent.get().calculateArea();
                } else {
                    // Jeśli komponent o danym name nie został znaleziony, zgłaszamy błąd
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("error", "Component with given name not found");
                    return errorResponse;
                }
            } else {
                // Jeśli nie podano name, obliczamy powierzchnię całego budynku
                totalArea = building.calculateArea();
            }

            // Zaokrąglamy wynik do dwóch miejsc po przecinku
            totalArea = Math.round(totalArea * 100.0) / 100.0;

            // Przygotowanie odpowiedzi
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

    @RequestMapping(value = "/calculateHeat", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> calculateHeat(@RequestBody String buildingJson, @RequestParam(value = "name", required = false) String name) {
        try {
            // Parsowanie JSON budynku
            Building building = BuildingParser.parseJson(buildingJson);

            double totalHeat = 0.0;

            if (name != null && !name.isEmpty()) {
                // Przeszukiwanie hierarchii w poszukiwaniu komponentu z dopasowanym name
                Optional<BuildingComponent> foundComponent = findComponentByName(building, name);

                if (foundComponent.isPresent()) {
                    // Obliczamy powierzchnię dla znalezionego komponentu
                    totalHeat = foundComponent.get().calculateHeat();
                } else {
                    // Jeśli komponent o danym name nie został znaleziony, zgłaszamy błąd
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("error", "Component with given name not found");
                    return errorResponse;
                }
            } else {
                // Jeśli nie podano name, obliczamy powierzchnię całego budynku
                totalHeat = building.calculateHeat();
            }

            // Zaokrąglamy wynik do dwóch miejsc po przecinku
            totalHeat = Math.round(totalHeat * 100.0) / 100.0;

            // Przygotowanie odpowiedzi
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
     * Rekurencyjna metoda pomocnicza do znajdowania komponentu po name w hierarchii budynku.
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


    @RequestMapping(value = "/highRoomHeating", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> highRoomHeating(@RequestBody String buildingJson, @RequestParam(value = "threshold", required = true) double threshold) {
        try {
            // Parsowanie JSON budynku
            Building building = BuildingParser.parseJson(buildingJson);

            // Lista pomieszczeń przekraczających zadany próg zużycia energii cieplnej
            List<Room> roomsExceedingThreshold = findRoomsExceedingHeatThreshold(building, threshold);

            // Przygotowanie odpowiedzi
            Map<String, Object> response = new HashMap<>();
            response.put("roomsExceedingThreshold", roomsExceedingThreshold.stream()
                    .map(room -> Map.of(
                            "name", room.getName(),
                            "cube", room.getCube(),
                            "heating", room.getHeating(),
                            "heatPerCube", Math.round((room.getHeating() / room.getCube() )* 100.0) / 100.0
                    ))
                    .collect(Collectors.toList())); // Konwertujemy listę pokoi do map JSON-owych
            return response;

        } catch (Exception e) {
            logger.error("Error processing calculateHeat", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate heat");
            return errorResponse;
        }
    }

    /**
     * Rekurencyjna metoda pomocnicza do znajdowania pomieszczeń przekraczających zadany próg zużycia energii cieplnej.
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