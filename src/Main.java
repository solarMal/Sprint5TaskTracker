import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import manager.TaskManager;
import inmemory.InMemoryTaskManager;

import java.util.ArrayList;
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
                , Status.DONE
                , firstEpic);
        SubTask secondSubTaskFirstEpic = new SubTask("secondSubTaskFirstEpic"
                , "descriptionSecondSubTaskFirstEpic"
                , Status.DONE
                , firstEpic);


        Epic secondEpic = new Epic("secondEpic", "descriptionSecondEpic");
        SubTask firstSubTaskSecondEpic = new SubTask("firstSubTaskSecondEpic"
                , "descriptionFirstSubTaskSecondEpic"
                , Status.IN_PROGRESS
                , secondEpic);

        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);
        taskManager.createSubTask(secondSubTaskFirstEpic);

        taskManager.createEpic(secondEpic);
        taskManager.createSubTask(firstSubTaskSecondEpic);

        taskManager.createTask(firstTask);
        taskManager.createTask(secondTask);

        Epic epic1 = taskManager.getEpicById(firstEpic.getId());
        taskManager.getSubTaskById(firstSubTaskFirstEpic.getId(), firstEpic);
        taskManager.getSubTaskById(secondSubTaskFirstEpic.getId(), firstEpic);


        Epic epic2 = taskManager.getEpicById(secondEpic.getId());
        taskManager.getSubTaskById(firstSubTaskSecondEpic.getId(), secondEpic);
        taskManager.getTaskById(firstTask.getId());
        taskManager.getTaskById(secondTask.getId());

//        taskManager.deleteAllSubTasks(firstEpic);
//        taskManager.deleteAllSubTasks(secondEpic);
//        taskManager.deleteAllTasks();
//            taskManager.deleteAllEpics();
//        taskManager.deleteTaskById(firstTask.getId());
//        taskManager.deleteTaskById(secondTask.getId());
//        taskManager.deleteEpicById(firstEpic.getId());
//        taskManager.deleteEpicById(secondEpic.getId());
        taskManager.deleteSubTaskById(firstSubTaskFirstEpic.getId(), firstEpic);
        taskManager.deleteSubTaskById(secondSubTaskFirstEpic.getId(), firstEpic);

        List<Task> taskList = taskManager.getHistory();

        for (Task tasks: taskList) {
            System.out.println(tasks);
        }


    }
}