package managers;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private Map<Integer, Task> tasks;
    private Map<Integer, Subtask> subtasks;
    private Map<Integer, Epic> epics;
    private int id = 0;

    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        tasks = new HashMap();
        subtasks = new HashMap();
        epics = new HashMap();
        this.historyManager = historyManager;
    }

    @Override
    public void createTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            return;
        }
        task.setId(generateNextId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            return;
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.addTaskHistoryList(task);
        return task;
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            return;
        }
        subtask.setId(generateNextId());
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addSubtask(subtask);
        updateEpic(getEpic(subtask.getEpicId()));
    }

    @Override
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
        updateEpic(getEpic(subtask.getEpicId()));
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.addTaskHistoryList(subtask);
        return subtask;
    }

    @Override
    public void removeSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        Epic epic = getEpic(subtask.getEpicId());
        epic.removeSubtask(subtask);
        updateEpic(epic);
        subtasks.remove(id);
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void createEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            return;
        }
        epic.setId(generateNextId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        epic.setStatus(computeEpicStatus(epic));
        epics.put(epic.getId(), epic);
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.addTaskHistoryList(epic);
        return epic;
    }

    @Override
    public List<Subtask> getEpicSubtasks(Epic epic) {
        return epic.getSubtasks();
    }

    @Override
    public void removeEpic(int id) {
        List<Subtask> subtasks = getEpic(id).getSubtasks();
        for (Subtask s : subtasks) {
            this.subtasks.remove(s.getId());
        }
        epics.remove(id);
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    private Status computeEpicStatus(Epic epic) {
        List<Subtask> allSubtasks = epic.getSubtasks();
        if (allSubtasks.isEmpty()) {
            return Status.NEW;
        }
        int newStatus = 0;
        int doneStatus = 0;

        for (Subtask subtask1 : allSubtasks) {
            if (subtask1.getStatus() == Status.NEW) {
                newStatus++;
            } else if (subtask1.getStatus() == Status.DONE) {
                doneStatus++;
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

