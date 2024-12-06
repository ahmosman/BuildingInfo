package pl.put.poznan.buildinginfo.logic.entities;

import java.util.List;

/**
 * Reprezentuje budynek, który składa się z jednego lub więcej poziomów.
 * Każdy budynek ma unikalne ID, nazwę oraz listę poziomów (pięter).
 *
 * @author Ahmen Osman
 * @version 1.0
 * @since 2024-11-25
 */
public class Building {
    private String id;
    private String name;
    private List<Level> levels;

    /**
     * Pobiera identyfikator budynku.
     *
     * @return identyfikator budynku w formie ciągu znaków
     */
    public String getId() {
        return id;
    }

    /**
     * Ustawia identyfikator budynku.
     *
     * @param id identyfikator budynku w formie ciągu znaków
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Pobiera nazwę budynku.
     *
     * @return nazwa budynku w formie ciągu znaków
     */
    public String getName() {
        return name;
    }

    /**
     * Ustawia nazwę budynku.
     *
     * @param name nazwa budynku w formie ciągu znaków
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Pobiera listę poziomów (pięter) w budynku.
     *
     * @return lista poziomów w budynku
     */
    public List<Level> getLevels() {
        return levels;
    }

    /**
     * Ustawia listę poziomów (pięter) w budynku.
     *
     * @param levels lista poziomów w budynku
     */
    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    /**
     * Zwraca reprezentację tekstową budynku, w tym jego identyfikator, nazwę oraz szczegóły poziomów.
     *
     * @return reprezentacja tekstowa budynku
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Building ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Levels:\n");
        for (Level level : levels) {
            sb.append(level.toString()).append("\n");
        }
        return sb.toString();
    }
}
