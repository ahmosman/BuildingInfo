package pl.put.poznan.buildinginfo.logic.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a building composed of multiple levels.
 * This class extends {@link BuildingComponent} and allows managing
 * and aggregating information about the building's levels.
 */
public class Building extends BuildingComponent {
    private List<BuildingComponent> levels = new ArrayList<>();

    /**
     * Constructs a Building with the given ID and name.
     *
     * @param id   the unique identifier of the building
     * @param name the name of the building
     */
    public Building(String id, String name) {
        super(id, name);
    }

    /**
     * Sets the levels of the building.
     *
     * @param levels a list of {@link Level} instances to add to the building
     * @throws IllegalArgumentException if any of the provided levels are null
     */
    public void setLevels(List<Level> levels) {
        for (Level level : levels) {
            if (level == null) {
                throw new IllegalArgumentException("Level cannot be null");
            }
            this.levels.add(level);  // Adds the level to the building components
        }
    }

    /**
     * Adds a component to the building's list of levels.
     *
     * @param component the {@link BuildingComponent} to add
     */
    @Override
    public void addComponent(BuildingComponent component) {
        levels.add(component);
    }

    /**
     * Removes a component from the building's list of levels.
     *
     * @param component the {@link BuildingComponent} to remove
     */
    @Override
    public void removeComponent(BuildingComponent component) {
        levels.remove(component);
    }

    /**
     * Returns the list of components (levels) in the building.
     *
     * @return a list of {@link BuildingComponent} instances representing the levels
     */
    @Override
    public List<BuildingComponent> getComponents() {
        return levels;
    }

    /**
     * Calculates the total area of the building by summing the areas of all levels.
     *
     * @return the total area of the building
     */
    @Override
    public double calculateArea() {
        return levels.stream()
                .mapToDouble(BuildingComponent::calculateArea)
                .sum();
    }

    /**
     * Calculates the total heat demand of the building by summing the heat demands of all levels.
     *
     * @return the total heat demand of the building
     */
    @Override
    public double calculateHeat() {
        return levels.stream()
                .mapToDouble(BuildingComponent::calculateHeat)
                .sum();
    }

    /**
     * Returns a string representation of the building, including its ID, name, and all levels.
     *
     * @return a string describing the building
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Building ID: ").append(getId()).append("\n")
                .append("Name: ").append(getName()).append("\n")
                .append("Levels: \n");
        for (BuildingComponent level : levels) {
            sb.append("  ").append(level.toString()).append("\n");
        }
        return sb.toString();
    }
}
