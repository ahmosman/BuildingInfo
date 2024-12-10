package pl.put.poznan.buildinginfo.logic.entities;

import java.util.*;

public abstract class BuildingComponent {
    private String id;
    private String name;

    public BuildingComponent(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Obliczanie powierzchni (do nadpisania w podklasach)
    public abstract double calculateArea();

    // Obliczanie ciepla (do nadpisania w podklasach)
    public abstract double calculateHeat();

    // Dodawanie i usuwanie komponentów (tylko dla kompozytów)
    public void addComponent(BuildingComponent component) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    public void removeComponent(BuildingComponent component) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    public List<BuildingComponent> getComponents() {
        return Collections.emptyList();
    }
}