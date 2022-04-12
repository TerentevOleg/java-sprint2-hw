package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description, int id, Status status) {
        super(name, description, id, status);
    }

    public ArrayList<Subtask> getSubtasks() {
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
                "\n\t name= " + getName() +
                "\n\t description= " + getDescription() +
                "\n\t id= " + getId() +
                "\n\t status= " + getStatus() +
                "\n\t subtasks= " + subtasks +
                "\n\t}";
    }
}

