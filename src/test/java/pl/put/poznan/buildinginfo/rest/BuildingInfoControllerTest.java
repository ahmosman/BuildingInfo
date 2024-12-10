package pl.put.poznan.buildinginfo.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link BuildingInfoController} class, testing various methods for calculating area, heat,
 * and rooms exceeding a specific heat threshold.
 */
public class BuildingInfoControllerTest {

    private BuildingInfoController controller;
    private String jsonInput;

    /**
     * Sets up the test environment before each test.
     * Initializes a new instance of the {@link BuildingInfoController} and prepares a sample JSON input representing
     * the building's structure.
     */
    @BeforeEach
    void setUp(){
        controller = Mockito.spy(new BuildingInfoController());
        jsonInput = "{\n" +
                "  \"id\": \"building1\",\n" +
                "  \"name\": \"Main Office\",\n" +
                "  \"levels\": [\n" +
                "    {\n" +
                "      \"id\": \"level1\",\n" +
                "      \"name\": \"Ground Floor\",\n" +
                "      \"rooms\": [\n" +
                "        {\n" +
                "          \"id\": \"room1\",\n" +
                "          \"name\": \"Conference Room\",\n" +
                "          \"area\": 50.0,\n" +
                "          \"cube\": 150.0,\n" +
                "          \"heating\": 20.5,\n" +
                "          \"light\": 300.0\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"room2\",\n" +
                "          \"name\": \"Office 101\",\n" +
                "          \"area\": 30.0,\n" +
                "          \"cube\": 90.0,\n" +
                "          \"heating\": 15.0,\n" +
                "          \"light\": 200.0\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"level2\",\n" +
                "      \"name\": \"First Floor\",\n" +
                "      \"rooms\": [\n" +
                "        {\n" +
                "          \"id\": \"room3\",\n" +
                "          \"name\": \"Office 201\",\n" +
                "          \"area\": 25.0,\n" +
                "          \"cube\": 75.0,\n" +
                "          \"heating\": 10.0,\n" +
                "          \"light\": 150.0\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    /**
     * Tests the {@link BuildingInfoController#calculateArea(String, String)} method.
     * Verifies that the method correctly calculates the total area for a given floor.
     */
    @Test
    void testCalculateArea() throws Exception {
        // Mocking the calculateArea() method to return the correct area.
        assertEquals(80.0, controller.calculateArea(jsonInput, "Ground Floor").get("totalArea"));
    }

    /**
     * Tests the {@link BuildingInfoController#calculateHeat(String, String)} method.
     * Verifies that the method correctly calculates the total heating for the building.
     */
    @Test
    void testCalculateHeat() throws Exception {
        // Mocking the calculateHeat() method to return the correct total heat value.
        assertEquals(45.5, controller.calculateHeat(jsonInput, "Main Office").get("totalHeat"));
    }

    /**
     * Tests the {@link BuildingInfoController#highRoomHeating(String, double)} method.
     * Verifies that the method correctly identifies rooms exceeding a given heating threshold.
     */
    @Test
    void highRoomHeating() throws Exception {
        // Expected result (with ordered keys)
        String expectedRoomsExceedingThreshold =
                "[{cube=150.0, heatPerCube=0.14, heating=20.5, name=Conference Room}, " +
                        "{cube=90.0, heatPerCube=0.17, heating=15.0, name=Office 101}, " +
                        "{cube=75.0, heatPerCube=0.13, heating=10.0, name=Office 201}]";

        // Result returned by the method
        List<Map<String, Object>> roomsExceedingThreshold =
                (List<Map<String, Object>>) controller.highRoomHeating(jsonInput, 0.11).get("roomsExceedingThreshold");

        // Sorting the list by "name" key for deterministic order
        roomsExceedingThreshold.sort(Comparator.comparing(room -> room.get("name").toString()));

        // Reordering the keys in each map
        List<Map<String, Object>> orderedRoomsExceedingThreshold = roomsExceedingThreshold.stream()
                .map(room -> {
                    Map<String, Object> orderedMap = new LinkedHashMap<>();
                    orderedMap.put("cube", room.get("cube"));
                    orderedMap.put("heatPerCube", room.get("heatPerCube"));
                    orderedMap.put("heating", room.get("heating"));
                    orderedMap.put("name", room.get("name"));
                    return orderedMap;
                })
                .collect(Collectors.toList());

        // Converting the result to a String
        String actualRoomsExceedingThreshold = orderedRoomsExceedingThreshold.toString();

        // Comparing the expected and actual results
        assertEquals(expectedRoomsExceedingThreshold, actualRoomsExceedingThreshold);
    }
}
