import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import manager.TaskManager;
import inmemory.InMemoryTaskManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new InMemoryTaskManager();

        Task firstTask = new Task("firstTask", "description firstTask", Status.NEW);
        Task secondTask = new Task("secondTask", "description second task", Status.NEW);
        Task updateTask = new Task("updateTask", "description updateTask", Status.NEW);

        Epic firstEpic = new Epic("firstEpic", "descriptionFirstEpic");
        SubTask firstSubTaskFirstEpic = new SubTask("firstSubTaskFirstEpic"
                , "descriptionFirstSubTaskFirstEpic"
                , Status.NEW
                , firstEpic);
        SubTask secondSubTaskFirstEpic = new SubTask("secondSubTaskFirstEpic"
                , "descriptionSecondSubTaskFirstEpic"
                , Status.IN_PROGRESS
                , firstEpic);
        SubTask thirdSubTaskFirstEpic = new SubTask("thirdSubTaskFirstEpic"
                , "descriptionThirdSubTaskFirstEpic"
                , Status.NEW
                , firstEpic);

        Epic secondEpic = new Epic("secondEpic", "descriptionSecondEpic");
        SubTask firstSubTaskSecondEpic = new SubTask("firstSubTaskSecondEpic"
                , "descriptionFirstSubTaskSecondEpic"
                , Status.IN_PROGRESS
                , secondEpic);


        taskManager.createTask(firstTask);
        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);

        List<Task> tasks = taskManager.getHistory();
        System.out.println(tasks);

    }
}