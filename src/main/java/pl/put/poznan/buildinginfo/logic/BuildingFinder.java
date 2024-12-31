package pl.put.poznan.buildinginfo.logic;

import pl.put.poznan.buildinginfo.logic.entities.BuildingComponent;
import pl.put.poznan.buildinginfo.logic.entities.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuildingFinder {

    /**
     * Helper method to recursively find a component by name in the building hierarchy.
     *
     * @param component The root component to start the search
     * @param name      The name of the component to find
     * @return An {@link Optional} containing the component if found, otherwise empty
     */
    public static Optional<BuildingComponent> findComponentByName(BuildingComponent component, String name) {
        if (component.getName().equalsIgnoreCase(name)) {
            return Optional.of(component);
        }
        for (BuildingComponent child : component.getComponents()) {
            Optional<BuildingComponent> found = findComponentByName(child, name);
            if (found.isPresent()) {
                return found;
            }
        }
        return Optional.empty();
    }

    /**
     * Helper method to find rooms with heating per cubic meter exceeding a given threshold.
     *
     * @param component The root component to start the search
     * @param threshold The heating threshold per cubic meter
     * @return A list of rooms exceeding the threshold
     */
    public static List<Room> findRoomsExceedingHeatThreshold(BuildingComponent component, double threshold) {
        List<Room> roomsExceedingThreshold = new ArrayList<>();

        if (component instanceof Room) {
            Room room = (Room) component;
            double heatPerCube = room.getHeating() / room.getCube();
            if (heatPerCube > threshold) {
                roomsExceedingThreshold.add(room);
            }
        } else {
            for (BuildingComponent child : component.getComponents()) {
                roomsExceedingThreshold.addAll(findRoomsExceedingHeatThreshold(child, threshold));
            }
        }

        return roomsExceedingThreshold;
    }
}
