package pl.put.poznan.buildinginfo.app;

import pl.put.poznan.buildinginfo.logic.BuildingFinder;
import pl.put.poznan.buildinginfo.logic.BuildingParser;
import pl.put.poznan.buildinginfo.logic.entities.Building;
import pl.put.poznan.buildinginfo.logic.entities.BuildingComponent;
import pl.put.poznan.buildinginfo.logic.entities.Room;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BuildingInfoCMD {
    private static Building building;

    public static void main(String[] args) {
        try {
            InputStream inputStream = BuildingInfoCMD.class.getClassLoader().getResourceAsStream("building-cmd.json");
            if (inputStream == null) {
                throw new IOException("File not found: building-cmd.json");
            }
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            building = BuildingParser.parseJson(content);
            showMenu();
        } catch (IOException e) {
            System.err.println("Error reading the JSON file: " + e.getMessage());
        }
    }

    private static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Show building info");
            System.out.println("2. Calculate total area");
            System.out.println("3. Calculate total heating");
            System.out.println("4. Find rooms with high heating per cubic meter");
            System.out.println("5. Exit");

            int choice = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    choice = scanner.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number for the action.");
                    scanner.next(); // consume invalid input
                }
            }
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println(building);
                    break;
                case 2:
                    System.out.print("Enter component name (or press Enter for total building area): ");
                    String areaName = scanner.nextLine();
                    calculateAndPrintArea(areaName);
                    break;
                case 3:
                    System.out.print("Enter component name (or press Enter for total building heating): ");
                    String heatName = scanner.nextLine();
                    calculateAndPrintHeat(heatName);
                    break;
                case 4:
                    System.out.print("Enter heating threshold: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a valid number for the threshold.");
                        scanner.next(); // consume invalid input
                    }
                    double threshold = scanner.nextDouble();
                    List<Room> rooms = BuildingFinder.findRoomsExceedingHeatThreshold(building, threshold);
                    rooms.forEach(room -> System.out.println(room.getName() + " - Heat per cube: " + (room.getHeating() / room.getCube())));
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void calculateAndPrintArea(String name) {
        double totalArea;
        if (name != null && !name.isEmpty()) {
            Optional<BuildingComponent> component = BuildingFinder.findComponentByName(building, name);
            if (component.isPresent()) {
                totalArea = component.get().calculateArea();
            } else {
                System.out.println("Component with given name not found");
                return;
            }
        } else {
            totalArea = building.calculateArea();
        }
        System.out.println("Total area: " + Math.round(totalArea * 100.0) / 100.0);
    }

    private static void calculateAndPrintHeat(String name) {
        double totalHeat;
        if (name != null && !name.isEmpty()) {
            Optional<BuildingComponent> component = BuildingFinder.findComponentByName(building, name);
            if (component.isPresent()) {
                totalHeat = component.get().calculateHeat();
            } else {
                System.out.println("Component with given name not found");
                return;
            }
        } else {
            totalHeat = building.calculateHeat();
        }
        System.out.println("Total heating: " + Math.round(totalHeat * 100.0) / 100.0);
    }
}
