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
        SubTask thirdSubTaskFirstEpic = new SubTask("thirdSubTaskFirstEpic"
                , "descriptionThirdSubTaskFirstEpic"
                , Status.DONE
                , firstEpic);

        Epic secondEpic = new Epic("secondEpic", "descriptionSecondEpic");
        SubTask firstSubTaskSecondEpic = new SubTask("firstSubTaskSecondEpic"
                , "descriptionFirstSubTaskSecondEpic"
                , Status.IN_PROGRESS
                , secondEpic);

        taskManager.createTask(firstTask);
        taskManager.createTask(secondTask);
        taskManager.getTaskById(firstTask.getId());
        taskManager.getTaskById(secondTask.getId());
        List<Task> taskList = taskManager.getHistory();
        System.out.println(taskList);
//        taskManager.updateTask(5,firstTask);
//        taskManager.createTask(secondTask);
//        List<Task> tasks = taskManager.getTasks();
//        System.out.println(tasks);

//        taskManager.createEpic(firstEpic);
//        taskManager.createSubTask(firstSubTaskFirstEpic);
//        taskManager.createSubTask(secondSubTaskFirstEpic);
//        taskManager.createSubTask(thirdSubTaskFirstEpic);
//        Epic epic = taskManager.getEpicById(firstEpic.getId());
//        System.out.println(epic);

//        taskManager.createEpic(secondEpic);
//        taskManager.createSubTask(firstSubTaskSecondEpic);
//
//        List<Epic> epics = taskManager.getEpics();
//        System.out.println(epics);
//        List<SubTask> subTasks = taskManager.getAllSubTasks();
//        System.out.println(subTasks);



//        taskManager.getTaskById(secondTask.getId());
//        taskManager.getTaskById(firstTask.getId());
//        taskManager.getEpicById(firstEpic.getId());
//        taskManager.getSubTaskById(firstSubTaskFirstEpic.getId(), firstEpic);
//        taskManager.getSubTaskById(thirdSubTaskFirstEpic.getId(), firstEpic);
//        taskManager.getSubTaskById(secondSubTaskFirstEpic.getId(), firstEpic);

//        taskManager.deleteTaskById(firstTask.getId());
//        taskManager.deleteTaskById(secondTask.getId());
//        taskManager.deleteSubTaskById(firstSubTaskFirstEpic.getId(), firstEpic);
//        taskManager.deleteSubTaskById(secondSubTaskFirstEpic.getId(), firstEpic);
//        taskManager.deleteSubTaskById(secondSubTaskFirstEpic.getId(), firstEpic);
    }
}