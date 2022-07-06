package managers;

import model.Task;

import java.util.List;

public interface HistoryManager {

    List<Task> getTaskHistoryList();

    void addTaskHistoryList(Task task);

    void removeTaskHistoryList(int id);
}
