package pl.put.poznan.buildinginfo.logic;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.put.poznan.buildinginfo.logic.entities.Building;
import pl.put.poznan.buildinginfo.logic.entities.Level;
import pl.put.poznan.buildinginfo.logic.entities.Room;

import java.util.ArrayList;
import java.util.List;

public class BuildingParser {

    // Metoda do parsowania JSON i tworzenia obiektu Building
    public static Building parseJson(String json) {
        JSONObject jsonObject = new JSONObject(json);
        Building building = new Building(
                jsonObject.getString("id"),
                jsonObject.optString("name", null));  // Tworzymy obiekt Building

        // Parsowanie poziomów
        JSONArray levelsArray = jsonObject.getJSONArray("levels");
        List<Level> levels = new ArrayList<>();

        for (int i = 0; i < levelsArray.length(); i++) {
            JSONObject levelObject = levelsArray.getJSONObject(i);
            Level level = new Level(
                    levelObject.getString("id"),
                    levelObject.optString("name", null) );  // Tworzymy nowy poziom

            // Parsowanie pokoi na poziomie
            JSONArray roomsArray = levelObject.getJSONArray("rooms");
            List<Room> rooms = new ArrayList<>();
            for (int j = 0; j < roomsArray.length(); j++) {
                JSONObject roomObject = roomsArray.getJSONObject(j);
                Room room = new Room(roomObject.getString("id"),
                        roomObject.optString("name", null),
                        roomObject.getDouble("area"),
                        roomObject.getDouble("cube"),
                        (float) roomObject.getDouble("heating"),
                        (float) roomObject.getDouble("light")
                );  // Tworzymy nowy pokój
                rooms.add(room);  // Dodajemy pokój do listy pokoi
            }

            // Dodajemy pokoje do poziomu
            level.setRooms(rooms);
            // Dodajemy poziom do budynku (w formie BuildingComponent)
            building.addComponent(level);
        }

        // Dodajemy poziomy do budynku
        building.setLevels(levels);

        return building;  // Zwracamy stworzony obiekt Building
    }
}
