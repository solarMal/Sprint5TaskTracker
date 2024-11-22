package inmemory;

import manager.HistoryManager;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> historyTask = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (historyTask.size() == 10) {
            historyTask.remove(0);
            historyTask.add(task);
        } else {
            historyTask.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        if (historyTask.isEmpty()) {
            System.out.println("Нет просмотренных задач");
        }
        return historyTask;
    }

    @Override
    public void remove(Task task) {
        historyTask.remove(task);
    }
}
