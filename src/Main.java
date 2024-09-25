import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import manager.TaskManager;
import inmemory.InMemoryTaskManager;


public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new InMemoryTaskManager();

        Task firstTask = new Task("firstTask", "description firstTask", "new");
        Task secondTask = new Task("secondTask", "description second task", "new");
        Task updateTask = new Task("updateTask", "description updateTask", "new");

        Epic firstEpic = new Epic("firstEpic", "descriptionFirstEpic");
        SubTask firstSubTaskFirstEpic = new SubTask("firstSubTaskFirstEpic"
                , "descriptionFirstSubTaskFirstEpic"
                , "DONE"
                , firstEpic);
        SubTask secondSubTaskFirstEpic = new SubTask("secondSubTaskFirstEpic"
                , "descriptionSecondSubTaskFirstEpic"
                , "DONE"
                , firstEpic);


        Epic secondEpic = new Epic("secondEpic", "descriptionSecondEpic");
        SubTask firstSubTaskSecondEpic = new SubTask("firstSubTaskSecondEpic"
                , "descriptionFirstSubTaskSecondEpic"
                , "IN_PROGRESS"
                , secondEpic);


        taskManager.createEpic(firstEpic);
        taskManager.createEpic(secondEpic);

        taskManager.createSubTask(firstSubTaskFirstEpic);
        taskManager.createSubTask(secondSubTaskFirstEpic);
        taskManager.createSubTask(firstSubTaskSecondEpic);


//        taskManager.deleteSubTaskById(1, firstEpic);
//        taskManager.deleteSubTaskById(2, firstEpic);
//        taskManager.deleteSubTaskById(1, secondEpic);
//        taskManager.deleteAllEpics();
        taskManager.getEpics();




//       taskManager.createTask(firstTask);
//       taskManager.createTask(secondTask);
//
//       taskManager.getTasks();
//
//       Task task1 = taskManager.getTaskById(1);
//        System.out.println(task1);
//       Task task2 = taskManager.getTaskById(2);
//        System.out.println(task2);
//
//        taskManager.updateTask(3, updateTask);
//
//        taskManager.getTasks();
//
//        Task update = taskManager.getTaskById(2);
//        System.out.println(update);
//
//        taskManager.deleteTaskById(3);
//        taskManager.getTasks();
//
//        taskManager.deleteAllTasks();
//        taskManager.getTasks();

    }
}