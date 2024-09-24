package tasksmanager;

import tasks.Task;
import tasksinterface.TaskInterface;

import java.util.HashMap;

public class InMemoryTaskManager implements TaskInterface {
    int dynamicId = 1;

    HashMap<Integer, Task> taskHashMap = new HashMap<>();

    @Override
    public void createTask(Task task) {
        if (task != null) {
            task.setId(dynamicId++);
            taskHashMap.put(task.getId(), task);
        } else {
            System.out.println("ошибка создания задачи");
        }
    }

    @Override
    public void getTasks() {
        if (taskHashMap.isEmpty()) {
            System.out.println("нет активных задач");
            return;
        }

        for (Integer i : taskHashMap.keySet()) {
            Task task = taskHashMap.get(i);

            System.out.println("задача " + i + " " + task);
        }
    }

    @Override
    public Task getTaskById(int id) {

        Task task = null;
        if (taskHashMap.containsKey(id)) {
            task = taskHashMap.get(id);
        } else {
            System.out.println("задача с id " + id + " не найдена");
        }
        return task;
    }

    @Override
    public void updateTask(int id, Task task) {
        if (task != null && taskHashMap.containsKey(id)) {
            task.setId(id);
            taskHashMap.put(task.getId(), task);
        } else {
            System.out.println("ошибка в обновлении задачи. Задача с id " + id + " не существует");
        }
    }

    @Override
    public void deleteTaskById(int id) {
        if (taskHashMap.containsKey(id)) {
            taskHashMap.remove(id);
        } else {
            System.out.println("удалить задачу не получилось. id " + id + " не удалось найди");
        }
    }

    @Override
    public void deleteAllTasks() {
        taskHashMap.clear();
        System.out.println("все задачи успешно удалены");
    }
}
