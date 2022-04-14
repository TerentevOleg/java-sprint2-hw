package managers;

import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;

import java.util.*;

//Бекграунда по сути нет. Есть только инженерное образование и опыт работы со scada системами на контроллерах siemens.
//Там было блочное программирование.

public class TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private int id = 0;

    public void createTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            return;
        }
        task.setId(generateNextId());
        tasks.put(task.getId(), task);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void createSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            return;
        }
        subtask.setId(generateNextId());
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addSubtask(subtask);
    }

    public void updateSubtask(Subtask subtask) {
        Subtask foundTask = subtasks.get(subtask.getId());
        if (!Objects.equals(subtask.getEpicId(), foundTask.getEpicId())) {
            Epic epicToRemoveSubtask = getEpic(foundTask.getEpicId());
            Epic epicToAddSubtask = getEpic(subtask.getEpicId());
            epicToRemoveSubtask.removeSubtask(foundTask);
            epicToAddSubtask.addSubtask(subtask);
            updateEpic(epicToRemoveSubtask);
            updateEpic(epicToAddSubtask);
        }
        subtasks.put(subtask.getId(), subtask);
        Epic epic = getEpic(subtask.getEpicId());
        updateEpic(epic);
    }

    public void removeSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        Epic epic = getEpic(subtask.getEpicId());
        epic.removeSubtask(subtask);
        updateEpic(epic);
        subtasks.remove(id);
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void createEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            return;
        }
        epic.setId(generateNextId());
        epics.put(epic.getId(), epic);
    }

    public void updateEpic(Epic epic) {
        epic.setStatus(computeEpicStatus(epic));
        epics.put(epic.getId(), epic);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public List<Subtask> getEpicSubtasks(Epic epic) {
        return epic.getSubtasks();
    }

    public void removeEpic(int id) {
        List<Subtask> subtasks = getEpic(id).getSubtasks();
        for (Subtask s : subtasks) {
            this.subtasks.remove(s.getId());
        }
        epics.remove(id);
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    private Status computeEpicStatus(Epic epic) { //Не могу придумать, как переделать этот метод, уже всю голову сломал.
        List<Subtask> allSubtasks = epic.getSubtasks();
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

    private int generateNextId() {
        return ++id;
    }
}