package pl.put.poznan.buildinginfo.logic.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a level within a building, containing multiple rooms.
 * This class extends {@link BuildingComponent} and provides methods to manage
 * and aggregate information about rooms on this level.
 */
public class Level extends BuildingComponent {
    private List<BuildingComponent> rooms = new ArrayList<>();

    /**
     * Constructs a Level with the given ID and name.
     *
     * @param id   the unique identifier of the level
     * @param name the name of the level
     */
    public Level(String id, String name) {
        super(id, name);
    }

    /**
     * Sets the rooms on this level.
     *
     * @param _rooms a list of {@link Room} instances to add to the level
     * @throws IllegalArgumentException if any of the provided rooms are null
     */
    public void setRooms(List<Room> _rooms) {
        for (Room room : _rooms) {
            if (room == null) {
                throw new IllegalArgumentException("Room cannot be null");
            }
            rooms.add(room);  // Adds each room to the level's components
        }
    }

    /**
     * Adds a room or subcomponent to the level.
     *
     * @param component the {@link BuildingComponent} to add
     */
    @Override
    public void addComponent(BuildingComponent component) {
        rooms.add(component);
    }

    /**
     * Removes a room or subcomponent from the level.
     *
     * @param component the {@link BuildingComponent} to remove
     */
    @Override
    public void removeComponent(BuildingComponent component) {
        rooms.remove(component);
    }

    /**
     * Returns the list of rooms (components) on this level.
     *
     * @return a list of {@link BuildingComponent} instances representing the rooms
     */
    @Override
    public List<BuildingComponent> getComponents() {
        return rooms;
    }

    /**
     * Calculates the total area of the level by summing the areas of all rooms.
     *
     * @return the total area of the level
     */
    @Override
    public double calculateArea() {
        return rooms.stream()
                .mapToDouble(BuildingComponent::calculateArea)
                .sum();
    }

    /**
     * Calculates the total heat demand of the level by summing the heat demands of all rooms.
     *
     * @return the total heat demand of the level
     */
    @Override
    public double calculateHeat() {
        return rooms.stream()
                .mapToDouble(BuildingComponent::calculateHeat)
                .sum();
    }

    @Override
    public double calculateCube() {
        return rooms.stream()
                .mapToDouble(BuildingComponent::calculateCube)
                .sum();
    }

    @Override
    public double calculateLight() {
        return rooms.stream()
                .mapToDouble(BuildingComponent::calculateLight)
                .sum();
    }

    /**
     * Returns a string representation of the level, including its ID, name, and all rooms.
     *
     * @return a string describing the level
     */
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
