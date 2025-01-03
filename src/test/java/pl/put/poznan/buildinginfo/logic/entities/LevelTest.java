package pl.put.poznan.buildinginfo.logic.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Level class, ensuring that its methods for calculating area,
 * heat demand, cube, and light intensity work correctly.
 */
class LevelTest {

    /**
     * Tests the calculateArea method to ensure it correctly calculates
     * the total area of all components (rooms) in a level.
     */
    @Test
    void calculateArea() {
        // Arrange
        Room room1 = new Room("room1", "Conference Room", 50.0, 150.0, 20.5f, 300.0f);
        Room room2 = new Room("room2", "Office 101", 30.0, 90.0, 15.0f, 200.0f);
        Level level = new Level("level1", "Ground Floor");
        level.addComponent(room1);
        level.addComponent(room2);

        // Act
        double result = level.calculateArea();

        // Assert
        assertEquals(80.0, result, "The total calculated area should match the expected value.");
    }

    /**
     * Tests the calculateHeat method to ensure it correctly calculates
     * the total heat demand of all components (rooms) in a level.
     */
    @Test
    void calculateHeat() {
        // Arrange
        Room room1 = new Room("room1", "Conference Room", 50.0, 150.0, 20.5f, 300.0f);
        Room room2 = new Room("room2", "Office 101", 30.0, 90.0, 15.0f, 200.0f);
        Level level = new Level("level1", "Ground Floor");
        level.addComponent(room1);
        level.addComponent(room2);

        // Act
        double result = level.calculateHeat();

        // Assert
        assertEquals(35.5, result, "The total calculated heat should match the expected value.");
    }

    /**
     * Tests the calculateCube method to ensure it correctly calculates
     * the total volume (cube) of all components (rooms) in a level.
     */
    @Test
    void calculateCube() {
        // Arrange
        Room room1 = new Room("room1", "Conference Room", 50.0, 150.0, 20.5f, 300.0f);
        Room room2 = new Room("room2", "Office 101", 30.0, 90.0, 15.0f, 200.0f);
        Level level = new Level("level1", "Ground Floor");
        level.addComponent(room1);
        level.addComponent(room2);

        // Act
        double result = level.calculateCube();

        // Assert
        assertEquals(240.0, result, "The total calculated cube should match the expected value.");
    }

    /**
     * Tests the calculateLight method to ensure it correctly calculates
     * the total light intensity of all components (rooms) in a level.
     */
    @Test
    void calculateLight() {
        // Arrange
        Room room1 = new Room("room1", "Conference Room", 50.0, 150.0, 20.5f, 300.0f);
        Room room2 = new Room("room2", "Office 101", 30.0, 90.0, 15.0f, 200.0f);
        Level level = new Level("level1", "Ground Floor");
        level.addComponent(room1);
        level.addComponent(room2);

        // Act
        double result = level.calculateLight();

        // Assert
        assertEquals(500.0, result, "The total calculated light should match the expected value.");
    }
}
