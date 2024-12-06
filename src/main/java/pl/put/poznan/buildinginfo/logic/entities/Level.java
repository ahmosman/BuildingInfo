package pl.put.poznan.buildinginfo.logic.entities;

import java.util.List;

/**
 * Reprezentuje poziom (piętro) w budynku.
 * Każdy poziom ma unikalne ID, nazwę oraz listę pomieszczeń znajdujących się na tym poziomie.
 *
 * @author Ahmen Osman
 * @version 1.0
 * @since 2024-11-25
 */
public class Level {
    private String id;
    private String name;
    private List<Room> rooms;

    /**
     * Pobiera identyfikator poziomu.
     *
     * @return identyfikator poziomu w formie ciągu znaków
     */
    public String getId() {
        return id;
    }

    /**
     * Ustawia identyfikator poziomu.
     *
     * @param id identyfikator poziomu w formie ciągu znaków
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Pobiera nazwę poziomu.
     *
     * @return nazwa poziomu w formie ciągu znaków
     */
    public String getName() {
        return name;
    }

    /**
     * Ustawia nazwę poziomu.
     *
     * @param name nazwa poziomu w formie ciągu znaków
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Pobiera listę pomieszczeń znajdujących się na tym poziomie.
     *
     * @return lista pomieszczeń na poziomie
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Ustawia listę pomieszczeń znajdujących się na tym poziomie.
     *
     * @param rooms lista pomieszczeń na poziomie
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Zwraca reprezentację tekstową poziomu, w tym jego identyfikator, nazwę oraz szczegóły pomieszczeń.
     *
     * @return reprezentacja tekstowa poziomu
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  Level ID: ").append(id).append("\n");
        sb.append("  Name: ").append(name).append("\n");
        sb.append("  Rooms:\n");
        for (Room room : rooms) {
            sb.append(room.toString()).append("\n");
        }
        return sb.toString();
    }
}
