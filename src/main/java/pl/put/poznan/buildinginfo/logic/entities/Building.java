package pl.put.poznan.buildinginfo.logic.entities;

import java.util.List;

public class Building {
    private String id;
    private String name;
    private List<Level> levels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

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