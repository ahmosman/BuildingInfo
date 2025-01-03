package pl.put.poznan.buildinginfo.logic.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Room entity, containing methods to test
 * various calculations related to the room's properties.
 */
class RoomTest {

    /**
     * Test method to calculate the area of a room.
     *
     * <p>This test checks whether the area is calculated correctly for a given room.</p>
     *
     * @see Room#calculateArea()
     */
    @Test
    void calculateArea() {
        // Arrange
        Room room = new Room("room1", "Conference Room", 50.0, 150.0, 20.5f, 300.0f);

        // Act
        double result = room.calculateArea();

        // Assert
        assertEquals(50.0, result, "The calculated area should match the expected value.");
    }

    /**
     * Test method to calculate the heat of a room.
     *
     * <p>This test checks whether the heat requirement is calculated correctly for a given room.</p>
     *
     * @see Room#calculateHeat()
     */
    @Test
    void calculateHeat() {
        // Arrange
        Room room = new Room("room2", "Office 101", 30.0, 90.0, 15.0f, 200.0f);

        // Act
        double result = room.calculateHeat();

        // Assert
        assertEquals(15.0, result, "The calculated heat should match the expected value.");
    }

    /**
     * Test method to calculate the volume (cube) of a room.
     *
     * <p>This test checks whether the room's volume is calculated correctly based on the room's dimensions.</p>
     *
     * @see Room#calculateCube()
     */
    @Test
    void calculateCube() {
        // Arrange
        Room room = new Room("room3", "Office 201", 25.0, 75.0, 10.0f, 150.0f);

        // Act
        double result = room.calculateCube();

        // Assert
        assertEquals(75.0, result, "The calculated cube should match the expected value.");
    }

    /**
     * Test method to calculate the light requirement for a room.
     *
     * <p>This test checks whether the light requirement is calculated correctly for a given room.</p>
     *
     * @see Room#calculateLight()
     */
    @Test
    void calculateLight() {
        // Arrange
        Room room = new Room("room1", "Conference Room", 50.0, 150.0, 20.5f, 300.0f);

        // Act
        double result = room.calculateLight();

        // Assert
        assertEquals(300.0, result, "The calculated light should match the expected value.");
    }
}
