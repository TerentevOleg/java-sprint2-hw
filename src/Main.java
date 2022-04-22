import managers.TaskManager;
import managers.Manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = Manager.getDefault(Manager.getDefaultHistory());

        Task task1 = new Task("Перевести вещи в другую квартиру", "Переезд", Status.NEW);
        taskManager.createTask(task1);
        Task task2 = new Task("Сдать в срок (провалено)", "Сдать проект по java", Status.NEW);
        taskManager.createTask(task2);

        Epic epic = new Epic("Пн, ср, пт", "Тренировка в тренажерном зале", Status.NEW);
        taskManager.createEpic(epic);
        Subtask subTask1 = new Subtask(epic.getId(),"Пн", "Тренировка груди", Status.NEW);
        taskManager.createSubtask(subTask1);
        Subtask subTask2 = new Subtask(epic.getId(),"Ср", "Тренировка ног", Status.NEW);
        taskManager.createSubtask(subTask2);
        Subtask subtask3 = new Subtask(epic.getId(),"Пт", "Тренировка спины", Status.NEW);
        taskManager.createSubtask(subtask3);
        System.out.println(epic);

        Epic epic2 = new Epic("Для чего-то", "Что-то сделать", Status.NEW);
        taskManager.createEpic(epic2);

        Task task2update = new Task(1,"Новое описание", "Обновление задачи", Status.IN_PROGRESS);
        taskManager.updateTask(task2update);

        System.out.println();
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.getEpic(3);
        taskManager.getSubtask(4);
        taskManager.getSubtask(5);
        for (Task task : taskManager.history()){
            System.out.println(task);
        }

        taskManager.removeTask(task1.getId());
        taskManager.removeEpic(epic.getId());
    }
}