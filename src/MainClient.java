import manager.Managers;
import manager.TaskManager;
import status.Status;
import tasks.Epic;
import tasks.Task;

import java.io.IOException;

public class MainClient {

    public static void main(String[] args) throws IOException {
        new KVServer().start();

        TaskManager manager = Managers.getDefault();
        Task task = new Task("Test", "Описание", Status.NEW);
        manager.createTask(task);

        Epic epic = new Epic("Epic", "Description");

        System.out.println("Создана задача: " + manager.getTaskById(task.getId()));
        System.out.println(manager.getEpicById(epic.getId()));
    }
}
