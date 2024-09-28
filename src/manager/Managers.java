package manager;

import inmemory.InMemoryHistoryManager;
import inmemory.InMemoryTaskManager;

public class Managers <T extends TaskManager> {
    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
