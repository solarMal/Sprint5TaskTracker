package tasksinterface;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    void createTask(Task task);
    void getTasks();
    Task getTaskById(int id);
    void updateTask(int id, Task task);
    void deleteTaskById(int id);
    void deleteAllTasks();

    void createEpic(Epic epic);
    void getEpics();
    void deleteAllEpics();
    Epic getEpicById(int id);
    void updateEpic(int id, Epic epic);
    void deleteEpicById(int id);

    void createSubTask(SubTask subTask);
    void getAllSubTasks(Epic epic);
    void deleteAllSubTasks(Epic epic);
    SubTask getSubTaskById(int id, Epic epic);
    void updateSubTaskById(int id, SubTask subTask);
    void deleteSubTaskById(int id, Epic epic);

    List<Task> getHistory();

}
