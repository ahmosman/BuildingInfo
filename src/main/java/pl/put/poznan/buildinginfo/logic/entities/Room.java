package pl.put.poznan.buildinginfo.logic.entities;

public class Room extends BuildingComponent {
    private double area;
    private double cube;
    private float heating;
    private float light;

    public Room(String id, String name, double area, double cube, float heating, float light) {
        super(id, name);
        this.area = area;
        this.cube = cube;
        this.heating = heating;
        this.light = light;
    }

    @Override
    public double calculateArea() {
        return area;
    }

    @Override
    public double calculateHeat() {
        return heating;
    }


    public double getArea() {
        return area;
    }

    public double getCube() {
        return cube;
    }

    public float getHeating() {
        return heating;
    }

    public float getLight() {
        return light;
    }




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
