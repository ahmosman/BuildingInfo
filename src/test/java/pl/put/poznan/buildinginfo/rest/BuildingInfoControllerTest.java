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
 * light, cube, person per area, restrooms, and lighting.
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
    void setUp() {
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

    @Test
    void testCalculateArea() throws Exception {
        assertEquals(80.0, controller.calculateArea(jsonInput, "Ground Floor").get("totalArea"));
    }

    @Test
    void testCalculateHeatBuilding() throws Exception {
        assertEquals(45.5, controller.calculateHeat(jsonInput, "Main Office").get("totalHeat"));
    }

    @Test
    void testCalculateHeatRoom() throws Exception {
        assertEquals(10.0, controller.calculateHeat(jsonInput, "Office 201").get("totalHeat"));
    }

    @Test
    void testHighRoomHeating() throws Exception {
        String expectedRoomsExceedingThreshold =
                "[{cube=150.0, heatPerCube=0.14, heating=20.5, name=Conference Room}, " +
                        "{cube=90.0, heatPerCube=0.17, heating=15.0, name=Office 101}, " +
                        "{cube=75.0, heatPerCube=0.13, heating=10.0, name=Office 201}]";

        List<Map<String, Object>> roomsExceedingThreshold =
                (List<Map<String, Object>>) controller.highRoomHeating(jsonInput, 0.11).get("roomsExceedingThreshold");
        roomsExceedingThreshold.sort(Comparator.comparing(room -> room.get("name").toString()));

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

        String actualRoomsExceedingThreshold = orderedRoomsExceedingThreshold.toString();
        assertEquals(expectedRoomsExceedingThreshold, actualRoomsExceedingThreshold);
    }

    @Test
    void testCalculatePersonPerAreaLevel() throws Exception {
        Map<String, Object> response = controller.calculatePersonPerArea(jsonInput, "Ground Floor");
        assertEquals(80.0, response.get("totalArea"));
        assertEquals(26, response.get("maxPeople"));
    }

    @Test
    void testCalculatePersonPerAreaBuilding() throws Exception {
        Map<String, Object> response = controller.calculatePersonPerArea(jsonInput, "Main Office");
        assertEquals(105.0, response.get("totalArea"));
        assertEquals(35, response.get("maxPeople"));
    }

    @Test
    void testCalculateCubeBuilding() throws Exception {
        Map<String, Object> response = controller.calculateCube(jsonInput, "Main Office");
        assertEquals(315.0, response.get("totalCube"));
    }

    @Test
    void testCalculateCubeLevel() throws Exception {
        Map<String, Object> response = controller.calculateCube(jsonInput, "Ground Floor");
        assertEquals(240.0, response.get("totalCube"));
    }

    @Test
    void testCalculateCubeRoom() throws Exception {
        Map<String, Object> response = controller.calculateCube(jsonInput, "Office 201");
        assertEquals(75.0, response.get("totalCube"));
    }

    @Test
    void testCalculateRestroomsBuilding() throws Exception {
        Map<String, Object> response = controller.calculateRestrooms(jsonInput, "Main Office");
        assertEquals(35, response.get("maxPeople"));
        assertEquals(3, response.get("requiredRestrooms"));
    }

    @Test
    void testCalculateRestroomsLevel() throws Exception {
        Map<String, Object> response = controller.calculateRestrooms(jsonInput, "Ground Floor");
        assertEquals(26, response.get("maxPeople"));
        assertEquals(2, response.get("requiredRestrooms"));
    }


    @Test
    void testCalculateTotalLightLevel() throws Exception {
        Map<String, Object> response = controller.calculateTotalLight(jsonInput, "Ground Floor");
        assertEquals(500.0, response.get("totalLight"));
    }


    @Test
    void testCalculateTotalLight() throws Exception {
        Map<String, Object> response = controller.calculateTotalLight(jsonInput, null);
        assertEquals(650.0, response.get("totalLight"));
    }

    @Test
    void testCalculateLightingRoom() throws Exception {
        Map<String, Object> response = controller.calculateLighting(jsonInput, "Office 101");
        assertEquals(6.67, response.get("lightingPerArea"));
    }
    @Test
    void testCalculateLighting() throws Exception {
        Map<String, Object> response = controller.calculateLighting(jsonInput, null);
        assertEquals(6.19, response.get("lightingPerArea"));
    }
}

