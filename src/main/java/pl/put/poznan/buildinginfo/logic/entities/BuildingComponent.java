package pl.put.poznan.buildinginfo.logic.entities;

import java.util.*;

/**
 * Represents an abstract component of a building in a composite structure.
 * This class provides a base for both individual building elements (e.g., rooms)
 * and composite structures (e.g., buildings or levels).
 */
public abstract class BuildingComponent {
    private String id;
    private String name;

    /**
     * Constructs a building component with the specified ID and name.
     *
     * @param id   the unique identifier of the component
     * @param name the name of the component
     */
    public BuildingComponent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the unique identifier of the building component.
     *
     * @return the ID of the component
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the building component.
     *
     * @return the name of the component
     */
    public String getName() {
        return name;
    }

    /**
     * Calculates the area of the building component.
     * This method must be implemented in subclasses.
     *
     * @return the area of the component
     */
    public abstract double calculateArea();

    /**
     * Calculates the heat demand of the building component.
     * This method must be implemented in subclasses.
     *
     * @return the heat demand of the component
     */
    public abstract double calculateHeat();


    public abstract double calculateCube();

    /**
     * Adds a subcomponent to this building component.
     * By default, this operation is unsupported and will throw an exception.
     * Subclasses representing composite structures should override this method.
     *
     * @param component the {@link BuildingComponent} to add
     * @throws UnsupportedOperationException if the operation is not supported
     */
    public void addComponent(BuildingComponent component) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    /**
     * Removes a subcomponent from this building component.
     * By default, this operation is unsupported and will throw an exception.
     * Subclasses representing composite structures should override this method.
     *
     * @param component the {@link BuildingComponent} to remove
     * @throws UnsupportedOperationException if the operation is not supported
     */
    public void removeComponent(BuildingComponent component) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    /**
     * Retrieves the list of subcomponents of this building component.
     * By default, this returns an empty list. Subclasses representing composite
     * structures should override this method to return their subcomponents.
     *
     * @return a list of {@link BuildingComponent} instances (empty by default)
     */
    public List<BuildingComponent> getComponents() {
        return Collections.emptyList();
    }
}
