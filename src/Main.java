import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasksmanager.EpicManager;
import tasksmanager.TaskManager;


public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        EpicManager epicManager = new EpicManager();

        Task firstTask = new Task("firstTask", "description firstTask", "new");
        Task secondTask = new Task("secondTask", "description second task", "new");

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

//        taskManager.createTask(firstTask);
//        taskManager.createTask(secondTask);
//        Task task = taskManager.getTaskById(2);
//        Task task1 = taskManager.getTaskById(1);
//        System.out.println(task);
//        taskManager.deleteTaskById(2);
//        taskManager.getTasks();

        epicManager.createEpic(firstEpic);
        epicManager.createSubTask(firstSubTaskFirstEpic);
        epicManager.createSubTask(secondSubTaskFirstEpic);

        epicManager.createEpic(secondEpic);
        epicManager.createSubTask(firstSubTaskSecondEpic);
//        epicManager.getEpics();
        Epic epic1 = epicManager.getEpicById(1);
        System.out.println(epic1);
        SubTask subTask = epicManager.getSubTaskById(3, epic1);
        System.out.println(subTask);

//        epicManager.deleteAllEpics();
//        epicManager.getEpics();
//
//        taskManager.createTask(firstTask);
//        taskManager.createTask(secondTask);
//
//        taskManager.deleteAllTasks();
//        taskManager.getTasks();


    }
}