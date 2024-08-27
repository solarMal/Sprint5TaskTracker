package tasksmanager;

import tasks.Task;

import java.util.HashMap;

public class TaskManager {
    int dynamicId = 0;

    HashMap<Integer, Task> taskHashMap = new HashMap<>();

    public void createTask(Task task) {
        if (task != null) {
            task.setId(dynamicId++);
            taskHashMap.put(task.getId(), task);
        } else {
            System.out.println("ошибка создания задачи");
        }
    }

    public void getTasks() {
        if (taskHashMap.isEmpty()) {
            System.out.println("нет активных задач");
            return;
        }

        for (Integer i : taskHashMap.keySet()) {
            Task task = taskHashMap.get(i);

            System.out.println("задача " + (i + 1) + " " + task);
        }
    }

    public void getTaskById(int id) {
        id -= 1;
        if (taskHashMap.containsKey(id)) {
            Task task = taskHashMap.get(id);
            System.out.println("задача " + (id + 1) + " " + task);
        } else {
            System.out.println("задача с id " + (id + 1) + " не найдена");
        }
    }

    public void updateTask(int id, Task task) {
        id -= 1;
        if (task != null && taskHashMap.containsKey(id)) {
            task.setId(id);
            taskHashMap.put(task.getId(), task);
        } else {
            System.out.println("ошибка в обновлении задачи");
        }
    }

    public void deleteTaskById(int id) {
        id -= 1;
        if (taskHashMap.containsKey(id)) {
            taskHashMap.remove(id);
        } else {
            System.out.println("удалить задачу не получилось. id " + (id + 1) + " не удалось найди");
        }
    }

    public void deleteAllTasks() {
        taskHashMap.clear();
        System.out.println("все задачи успешно удалены");
    }
}
