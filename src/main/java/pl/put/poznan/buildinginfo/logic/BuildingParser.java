package pl.put.poznan.buildinginfo.logic;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.put.poznan.buildinginfo.logic.entities.Building;
import pl.put.poznan.buildinginfo.logic.entities.Level;
import pl.put.poznan.buildinginfo.logic.entities.Room;

import java.util.ArrayList;
import java.util.List;

public class BuildingParser {
    public static Building parseJson(String json) {
        JSONObject jsonObject = new JSONObject(json);

        Building building = new Building();
        building.setId(jsonObject.getString("id"));
        building.setName(jsonObject.optString("name", null));

        JSONArray levelsArray = jsonObject.getJSONArray("levels");
        List<Level> levels = new ArrayList<>();
        for (int i = 0; i < levelsArray.length(); i++) {
            JSONObject levelObject = levelsArray.getJSONObject(i);
            Level level = new Level();
            level.setId(levelObject.getString("id"));
            level.setName(levelObject.optString("name", null));

            JSONArray roomsArray = levelObject.getJSONArray("rooms");
            List<Room> rooms = new ArrayList<>();
            for (int j = 0; j < roomsArray.length(); j++) {
                JSONObject roomObject = roomsArray.getJSONObject(j);
                Room room = new Room();
                room.setId(roomObject.getString("id"));
                room.setName(roomObject.optString("name", null));
                room.setArea(roomObject.getDouble("area"));
                room.setCube(roomObject.getDouble("cube"));
                room.setHeating((float) roomObject.getDouble("heating"));
                room.setLight((float) roomObject.getDouble("light"));
                rooms.add(room);
            }
            level.setRooms(rooms);
            levels.add(level);
        }
        building.setLevels(levels);

        return building;
    }
}