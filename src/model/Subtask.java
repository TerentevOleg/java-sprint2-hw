package model;

import java.util.Objects;

public class Subtask extends Task {
    private int epicID;
    private int id;
    private String name;
    private String description;
    private Status status;

    public Subtask(int epicID, int id, String name, String description, Status status) {
        super(id, name, description, status);
        this.epicID = epicID;
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Subtask(int epicID, String name, String description, Status status) {
        super(epicID, name, description, status);
        this.epicID = epicID;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(Integer epicID) {
        this.epicID = epicID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return id == subtask.id && Objects.equals(name, subtask.name) && Objects.equals(description, subtask.description)
                && status == subtask.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "\n\t id= " + getId() +
                "\n\t name= " + getName() +
                "\n\t description= " + getDescription() +
                "\n\t status= " + getStatus() +
                "\n\t}";
    }
}
