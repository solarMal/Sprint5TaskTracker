package manager;

import httpclient.HttpTaskManager;
import infile.FileBackedTasksManager;
import inmemory.InMemoryHistoryManager;

import java.net.MalformedURLException;
import java.net.URL;

public class Managers <T extends TaskManager> {
    public static FileBackedTasksManager getFileBackedTaskManager() {
        return new FileBackedTasksManager();
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        try {
            return new HttpTaskManager(new URL("http://localhost:8080"));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Неверный адрес KVServer", e);
        }
    }
}
