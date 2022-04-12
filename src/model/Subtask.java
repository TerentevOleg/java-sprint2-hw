package model;

public class Subtask extends Task {
    private Epic epicName;

    public Subtask(String name, String description, int id, Status status, Epic epicName) {
        super(name, description, id, status);
        this.epicName = epicName;
    }

    public Epic getEpicName() {
        return epicName;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "\n\t name= " + getName() +
                "\n\t description= " + getDescription() +
                "\n\t id= " + getId() +
                "\n\t status= " + getStatus() +
                "\n\t}";
    }
}
