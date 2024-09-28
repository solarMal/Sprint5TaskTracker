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
        taskManager.createEpic(secondEpic);

        taskManager.createSubTask(firstSubTaskFirstEpic);
        taskManager.createSubTask(secondSubTaskFirstEpic);
        taskManager.createSubTask(firstSubTaskSecondEpic);

        taskManager.createTask(firstTask);
        taskManager.createTask(secondTask);

        Task task1 = taskManager.getTaskById(1);
        Task task2 = taskManager.getTaskById(2);
        Task epic1 = taskManager.getEpicById(1);
        Task epic2 = taskManager.getEpicById(2);
        Task sub1 = taskManager.getSubTaskById(1, firstEpic);
        Task sub2 = taskManager.getSubTaskById(2, firstEpic);
        Task sub3 = taskManager.getSubTaskById(2, firstEpic);
        Task sub4 = taskManager.getSubTaskById(2, firstEpic);
        Task sub5 = taskManager.getSubTaskById(2, firstEpic);
        Task sub6 = taskManager.getSubTaskById(2, firstEpic);
        Task sub7 = taskManager.getSubTaskById(2, firstEpic);
        Task sub8 = taskManager.getSubTaskById(2, firstEpic);
        Task sub9 = taskManager.getSubTaskById(2, firstEpic);
        Task task5 = taskManager.getEpicById(1);

        List<Task> tasks = taskManager.getHistory();

        for (Task i: tasks) {
            System.out.println(i);
        }





       taskManager.createTask(firstTask);
       taskManager.createTask(secondTask);

       taskManager.getTasks();

       Task task14 = taskManager.getTaskById(1);
        System.out.println(task14);
       Task task22 = taskManager.getTaskById(2);
        System.out.println(task22);

        taskManager.updateTask(3, updateTask);

        taskManager.getTasks();

        Task update = taskManager.getTaskById(2);
        System.out.println(update);

        taskManager.deleteTaskById(3);
        taskManager.getTasks();

        taskManager.deleteAllTasks();
        taskManager.getTasks();

    }
}