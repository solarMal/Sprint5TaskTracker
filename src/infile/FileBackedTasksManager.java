package infile;

import inmemory.InMemoryTaskManager;
import manager.HistoryManager;
import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    final static String filePath = "D:\\dev\\MyProject\\TaskTracker\\taskTrackerFile.csv";
    final static File file = new File(filePath);

    public FileBackedTasksManager() {
        try {
            if (file.exists()) {

            } else {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Файл успешно создан: " + file.getAbsolutePath());
                } else {
                    System.out.println("не удалось создать файл");
                }
            }
        } catch (IOException exception) {
            System.out.println("произошла ошибка " + exception.getMessage());
        }
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public List<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Task updateTask(int id, Task task) {
        Task updateTask = super.updateTask(id, task);
        super.updateTask(id, task);
        return updateTask;
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public List<Epic> getEpics() {
        return super.getEpics();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public Epic updateEpic(int id, Epic epic) {
        Epic epic1 = super.updateEpic(id, epic);
        super.updateEpic(id, epic);
        return epic1;
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
    }

    @Override
    public void createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return super.getAllSubTasks();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
    }

    @Override
    public SubTask getSubTaskById(int id, Epic epic) {
        SubTask subTask = super.getSubTaskById(id, epic);
        save();
        return subTask;
    }

    @Override
    public SubTask updateSubTaskById(int id, SubTask subTask) {
        SubTask result = super.updateSubTaskById(id, subTask);
        super.updateSubTaskById(id, subTask);
        return result;
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    protected void save() {
        try (Writer writer = new FileWriter(filePath, false)) {
            writer.write("id,type,name,status,description,epic\n");

            for (Task task : getTasks()) {
                writer.write(toString(task) + "\n");
            }

            for (Epic epic : getEpics()) {
                writer.write(toString(epic) + "\n");
            }

            for (SubTask subTask : getAllSubTasks()) {
                writer.write(toString(subTask) + "\n");
            }

            writer.write("\n");

            String history = historyToString(historyManager);
            if (!history.isBlank()) {
                writer.write(history);
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных", e);
        }
    }

    private String toString(Task task) {
        String result;
        String subtaskEpicId = "";

        String id = String.valueOf(task.getId());
        String type = getType(task);
        String name = task.getName();
        String status = String.valueOf(task.getStatus());
        String description = task.getDescription();

        if (type.equals("SUBTASK")) {
            List<SubTask> subTasks = getAllSubTasks();
            for (SubTask subTask: subTasks) {
                if (task.getId() == subTask.getId()) {
                    subtaskEpicId = String.valueOf(subTask.getEpic().getId());
                }
            }
        }

        if (type.equals("SUBTASK")) {
            result = String.join(",", id, type, name, status, description, subtaskEpicId);
        } else {
            result = String.join(",", id, type, name, status, description + ",");
        }

        return result;
    }

     private Task fromString(String value) {
        String[] split = value.split(",");

        if (split[1].equals("TASK")) {
            int id = Integer.parseInt(split[0]);
            String name = split[2];
            String description = split[4];
            Status status = Status.valueOf(split[3]);

            return new Task(id, name, description, status);
        } else if (split[1].equals("EPIC")) {

            int id = Integer.parseInt(split[0]);
            String name = split[2];
            String description = split[4];
            Status status = Status.valueOf(split[3]);

            if (getEpicById(id).getSubTasks() != null) {
                HashMap<Integer, SubTask> subTaskHashMap = new HashMap<>(getEpicById(id).getSubTasks());
                return new Epic(id, name, description, status, subTaskHashMap);
            } else {
                return new Epic(id, name, description, status);
            }

        } else if (split[1].equals("SUBTASK")) {
            int id = Integer.parseInt(split[0]);
            String name = split[2];
            String description = split[4];
            Status status = Status.valueOf(split[3]);
            Epic epic = getEpicById(Integer.parseInt(split[5]));
            return new SubTask(id, name, description, status, epic);
        }
        return null;
    }

    protected String historyToString(HistoryManager manager) {
        StringBuilder result = new StringBuilder();
        List<Task> tasks = manager.getHistory();

        if (!tasks.isEmpty()) {
            for (Task task : tasks) {
                result.append(task.getId()).append(",");
            }
            result.deleteCharAt(result.length() - 1);
        } else {
            System.out.println("история пуста");
        }

        return result.toString();
    }

    protected List<Integer> historyFromString(String value) {
        List<Integer> historyIds = new ArrayList<>();
        if (value == null || value.isEmpty()) {
            return historyIds;
        }

        String[] parts = value.split(",");
        for (String part : parts) {
            historyIds.add(Integer.parseInt(part));
        }

        return historyIds;
    }

    public FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager manager = new FileBackedTasksManager();
        List<Integer> historyIds = new ArrayList<>();
        boolean isHistorySection = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    isHistorySection = true; // Переходим к секции истории
                    continue;
                }

                if (!isHistorySection) {
                    Task task = fromString(line);
                    if (task != null) {
                        if (task.getClass().equals(Task.class)) {
                            manager.createTask(task);
                        } else if (task.getClass().equals(Epic.class)) {
                            manager.createEpic((Epic) task);
                        } else if (task.getClass().equals(SubTask.class)) {
                            manager.createSubTask((SubTask) task);
                        }
                    }
                } else {
                    historyIds = historyFromString(line);
                }
            }

            for (int id : historyIds) {
                Task task = manager.findTaskById(id);
                if (task != null) {
                    manager.historyManager.add(task);
                    save();
                } else {
                    System.err.println("Задача с ID " + id + " не найдена для восстановления истории.");
                }
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при загрузке данных из файла", e);
        }

        return manager;
    }

    private Task findTaskById(int id) {
        if (taskHashMap.containsKey(id)) {
            return taskHashMap.get(id);
        }
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        for (Epic epic : epics.values()) {
            if (epic.getSubTasks().containsKey(id)) {
                return epic.getSubTasks().get(id);
            }
        }
        return null;
    }

    private String getType(Task task) {
        String result = "";
        if (task.getClass().equals(Task.class)) {
            result = String.valueOf(TaskType.TASK);
        } else if (task.getClass().equals(Epic.class)) {
            result = String.valueOf(TaskType.EPIC);
        } else if (task.getClass().equals(SubTask.class)) {
            result = String.valueOf(TaskType.SUBTASK);
        }
        return result;
    }

    public static void main(String[] args) throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();

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
                , Status.NEW
                , firstEpic);
        SubTask thirdSubTaskFirstEpic = new SubTask("thirdSubTaskFirstEpic"
                , "descriptionThirdSubTaskFirstEpic"
                , Status.NEW
                , firstEpic);

        Epic secondEpic = new Epic("secondEpic", "descriptionSecondEpic");
        SubTask firstSubTaskSecondEpic = new SubTask("firstSubTaskSecondEpic"
                , "descriptionFirstSubTaskSecondEpic"
                , Status.IN_PROGRESS
                , secondEpic);

    }

}
