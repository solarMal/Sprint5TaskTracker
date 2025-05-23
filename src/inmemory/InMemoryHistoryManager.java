package inmemory;

import manager.HistoryManager;
import tasks.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList nodes = new CustomLinkedList();

    @Override
    public void add(Task task) {
        nodes.linkLast(task);
    }

    @Override
    public void remove(int id) {
        nodes.remote(id);
    }

    @Override
    public List<Task> getHistory() {
        return nodes.getTasks();
    }
}
