package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    void createTask(Task task);
    List<Task> getTasks();
    Task getTaskById(int id);
    Task updateTask(int id, Task task);
    void deleteTaskById(int id);
    void deleteAllTasks();

    void createEpic(Epic epic);
    List<Epic> getEpics();
    Epic getEpicById(int id);
    Epic updateEpic(int id, Epic epic);
    void deleteEpicById(int id);
    void deleteAllEpics();

    void createSubTask(SubTask subTask);
    List<SubTask> getAllSubTasks();
    SubTask getSubTaskById(int id, Epic epic);
    SubTask updateSubTaskById(int id, SubTask subTask);
    void deleteSubTaskById(int id);
    void deleteAllSubTasks();

    List<Task> getHistory();
}
