package model;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int epicId, int id, String name, String description, Status status) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public Subtask(int epicId, String name, String description, Status status) {
        super(epicId, name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subtask)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return getEpicId() == ((Subtask) o).getEpicId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEpicId());
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
