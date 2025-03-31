package inmemory;

import manager.HistoryManager;
import manager.Managers;
import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import manager.TaskManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    int dynamicId = 1;

    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Task> taskHashMap = new HashMap<>();
    protected HistoryManager historyManager = Managers.getDefaultHistory();

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
    public List<Task> getTasks() {
        List<Task> result = new ArrayList<>();

        if (taskHashMap.isEmpty()) {
            System.out.println("нет активных задач");
        }

        for (Integer i : taskHashMap.keySet()) {
            Task task = taskHashMap.get(i);

            result.add(task);
        }
        return result;
    }

    @Override
    public Task getTaskById(int id) {

        Task task = null;
        if (taskHashMap.containsKey(id)) {
            task = taskHashMap.get(id);
        } else {
            System.out.println("задача с id " + id + " не найдена");
            return null;
        }
        historyManager.add(task);
        return task;
    }

    @Override
    public Task updateTask(int id, Task task) {
        if (task == null) {
            System.out.println("Ошибка: передана пустая задача.");
            return null;
        }
        if (!taskHashMap.containsKey(id)) {
            System.out.println("Ошибка: задача с id " + id + " не найдена.");
            return null;
        }

        task.setId(id);
        taskHashMap.put(id, task);
        return task;
    }


    @Override
    public void deleteTaskById(int id) {
        if (taskHashMap.containsKey(id)) {
            historyManager.remove(id);
            taskHashMap.remove(id);
        } else {
            System.out.println("удалить задачу не получилось. id " + id + " не удалось найди");
        }
    }

    @Override
    public void deleteAllTasks() {
        for (Integer i: taskHashMap.keySet()) {
            historyManager.remove(i);
        }
        taskHashMap.clear();
        System.out.println("все задачи успешно удалены");
    }

    @Override
    public void createEpic(Epic epic) {
        if (epic != null) {
            epic.setId(dynamicId++);
            epic.setStatus(Status.NEW);
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Эпик не создан");
        }
    }

    @Override
    public List<Epic> getEpics() {
        List<Epic> result = new ArrayList<>();
        if (!epics.isEmpty()) {
            for (Integer epicId : epics.keySet()) {
                Epic epic = epics.get(epicId);
                result.add(epic);
            }
        }
        return result;
    }

    @Override
    public void deleteAllEpics() {
        if (!epics.isEmpty()) {

            for (Epic epic: epics.values()) {
                historyManager.remove(epic.getId());
                for (SubTask subTask: epic.getSubTasks().values()) {
                    historyManager.remove(subTask.getId());
                }
            }

            epics.clear();
            System.out.println("все эпики удалены");
        } else {
            System.out.println("нет активных эпиков");
        }
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = null;
        if (epics.containsKey(id)) {
            epic = epics.get(id);
        } else {
            System.out.println("Эпик с id " + id + " не существует");
            return null;
        }
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Epic updateEpic(int id, Epic epic) {
        if (epic == null) {
            System.out.println("Ошибка: передан пустой эпик");
            return null;
        }
        if (!epics.containsKey(id)) {
            System.out.println("Ошибка: эпик с id " + id + " не найден.");
            return null;
        }

        HashMap<Integer, SubTask> subTasks = epics.get(id).getSubTasks();

        epic.setId(id);

        for (Map.Entry<Integer, SubTask> subTaskEntry : subTasks.entrySet()) {
            epic.getSubTasks().put(subTaskEntry.getKey(), subTaskEntry.getValue());
        }

        epics.put(id, epic);
        return epic;
    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            historyManager.remove(id);

            Epic epic = epics.get(id);
            for (int i: epic.getSubTasks().keySet()) {
                historyManager.remove(i);
            }

            epics.remove(id);
            System.out.println("Эпик с id № " + id + " успешно удалён");
        } else {
            System.out.println("Эпик с id № " + id + " нельзя удалить, он не существует");
        }
    }

    @Override
    public void createSubTask(SubTask subTask) {
        if (subTask != null && epics.containsKey(subTask.getEpic().getId())) {
            Epic epic = epics.get(subTask.getEpic().getId());

            subTask.setId(dynamicId);
            epic.getSubTasks().put(dynamicId++, subTask);

            updateEpicStatus(epic);
        } else {
            System.out.println("Эпик не существует");
        }
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        List<SubTask> result = new ArrayList<>();
        if (!epics.isEmpty()) {
            for (Epic epic: epics.values()) {
                if (!epic.getSubTasks().isEmpty()) {
                    result.addAll(epic.getSubTasks().values());
                }
            }
        }
        return result;
    }

    @Override
    public void deleteAllSubTasks(Epic epic) {
        if (!epic.getSubTasks().isEmpty()) {

            for (int i: epic.getSubTasks().keySet()) {
                historyManager.remove(i);
            }

            epic.getSubTasks().clear();
            epic.setStatus(Status.NEW);
            System.out.println("подзадачи успешно удалены");
        } else {
            System.out.println(epic + " не имеет подзадач");
        }
    }

    @Override
    public void deleteSubTaskById(int id, Epic epic) {
        if (!epic.getSubTasks().isEmpty() && epic.getSubTasks().containsKey(id)) {
            historyManager.remove(id);
            epic.getSubTasks().remove(id);
            System.out.println("подзадача с id " + id + " удалена");
            updateEpicStatus(epic);
        } else {
            System.out.println("ошибка удаления. Подзадачи с id " + id + " не существует");
        }
    }

    @Override
    public SubTask getSubTaskById(int id, Epic epic) {
        SubTask subTask = null;

        Map<Integer, SubTask> subTasks = epic.getSubTasks();
        if (subTasks != null && subTasks.containsKey(id)) {
            subTask = subTasks.get(id);
            historyManager.add(subTask);
        } else {
            System.out.println(epic + " не имеет подзадачи с id " + id);
            return null;
        }

        return subTask;
    }


    @Override
    public SubTask updateSubTaskById(int id, SubTask subTask) {
        if (subTask == null) {
            System.out.println("Ошибка: передана пустая подзадача");
            return null;
        }

        if (subTask.getEpic() == null) {
            System.out.println("Ошибка: подзадача не привязана к эпику");
            return null;
        }

        Epic epic = epics.get(subTask.getEpic().getId());

        if (epic == null) {
            System.out.println("Ошибка: эпик с id " + subTask.getEpic().getId() + " не найден.");
            return null;
        }

        if (epic.getSubTasks().containsKey(id)) {
            subTask.setId(id);
            epic.getSubTasks().put(id, subTask);
            updateEpicStatus(epic);
            return subTask;
        } else {
            System.out.println("Ошибка: подзадача с id " + id + " не найдена в эпике.");
            return null;
        }
    }

    private void updateEpicStatus(Epic epic) {
        int newCounter = 0;
        int doneCounter = 0;

        for (SubTask sub : epic.getSubTasks().values()) {
            if (sub.getStatus().equals(Status.NEW)) {
                newCounter++;
            } else if (sub.getStatus().equals(Status.DONE)) {
                doneCounter++;
            }
        }

        if (newCounter == epic.getSubTasks().size()) {
            epic.setStatus(Status.NEW);
        } else if (doneCounter == epic.getSubTasks().size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
