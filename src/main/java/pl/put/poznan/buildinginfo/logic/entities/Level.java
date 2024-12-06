package pl.put.poznan.buildinginfo.logic.entities;

import java.util.ArrayList;
import java.util.List;

public class Level extends BuildingComponent {
    private List<BuildingComponent> rooms = new ArrayList<>();

    public Level(String id, String name) {
        super(id, name);
    }

    public void setRooms(List<Room> _rooms) {
        for (Room room : _rooms) {
            rooms.add(room);  // Dodajemy każdy pokój do komponentów
        }
    }

    @Override
    public void addComponent(BuildingComponent component) {
        rooms.add(component);
    }

    @Override
    public void removeComponent(BuildingComponent component) {
        rooms.remove(component);
    }

    @Override
    public List<BuildingComponent> getComponents() {
        return rooms;
    }

    @Override
    public double calculateArea() {
        return rooms.stream()
                .mapToDouble(BuildingComponent::calculateArea)
                .sum();
    }

    @Override
    public double calculateHeat() {
        return rooms.stream()
                .mapToDouble(BuildingComponent::calculateHeat)
                .sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Level ID: ").append(getId()).append("\n")
                .append("Name: ").append(getName()).append("\n")
                .append("Rooms: \n");
        for (BuildingComponent room : rooms) {
            sb.append("  ").append(room.toString()).append("\n");
        }
        return sb.toString();
    }
}