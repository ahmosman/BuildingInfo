package pl.put.poznan.buildinginfo.logic.entities;

/**
 * Represents a room within a building.
 * This class extends {@link BuildingComponent} and provides properties
 * specific to individual rooms, such as area, volume, heating, and lighting.
 */
public class Room extends BuildingComponent {
    private double area;
    private double cube;
    private float heating;
    private float light;

    /**
     * Constructs a Room with the given parameters.
     *
     * @param id      the unique identifier of the room
     * @param name    the name of the room
     * @param area    the area of the room in square meters
     * @param cube    the volume of the room in cubic meters
     * @param heating the heating demand of the room
     * @param light   the lighting level of the room in lumens
     */
    public Room(String id, String name, double area, double cube, float heating, float light) {
        super(id, name);
        this.area = area;
        this.cube = cube;
        this.heating = heating;
        this.light = light;
    }

    /**
     * Calculates and returns the area of the room.
     *
     * @return the area of the room in square meters
     */
    @Override
    public double calculateArea() {
        return area;
    }

    /**
     * Calculates and returns the heating demand of the room.
     *
     * @return the heating demand of the room
     */
    @Override
    public double calculateHeat() {
        return heating;
    }

    /**
     * Gets the area of the room.
     *
     * @return the area of the room in square meters
     */
    public double getArea() {
        return area;
    }

    /**
     * Gets the volume of the room.
     *
     * @return the volume of the room in cubic meters
     */
    public double getCube() {
        return cube;
    }

    /**
     * Gets the heating demand of the room.
     *
     * @return the heating demand of the room
     */
    public float getHeating() {
        return heating;
    }

    /**
     * Gets the lighting level of the room.
     *
     * @return the lighting level of the room in lumens
     */
    public float getLight() {
        return light;
    }

    /**
     * Returns a string representation of the room, including its ID, name, area, volume, heating, and lighting.
     *
     * @return a string describing the room
     */
    @Override
    public String toString() {
        return "Room ID: " + getId() + "\n" +
                "Name: " + getName() + "\n" +
                "Area: " + area + " sqm\n" +
                "Cube: " + cube + " cubic meters\n" +
                "Heating: " + heating + " degrees\n" +
                "Light: " + light + " lumens";
    }
}
