package manager;

import infile.FileBackedTasksManager;
import inmemory.InMemoryHistoryManager;

public class Managers <T extends TaskManager> {
    public static FileBackedTasksManager getFileBackedTaskManager() {
        return new FileBackedTasksManager();
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
