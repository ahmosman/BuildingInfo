package pl.put.poznan.buildinginfo.logic.entities;

/**
 * Reprezentuje pojedyncze pomieszczenie w budynku.
 * Każde pomieszczenie posiada unikalne ID, nazwę, powierzchnię, kubaturę, a także informacje o ogrzewaniu i oświetleniu.
 *
 * @author Ahmen Osman
 * @version 1.0
 * @since 2024-11-25
 */
public class Room {
    private String id;
    private String name;
    private double area;
    private double cube;
    private float heating;
    private float light;

    /**
     * Pobiera identyfikator pomieszczenia.
     *
     * @return identyfikator pomieszczenia w formie ciągu znaków
     */
    public String getId() {
        return id;
    }

    /**
     * Ustawia identyfikator pomieszczenia.
     *
     * @param id identyfikator pomieszczenia w formie ciągu znaków
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Pobiera nazwę pomieszczenia.
     *
     * @return nazwa pomieszczenia w formie ciągu znaków
     */
    public String getName() {
        return name;
    }

    /**
     * Ustawia nazwę pomieszczenia.
     *
     * @param name nazwa pomieszczenia w formie ciągu znaków
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Pobiera powierzchnię pomieszczenia.
     *
     * @return powierzchnia pomieszczenia w metrach kwadratowych
     */
    public double getArea() {
        return area;
    }

    /**
     * Ustawia powierzchnię pomieszczenia.
     *
     * @param area powierzchnia pomieszczenia w metrach kwadratowych
     */
    public void setArea(double area) {
        this.area = area;
    }

    /**
     * Pobiera kubaturę pomieszczenia.
     *
     * @return kubatura pomieszczenia w metrach sześciennych
     */
    public double getCube() {
        return cube;
    }

    /**
     * Ustawia kubaturę pomieszczenia.
     *
     * @param cube kubatura pomieszczenia w metrach sześciennych
     */
    public void setCube(double cube) {
        this.cube = cube;
    }

    /**
     * Pobiera temperaturę ogrzewania w pomieszczeniu.
     *
     * @return temperatura ogrzewania w stopniach Celsjusza
     */
    public float getHeating() {
        return heating;
    }

    /**
     * Ustawia temperaturę ogrzewania w pomieszczeniu.
     *
     * @param heating temperatura ogrzewania w stopniach Celsjusza
     */
    public void setHeating(float heating) {
        this.heating = heating;
    }

    /**
     * Pobiera moc oświetlenia w pomieszczeniu.
     *
     * @return moc oświetlenia w lumenach
     */
    public float getLight() {
        return light;
    }

    /**
     * Ustawia moc oświetlenia w pomieszczeniu.
     *
     * @param light moc oświetlenia w lumenach
     */
    public void setLight(float light) {
        this.light = light;
    }

    /**
     * Zwraca reprezentację tekstową pomieszczenia, w tym jego identyfikator, nazwę, powierzchnię, kubaturę, ogrzewanie i oświetlenie.
     *
     * @return reprezentacja tekstowa pomieszczenia
     */
    @Override
    public String toString() {
        return "    Room ID: " + id + "\n" +
                "    Name: " + name + "\n" +
                "    Area: " + area + " sqm\n" +
                "    Cube: " + cube + " cubic meters\n" +
                "    Heating: " + heating + " degrees\n" +
                "    Light: " + light + " lumens";
    }
}
