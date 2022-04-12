import managers.Manager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {
    public static void main(String[] args) {

        Manager taskManager = new Manager();

        int id = taskManager.hashCode();
        Task task1 = new Task("Переезд", "Перевести вещи в другую квартиру", ++id, Status.NEW);
        taskManager.createTask(task1);
        Task task2 = new Task("Сдать проект по java", "Сдать в срок (провалено)", ++id, Status.NEW);
        taskManager.createTask(task2);
        System.out.println(task2);

        Epic epic = new Epic("Тренировка в тренажерном зале", "Пн, ср, пт", ++id, Status.NEW);
        taskManager.createEpic(epic);
        Subtask subTask1 = new Subtask("Тренировка груди", "Пн", ++id, Status.NEW, epic);
        taskManager.createSubtask(subTask1);
        Subtask subTask2 = new Subtask("Тренировка ног", "Ср", ++id, Status.NEW, epic);
        taskManager.createSubtask(subTask2);
        Subtask subtask3 = new Subtask("Тренировка спины", "Пт", ++id, Status.NEW, epic);
        taskManager.createSubtask(subtask3);

        Epic epic2 = new Epic("Что-то сделать", "Для чего-то", ++id, Status.NEW);
        taskManager.createEpic(epic2);

        Task task2update = new Task("Обновление задачи", "Новое описание", ++id, Status.IN_PROGRESS);
        taskManager.updateTask(task2update);
        System.out.println(task2update);

        taskManager.removeTask(task1.getId());
        taskManager.removeEpic(epic.getId());
    }
}