package managers;

import model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    public static List<Task> historyManager = new LinkedList<>();

    @Override
    public List<Task> getTaskHistoryList() {
        return historyManager;
    }

    @Override
    public void addTaskHistoryList(Task task) {
        historyManager.add(task);
        if (historyManager.size() > 10){
            historyManager.remove(0);
        }
    }
}
