package managers;

import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Manager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();

    public void createTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            return;
        }
        this.tasks.put(task.getId(), task);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Task removeTask(int id) {
        return tasks.remove(id);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public Subtask createSubtask(Subtask subtask) {
        if (subTasks.containsKey(subtask.getId())) {
            return null;
        }
        subTasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicName().getId()).addSubtask(subtask);
        return subtask;
    }

    public Subtask updateSubtask(Subtask subtask) {
        Subtask foundTask = subTasks.get(subtask.getId());
        if (!Objects.equals(subtask.getEpicName(), foundTask.getEpicName())) {
            Epic epicToRemoveSubtask = getEpic(foundTask.getEpicName().getId());
            Epic epicToAddSubtask = getEpic(subtask.getEpicName().getId());
            epicToRemoveSubtask.removeSubtask(foundTask);
            epicToAddSubtask.addSubtask(subtask);
            updateEpic(epicToRemoveSubtask);
            updateEpic(epicToAddSubtask);
        }
        subTasks.put(subtask.getId(), subtask);
        Epic epic = getEpic(subtask.getEpicName().getId());
        updateEpic(epic);
        return subtask;
    }

    public Subtask getSubtask(int id) {
        return subTasks.get(id);
    }

    public Subtask removeSubtask(int id) {
        Subtask subtask = getSubtask(id);
        Epic epic = getEpic(subtask.getEpicName().getId());
        epic.removeSubtask(subtask);
        updateEpic(epic);
        return subTasks.remove(id);
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subTasks.values());
    }

    public Epic createEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            return null;
        }
        return epics.put(epic.getId(), epic);
    }

    public Epic updateEpic(Epic epic) {
        epic.setStatus(epicStatus(epic));
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        return epic.getSubtasks();
    }

    public void removeEpic(int id) {
        ArrayList<Subtask> subtasks = getEpic(id).getSubtasks();
        for (Subtask s : subtasks) {
            subTasks.remove(s.getId());
        }
        epics.remove(id);
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    private Status epicStatus(Epic epic) {
        ArrayList<Subtask> allSubtasks = epic.getSubtasks();
        if (allSubtasks.isEmpty()) {
            return Status.NEW;
        }
        int newStatus = 0;
        int doneStatus = 0;

        for (Subtask Subtask1 : allSubtasks) {
            switch (Subtask1.getStatus()) {
                case NEW:
                    newStatus++;
                    break;
                case DONE:
                    doneStatus++;
                    break;
            }
        }
        if (newStatus == allSubtasks.size()) {
            return Status.NEW;
        }
        if (doneStatus == allSubtasks.size()) {
            return Status.DONE;
        }
        return Status.IN_PROGRESS;
    }
}