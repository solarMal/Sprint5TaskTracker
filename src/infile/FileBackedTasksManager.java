package infile;

import inmemory.InMemoryTaskManager;
import manager.HistoryManager;
import manager.Managers;
import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
    final String filePath = "D:\\dev\\MyProject\\TaskTracker\\taskTrackerFile.txt";

    public FileBackedTasksManager() {
        File file = new File(filePath);

        try {
            if (file.exists()) {
                System.out.println(file.getAbsolutePath());
            } else {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Файл успешно создан: " + file.getAbsolutePath());
                } else {
                    System.out.println("неудалось создать файл");
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
    public void updateTask(int id, Task task) {
        super.updateTask(id, task);
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
    public void updateEpic(int id, Epic epic) {
        super.updateEpic(id, epic);
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
    public void deleteAllSubTasks(Epic epic) {
        super.deleteAllSubTasks(epic);
    }

    @Override
    public void deleteSubTaskById(int id, Epic epic) {
        super.deleteSubTaskById(id, epic);
    }

    @Override
    public SubTask getSubTaskById(int id, Epic epic) {
        SubTask subTask = super.getSubTaskById(id, epic);
        save();
        return subTask;
    }

    @Override
    public void updateSubTaskById(int id, SubTask subTask) {
        super.updateSubTaskById(id, subTask);
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    private void save() {
        try (Writer writer = new FileWriter(filePath, false)) {
            writer.write("id,type,name,status,description,epic" + "\n");

            if (!taskHashMap.isEmpty()) {
                List<Task> tasks = getTasks();
                for (Task task: tasks) {
                    writer.write(toString(task) + "\n");
                }
            }

            if (!epics.isEmpty()) {
                List<Epic> epicList = getEpics();
                for (Epic epic: epicList) {
                    writer.write(toString(epic) + "\n");
                }
            }

            if (!getEpics().isEmpty()) {
                List<SubTask> subTasks = getAllSubTasks();
                for (SubTask subTask: subTasks) {
                    writer.write(toString(subTask) + "\n");
                }
            }

            if (!historyManager.getHistory().isEmpty()) {
                String s = FileBackedTasksManager.historyToString(historyManager);
                writer.write("\n" + s);
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных", e);
        }

    }

    //сохранения задачи в строку
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

    //создания задачи из строки
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

    //сохранение менеджера в строку
    static String historyToString(HistoryManager manager) {
        StringBuilder result = new StringBuilder();
        List<Task> tasks = manager.getHistory();

        if (!tasks.isEmpty()) {
            for (Task task: tasks) {
                result.append(task.getId()).append(",");
            }
        } else {
            System.out.println("история пуста");
        }

        return result.deleteCharAt(result.length()-1).toString();
    }

    //восстановления менеджера истории из CSV.
    static List<Integer> historyFromString(String value) {
        List<Integer> result = new ArrayList<>();
        String[] split = value.split(",");

        for (String s: split) {
            result.add(Integer.parseInt(s));
        }
        return result;
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

        fileBackedTasksManager.createTask(firstTask);
//        String task1 = fileBackedTasksManager.toString(firstTask);
//        System.out.println(task1);

        fileBackedTasksManager.createTask(secondTask);
//        String task2 = fileBackedTasksManager.toString(secondTask);
//        System.out.println(task2);

        fileBackedTasksManager.createTask(updateTask);
//        String task3 = fileBackedTasksManager.toString(updateTask);
//        System.out.println(task3);

        fileBackedTasksManager.createEpic(firstEpic);
//        String epic1 = fileBackedTasksManager.toString(firstEpic);
//        System.out.println(epic1);

        fileBackedTasksManager.createSubTask(firstSubTaskFirstEpic);
//        String subtask1 = fileBackedTasksManager.toString(firstSubTaskFirstEpic);
//        System.out.println(subtask1);

        fileBackedTasksManager.createSubTask(secondSubTaskFirstEpic);
//        String subtask2 = fileBackedTasksManager.toString(secondSubTaskFirstEpic);
//        System.out.println(subtask2);

        fileBackedTasksManager.createSubTask(thirdSubTaskFirstEpic);
//        String subtask3 = fileBackedTasksManager.toString(thirdSubTaskFirstEpic);
//        System.out.println(subtask3);
//
//        String updateEpicStatus = fileBackedTasksManager.toString(firstEpic);
//        System.out.println(updateEpicStatus);
//
//        System.out.println(fileBackedTasksManager.fromString(task1));
//        System.out.println(fileBackedTasksManager.fromString(subtask1));
//        System.out.println(fileBackedTasksManager.fromString(epic1));

        fileBackedTasksManager.getTaskById(firstTask.getId());
        fileBackedTasksManager.getTaskById(secondTask.getId());
        fileBackedTasksManager.getEpicById(firstEpic.getId());
        fileBackedTasksManager.getSubTaskById(firstSubTaskFirstEpic.getId(), firstEpic);
        fileBackedTasksManager.getSubTaskById(secondSubTaskFirstEpic.getId(), firstEpic);

        String s = FileBackedTasksManager.historyToString(fileBackedTasksManager.historyManager);
        System.out.println(s);





    }

}
