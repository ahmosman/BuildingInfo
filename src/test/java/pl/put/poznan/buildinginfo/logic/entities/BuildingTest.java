package pl.put.poznan.buildinginfo.logic.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the Building class, verifying the functionality
 * of methods that calculate the aggregate properties of a building.
 */
class BuildingTest {

    /**
     * Tests the calculateArea method to ensure it correctly sums up
     * the areas of all rooms across all levels in the building.
     */
    @Test
    void calculateArea() {
        // Arrange
        Room room1 = new Room("room1", "Conference Room", 50.0, 150.0, 20.5f, 300.0f);
        Room room2 = new Room("room2", "Office 101", 30.0, 90.0, 15.0f, 200.0f);
        Level level1 = new Level("level1", "Ground Floor");
        level1.addComponent(room1);
        level1.addComponent(room2);

        Room room3 = new Room("room3", "Office 201", 25.0, 75.0, 10.0f, 150.0f);
        Level level2 = new Level("level2", "First Floor");
        level2.addComponent(room3);

        Building building = new Building("building1", "Main Office");
        building.addComponent(level1);
        building.addComponent(level2);

        // Act
        double result = building.calculateArea();

        // Assert
        assertEquals(105.0, result, "The total calculated area should match the expected value.");
    }

    /**
     * Tests the calculateHeat method to ensure it correctly sums up
     * the heating demands of all rooms across all levels in the building.
     */
    @Test
    void calculateHeat() {
        // Arrange
        Room room1 = new Room("room1", "Conference Room", 50.0, 150.0, 20.5f, 300.0f);
        Room room2 = new Room("room2", "Office 101", 30.0, 90.0, 15.0f, 200.0f);
        Level level1 = new Level("level1", "Ground Floor");
        level1.addComponent(room1);
        level1.addComponent(room2);

        Room room3 = new Room("room3", "Office 201", 25.0, 75.0, 10.0f, 150.0f);
        Level level2 = new Level("level2", "First Floor");
        level2.addComponent(room3);

        Building building = new Building("building1", "Main Office");
        building.addComponent(level1);
        building.addComponent(level2);

        // Act
        double result = building.calculateHeat();

        // Assert
        assertEquals(45.5, result, "The total calculated heat should match the expected value.");
    }

    /**
     * Tests the calculateCube method to ensure it correctly sums up
     * the volumes of all rooms across all levels in the building.
     */
    @Test
    void calculateCube() {
        // Arrange
        Room room1 = new Room("room1", "Conference Room", 50.0, 150.0, 20.5f, 300.0f);
        Room room2 = new Room("room2", "Office 101", 30.0, 90.0, 15.0f, 200.0f);
        Level level1 = new Level("level1", "Ground Floor");
        level1.addComponent(room1);
        level1.addComponent(room2);

        Room room3 = new Room("room3", "Office 201", 25.0, 75.0, 10.0f, 150.0f);
        Level level2 = new Level("level2", "First Floor");
        level2.addComponent(room3);

        Building building = new Building("building1", "Main Office");
        building.addComponent(level1);
        building.addComponent(level2);

        // Act
        double result = building.calculateCube();

        // Assert
        assertEquals(315.0, result, "The total calculated cube should match the expected value.");
    }

    /**
     * Tests the calculateLight method to ensure it correctly sums up
     * the lighting demands of all rooms across all levels in the building.
     */
    @Test
    void calculateLight() {
        // Arrange
        Room room1 = new Room("room1", "Conference Room", 50.0, 150.0, 20.5f, 300.0f);
        Room room2 = new Room("room2", "Office 101", 30.0, 90.0, 15.0f, 200.0f);
        Level level1 = new Level("level1", "Ground Floor");
        level1.addComponent(room1);
        level1.addComponent(room2);

        Room room3 = new Room("room3", "Office 201", 25.0, 75.0, 10.0f, 150.0f);
        Level level2 = new Level("level2", "First Floor");
        level2.addComponent(room3);

        Building building = new Building("building1", "Main Office");
        building.addComponent(level1);
        building.addComponent(level2);

        // Act
        double result = building.calculateLight();

        // Assert
        assertEquals(650.0, result, "The total calculated light should match the expected value.");
    }
}
