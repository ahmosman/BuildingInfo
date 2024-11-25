package pl.put.poznan.buildinginfo.logic.entities;

import java.util.List;

public class Level {
    private String id;
    private String name;
    private List<Room> rooms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  Level ID: ").append(id).append("\n");
        sb.append("  Name: ").append(name).append("\n");
        sb.append("  Rooms:\n");
        for (Room room : rooms) {
            sb.append(room.toString()).append("\n");
        }
        return sb.toString();
    }
}