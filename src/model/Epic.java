package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks = new ArrayList<>();

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "\n\t id= " + getId() +
                "\n\t name= " + getName() +
                "\n\t description= " + getDescription() +
                "\n\t status= " + getStatus() +
                "\n\t subtasks= " + subtasks +
                "\n\t}";
    }
}

