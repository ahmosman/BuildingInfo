package pl.put.poznan.buildinginfo.logic.entities;

import java.util.ArrayList;
import java.util.List;

public class Building extends BuildingComponent {
    private List<BuildingComponent> levels = new ArrayList<>();

    public Building(String id, String name) {
        super(id, name);
    }

    public void setLevels(List<Level> levels) {
        for (Level level : levels) {
            levels.add(level);  // Dodajemy poziom do komponentów budynku
        }
    }


    @Override
    public void addComponent(BuildingComponent component) {
        levels.add(component);
    }

    @Override
    public void removeComponent(BuildingComponent component) {
        levels.remove(component);
    }

    @Override
    public List<BuildingComponent> getComponents() {
        return levels;
    }

    @Override
    public double calculateArea() {
        return levels.stream()
                .mapToDouble(BuildingComponent::calculateArea)
                .sum();
    }

    @Override
    public double calculateHeat() {
        return levels.stream()
                .mapToDouble(BuildingComponent::calculateHeat)
                .sum();
    }


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
