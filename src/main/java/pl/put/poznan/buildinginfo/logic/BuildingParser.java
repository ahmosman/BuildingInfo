package pl.put.poznan.buildinginfo.logic;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.put.poznan.buildinginfo.logic.entities.Building;
import pl.put.poznan.buildinginfo.logic.entities.Level;
import pl.put.poznan.buildinginfo.logic.entities.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for parsing JSON data and creating a {@link Building} object hierarchy.
 */
public class BuildingParser {

    /**
     * Parses a JSON string and constructs a {@link Building} object.
     *
     * The JSON structure is expected to include the following:
     * - "id" (String): The unique identifier for the building
     * - "name" (String, optional): The name of the building
     * - "levels" (Array): An array of levels, where each level contains:
     *     - "id" (String): The unique identifier for the level
     *     - "name" (String, optional): The name of the level
     *     - "rooms" (Array): An array of rooms, where each room contains:
     *         - "id" (String): The unique identifier for the room
     *         - "name" (String, optional): The name of the room
     *         - "area" (double): The area of the room in square meters
     *         - "cube" (double): The volume of the room in cubic meters
     *         - "heating" (double): The heating demand of the room
     *         - "light" (double): The lighting level of the room in lumens
     *
     * @param json a JSON string representing the building structure
     * @return a {@link Building} object constructed from the JSON data
     * @throws org.json.JSONException if the JSON data is invalid or missing required fields
     */
    public static Building parseJson(String json) {
        JSONObject jsonObject = new JSONObject(json);

        // Create a Building object
        Building building = new Building(
                jsonObject.getString("id"),
                jsonObject.optString("name", null)); // Use optString to handle optional "name"

        // Parse levels
        JSONArray levelsArray = jsonObject.getJSONArray("levels");
        List<Level> levels = new ArrayList<>();

        for (int i = 0; i < levelsArray.length(); i++) {
            JSONObject levelObject = levelsArray.getJSONObject(i);

            // Create a Level object
            Level level = new Level(
                    levelObject.getString("id"),
                    levelObject.optString("name", null)); // Use optString to handle optional "name"

            // Parse rooms for the level
            JSONArray roomsArray = levelObject.getJSONArray("rooms");
            List<Room> rooms = new ArrayList<>();
            for (int j = 0; j < roomsArray.length(); j++) {
                JSONObject roomObject = roomsArray.getJSONObject(j);

                // Create a Room object
                Room room = new Room(
                        roomObject.getString("id"),
                        roomObject.optString("name", null),
                        roomObject.getDouble("area"),
                        roomObject.getDouble("cube"),
                        (float) roomObject.getDouble("heating"),
                        (float) roomObject.getDouble("light")
                );
                rooms.add(room); // Add the room to the list of rooms
            }

            // Add rooms to the level
            level.setRooms(rooms);

            // Add the level to the list of levels
            levels.add(level);
        }

        // Add levels to the building
        building.setLevels(levels);

        return building; // Return the constructed Building object
    }
}
