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
                , "NEW"
                , firstEpic);


        Epic secondEpic = new Epic("secondEpic", "descriptionSecondEpic");
        SubTask firstSubTaskSecondEpic = new SubTask("firstSubTaskSecondEpic"
                , "descriptionFirstSubTaskSecondEpic"
                , "IN_PROGRESS"
                , secondEpic);

        epicManager.createEpic(firstEpic);
        epicManager.createSubTask(firstSubTaskFirstEpic);
        epicManager.createSubTask(secondSubTaskFirstEpic);

        epicManager.createEpic(secondEpic);
        epicManager.createSubTask(firstSubTaskSecondEpic);

        epicManager.deleteAllEpics();
        epicManager.getEpics();

        taskManager.createTask(firstTask);
        taskManager.createTask(secondTask);

        taskManager.deleteAllTasks();
        taskManager.getTasks();


    }
}