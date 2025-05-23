package httpclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import infile.FileBackedTasksManager;
import tasks.Epic;
import tasks.Task;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;


public class HttpTaskManager extends FileBackedTasksManager {
    private final KVTaskClient kvClient;
    Gson gson = new Gson();

    public HttpTaskManager(URL serverUrl) {
        super();
        this.kvClient = new KVTaskClient(serverUrl.toString());

        try {
            loadFromServer();
        } catch (Exception e) {
            System.out.println("Не удалось загрузить данные с KVServer: " + e.getMessage());
        }
    }

    @Override
    protected void save() {
        try {
            String tasksJson = gson.toJson(getTasks());
            kvClient.put("tasks", tasksJson);

            String epicsJson = gson.toJson(getEpics());
            kvClient.put("epics", epicsJson);

            String historyString = historyToString(historyManager);
            kvClient.put("history", historyString);

        } catch (Exception e) {
            System.out.println("Ошибка при сохранении в KVServer: " + e.getMessage());
        }
    }

    private void loadFromServer() {
        try {
            // загружаем задачи
            String tasksJson = kvClient.load("tasks");
            restoreTasksFromJson(tasksJson);

            String epicsJson = kvClient.load("epics");
            restoreEpicsFromJson(epicsJson);

            String historyJson = kvClient.load("history");
            restoreHistoryFromJson(historyJson);
        } catch (Exception e) {
            System.out.println("Ошибка загрузки с KVServer: " + e.getMessage());
        }
    }

    private void restoreTasksFromJson(String json) {
        Type taskListType = new TypeToken<List<Task>>() {}.getType();
        List<Task> taskList = gson.fromJson(json, taskListType);
        if (taskList != null) {
            for (Task task : taskList) {
                taskHashMap.put(task.getId(), task);
            }
        }
    }

    private void restoreEpicsFromJson(String json) {
        Type epicListType = new TypeToken<List<Epic>>() {}.getType();
        List<Epic> epicList = gson.fromJson(json, epicListType);
        if (epicList != null) {
            for (Epic epic : epicList) {
                epics.put(epic.getId(), epic);
            }
        }
    }

    private void restoreHistoryFromJson(String json) {
        Type historyListType = new TypeToken<List<Integer>>() {}.getType();
        List<Integer> historyIds = gson.fromJson(json, historyListType);
        if (historyIds != null) {
            for (Integer id : historyIds) {
                if (taskHashMap.containsKey(id)) {
                    historyManager.add(taskHashMap.get(id));
                } else if (epics.containsKey(id)) {
                    historyManager.add(epics.get(id));
                }
            }
        }
    }
}
