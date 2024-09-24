import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasksinterface.EpicInterface;
import tasksinterface.SubTaskInterface;
import tasksmanager.InMemoryEpicManager;



public class Main {
    public static void main(String[] args) {
//        TaskInterface taskManager = new InMemoryTaskManager();
        EpicInterface epicManager = new InMemoryEpicManager();
        SubTaskInterface subTaskManager = new InMemoryEpicManager();

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


        epicManager.createEpic(firstEpic);
        epicManager.createEpic(secondEpic);

        subTaskManager.createSubTask(firstSubTaskFirstEpic);
        subTaskManager.createSubTask(secondSubTaskFirstEpic);
        subTaskManager.createSubTask(firstSubTaskSecondEpic);


        subTaskManager.deleteSubTaskById(1, firstEpic);
        subTaskManager.deleteSubTaskById(2, firstEpic);
        subTaskManager.deleteSubTaskById(1, secondEpic);
        epicManager.deleteAllEpics();
        epicManager.getEpics();




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