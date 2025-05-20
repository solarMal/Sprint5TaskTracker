package httpserver;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import infile.FileBackedTasksManager;
import manager.Managers;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final FileBackedTasksManager fileBackedTasksManager = Managers.getFileBackedTaskManager();
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        //создаём и запускаем сервер
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    static class TasksHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Endpoint endpoint = getEndpoint(exchange);

            if (endpoint != null) {
                switch (endpoint) {
                    case GET_TASK:
                        handleGetTasks(exchange);
                        break;
                    case GET_TASK_BY_ID:
                        handleGetTaskById(exchange);
                        break;
                    case POST_TASK:
                        handlePostTask(exchange);
                        break;
                    case DELETE_TASK_BY_ID:
                        handleDeleteTaskById(exchange);
                        break;
                    case DELETE_TASKS:
                        handleDeleteTask(exchange);
                        break;
//                    case GET_SUBTASK:
//                        writeResponse(exchange, "получен запрос на получение подзадачи"
//                                , 200);
//                        break;
//                    case GET_SUBTASK_BY_ID:
//                        writeResponse(exchange, "получен запрос на получение всех подзадач"
//                                , 200);
//                        break;
                    case POST_SUBTASK:
                        handlePostSubTask(exchange);
                        break;
//                    case DELETE_SUBTASK_BY_ID:
//                        writeResponse(exchange, "получен запрос на удаление подзадачи по id"
//                                , 200);
//                        break;
//                    case DELETE_SUBTASKS:
//                        writeResponse(exchange, "получен запрос на удаление всех подзадач"
//                                , 200);
//                        break;
                    case POST_EPIC:
                        handlePostEpic(exchange);
                        break;
                    case GET_EPIC:
                        handleGetEpics(exchange);
                        break;
                    case  GET_EPIC_BY_ID:
                        handleGetEpicById(exchange);
                        break;
                    case DELETE_EPIC_BY_ID:
                        handleDeleteEpicById(exchange);
                        break;
                    case DELETE_EPICS:
                        handleDeleteEpics(exchange);
                        break;
//                    case GET_HISTORY:
//                        writeResponse(exchange, "получен запрос на получение истории"
//                                , 200);
//                        break;
//                    case GET_PRIORITIZED_TASKS:
//                        writeResponse(exchange, "получен запрос на получение приоритетных задач"
//                                , 200);
//                        break;
                }
            } else {
                writeResponse(exchange, "неизвестный запрос", 404);
            }
        }

        private void handlePostTask(HttpExchange exchange) throws IOException {
            String body = new String(exchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);

            Task task;
            try {
                 task = gson.fromJson(body, Task.class);

                 if (task.getId() != 0 && fileBackedTasksManager.getTaskById(task.getId()) != null) {
                     fileBackedTasksManager.updateTask(task.getId(), task);
                     writeResponse(exchange, "задача обновлена", 200);
                 } else {
                     fileBackedTasksManager.createTask(task);
                     writeResponse(exchange, "задача добавлена", 201);
                 }
            } catch (Exception e) {
                writeResponse(exchange, "Получен некорректный JSON", 400);
            }

        }

        private void handleGetTaskById(HttpExchange exchange) throws IOException {
            Optional<Integer> id = getId(exchange);

            if (id.isEmpty()) {
                writeResponse(exchange, "Некорректный идентификатор задачи", 400);
                return;
            }

            int taskId = id.get();

            Task task = fileBackedTasksManager.getTaskById(taskId);

            if (task == null) {
                writeResponse(exchange, "Задача с идентификатором " + taskId + " не найдена"
                        , 404);
            } else {
                writeResponse(exchange, gson.toJson(task), 200);
            }
        }

        private void handleGetTasks(HttpExchange exchange) throws IOException {
            List<Task> tasks = fileBackedTasksManager.getTasks();

            if (tasks.isEmpty()) {
                writeResponse(exchange, "Нет активных задач"
                        , 404);
                return;
            }
            String json = gson.toJson(tasks);
            writeResponse(exchange, json, 200);
        }

        private void handleDeleteTask(HttpExchange exchange) throws IOException {
            fileBackedTasksManager.deleteAllTasks();
            List<Task> tasks = fileBackedTasksManager.getTasks();
            if (tasks == null || tasks.isEmpty()) {
                writeResponse(exchange, "Все задачи успешно удалены", 200);
            } else {
                writeResponse(exchange, "Что то пошло не так", 500);
            }
        }

        private void handleDeleteTaskById(HttpExchange exchange) throws IOException {
            Optional<Integer> id = getId(exchange);

            if (id.isEmpty()) {
                writeResponse(exchange, "Некорректный идентификатор задачи", 400);
                return;
            }
            int taskId = id.get();

            Task task = fileBackedTasksManager.getTaskById(taskId);
            if (task == null) {
                writeResponse(exchange, "Задача с идентификатором " + taskId + " не найдена"
                        , 404);
            } else {
                fileBackedTasksManager.deleteTaskById(taskId);
                writeResponse(exchange, "Задача с идентификатором " + taskId + " успешно удалена"
                        , 200);
            }
        }

        private Optional<Integer> getId(HttpExchange exchange) {
            String query = exchange.getRequestURI().getQuery();
            if (query == null || !query.startsWith("id=")) {
                return Optional.empty();
            }

            try {
                return Optional.of(Integer.parseInt(query.substring(3)));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }

        public void handlePostEpic(HttpExchange exchange) throws IOException {
            String body = new String(exchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);

            Epic epic;
            try {
                epic = gson.fromJson(body, Epic.class);

                if (epic.getId() != 0 && fileBackedTasksManager.getEpicById(epic.getId()) != null) {
                    fileBackedTasksManager.updateEpic(epic.getId(), epic);
                    writeResponse(exchange, "эпик обновлен", 200);
                } else {
                    fileBackedTasksManager.createEpic(epic);
                    writeResponse(exchange, "эпик добавлен", 201);
                }
            } catch (Exception e) {
                writeResponse(exchange, "получен некорректный JSON", 400);
            }
        }

        private void handleGetEpics(HttpExchange exchange) throws IOException {
            List<Epic> epics = fileBackedTasksManager.getEpics();

            if (epics.isEmpty()) {
                writeResponse(exchange, "Нет активных эпиков", 404);
                return;
            }

            String json = gson.toJson(epics);
            writeResponse(exchange, json, 200);
        }

        private void handleGetEpicById(HttpExchange exchange) throws IOException {
            Optional<Integer> id = getId(exchange);

            if (id.isEmpty()) {
                writeResponse(exchange, "Некорректный идентификатор эпика", 400);
                return;
            }

            int epicId = id.get();

            Epic epic = fileBackedTasksManager.getEpicById(epicId);

            if (epic == null) {
                writeResponse(exchange, "Эпик с идентификатором " + epicId + " не найдена"
                        , 404);
            } else {
                writeResponse(exchange, gson.toJson(epic), 200);
            }
        }

        private void handleDeleteEpics(HttpExchange exchange) throws IOException {
            fileBackedTasksManager.deleteAllEpics();
            List<Epic> epics = fileBackedTasksManager.getEpics();

            if (epics == null || epics.isEmpty()) {
                writeResponse(exchange, "Все задачи успешно удалены", 200);
            } else {
                writeResponse(exchange, "Что то пошло не так", 500);
            }
        }

        private void handleDeleteEpicById(HttpExchange exchange) throws IOException {
            Optional<Integer> id = getId(exchange);

            if (id.isEmpty()) {
                writeResponse(exchange, "Некорректный идентификатор эпика", 400);
                return;
            }

            int epicId = id.get();

            Epic epic = fileBackedTasksManager.getEpicById(epicId);

            if (epic == null) {
                writeResponse(exchange, "Задача с идентификатором " + epicId + " не найдена"
                        , 404);
            } else {
                fileBackedTasksManager.deleteEpicById(epicId);
                writeResponse(exchange, "Задача с идентификатором " + epicId + " успешно удалена"
                        , 200);
            }
        }

        private void handlePostSubTask(HttpExchange exchange) throws IOException {

        }

        private Endpoint getEndpoint(HttpExchange exchange) {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();
            String query = exchange.getRequestURI().getQuery();

            switch (method) {
                case "GET":
                    switch (path) {
                        case "/tasks/task/":
                            if (query != null && query.startsWith("id=")) {
                                return Endpoint.GET_TASK_BY_ID;
                            } else {
                                return Endpoint.GET_TASK;
                            }
                        case "/tasks/subtask/":
                            if (query != null && query.startsWith("id=")) {
                                return Endpoint.GET_SUBTASK_BY_ID;
                            } else {
                                return Endpoint.GET_SUBTASK;
                            }
                        case "/tasks/epic/":
                            if (query != null && query.startsWith("id=")) {
                                return Endpoint.GET_EPIC_BY_ID;
                            } else {
                                return Endpoint.GET_EPIC;
                            }
                        case "/history":
                            return Endpoint.GET_HISTORY;
                        case "/tasks/prioritized":
                            return Endpoint.GET_PRIORITIZED_TASKS;
                    }
                    break;

                case "POST":
                    switch (path) {
                        case "/tasks/task":
                            return Endpoint.POST_TASK;
                        case "/subtask":
                            return Endpoint.POST_SUBTASK;
                        case "/tasks/epic":
                            return Endpoint.POST_EPIC;
                    }
                    break;

                case "DELETE":
                    switch (path) {
                        case "/tasks/task/":
                            if (query != null && query.startsWith("id=")) {
                                return Endpoint.DELETE_TASK_BY_ID;
                            } else {
                                return Endpoint.DELETE_TASKS;
                            }
                        case "/subtasks":
                            if (query != null && query.startsWith("id=")) {
                                return Endpoint.DELETE_SUBTASK_BY_ID;
                            } else {
                                return Endpoint.DELETE_SUBTASKS;
                            }
                        case "/tasks/epic/":
                            if (query != null && query.startsWith("id=")) {
                                return Endpoint.DELETE_EPIC_BY_ID;
                            } else {
                                return Endpoint.DELETE_EPICS;
                            }
                    }
                    break;
            }

            return null;
        }

        private void writeResponse(HttpExchange exchange,
                                   String responseString,
                                   int responseCode) throws IOException {
            if (!responseString.isBlank()) {
                byte[] responseBytes = responseString.getBytes(DEFAULT_CHARSET);
                exchange.sendResponseHeaders(responseCode, responseBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            } else {
                exchange.sendResponseHeaders(responseCode, -1);//тело не отправляется
             }
        }

        enum Endpoint {
            GET_TASK,
            GET_TASK_BY_ID,
            POST_TASK,
            DELETE_TASK_BY_ID,
            DELETE_TASKS,

            GET_SUBTASK,
            GET_SUBTASK_BY_ID,
            POST_SUBTASK,
            UPDATE_SUBTASK,
            DELETE_SUBTASK_BY_ID,
            DELETE_SUBTASKS,

            GET_EPIC,
            GET_EPIC_BY_ID,
            POST_EPIC,
            DELETE_EPIC_BY_ID,
            DELETE_EPICS,

            GET_HISTORY,
            GET_PRIORITIZED_TASKS
        }
    }

}
