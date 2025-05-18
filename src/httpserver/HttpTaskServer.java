package httpserver;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import infile.FileBackedTasksManager;
import manager.Managers;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
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
//                    case GET_TASK_BY_ID:
//                        writeResponse(exchange, "получен запрос на получение задачи по id"
//                                , 200);
//                        break;
                    case POST_TASK:
                        handlePostTask(exchange);
                        break;
//                    case DELETE_TASK_BY_ID:
//                        writeResponse(exchange, "получен запрос на удаление задачи"
//                                , 200);
//                        break;
//                    case DELETE_TASKS:
//                        writeResponse(exchange, "получен запрос на удаление всех задач"
//                                , 200);
//                        break;
//                    case GET_SUBTASK:
//                        writeResponse(exchange, "получен запрос на получение подзадачи"
//                                , 200);
//                        break;
//                    case GET_SUBTASK_BY_ID:
//                        writeResponse(exchange, "получен запрос на получение всех подзадач"
//                                , 200);
//                        break;
//                    case POST_SUBTASK:
//                        writeResponse(exchange, "получен запрос на добавление подзадачи"
//                                , 201);
//                        break;
//                    case DELETE_SUBTASK_BY_ID:
//                        writeResponse(exchange, "получен запрос на удаление подзадачи по id"
//                                , 200);
//                        break;
//                    case DELETE_SUBTASKS:
//                        writeResponse(exchange, "получен запрос на удаление всех подзадач"
//                                , 200);
//                        break;
//                    case GET_EPIC:
//                        writeResponse(exchange, "получен запрос на получение эпика"
//                                , 200);
//                        break;
//                    case  GET_EPIC_BY_ID:
//                        writeResponse(exchange, "получен запрос на получение эпика по id"
//                                , 200);
//                        break;
//                    case POST_EPIC:
//                        writeResponse(exchange, "получен запрос на добавление эпика"
//                                , 200);
//                        break;
//                    case DELETE_EPIC_BY_ID:
//                        writeResponse(exchange, "получен запрос на удаление эпика по id"
//                                , 200);
//                        break;
//                    case DELETE_EPICS:
//                        writeResponse(exchange, "получен завпрос на удаление всех эпиков"
//                                , 200);
//                        break;
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

        private Endpoint getEndpoint(HttpExchange exchange) {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();
            String query = exchange.getRequestURI().getQuery();

            switch (method) {
                case "GET":
                    switch (path) {
                        case "/tasks":
                            if (query != null && query.startsWith("id=")) {
                                return Endpoint.GET_TASK_BY_ID;
                            } else {
                                return Endpoint.GET_TASK;
                            }
                        case "/subtasks":
                            if (query != null && query.startsWith("id=")) {
                                return Endpoint.GET_SUBTASK_BY_ID;
                            } else {
                                return Endpoint.GET_SUBTASK;
                            }
                        case "/epics":
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
                        case "/epic":
                            return Endpoint.POST_EPIC;
                    }
                    break;

                case "DELETE":
                    switch (path) {
                        case "/tasks":
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
                        case "/epics":
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

        private void handlePostTask(HttpExchange exchange) throws IOException {
            String body = new String(exchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);

            Task task;
            try {
                task = gson.fromJson(body, Task.class);
            } catch (Exception e) {
                writeResponse(exchange, "Получен некорректный JSON", 400);
                return;
            }

            if (task.getName() == null || task.getDescription() == null || task.getStatus() == null) {
                writeResponse(exchange, "Поля задачи не могут быть пустыми", 400);
                return;
            }

            fileBackedTasksManager.createTask(task);
            writeResponse(exchange, "задача добавлена", 201);
        }

        private void handleGetTasks(HttpExchange exchange) throws IOException {
            List<Task> tasks = fileBackedTasksManager.getTasks();
            String json = gson.toJson(tasks);
            writeResponse(exchange, json, 200);
            writeResponse(exchange, "Получен запрос на получение задачи"
                    , 200);
        }

//        private Optional<Integer> getEpicId(HttpExchange exchange) {
//            String[] pathParts = exchange.getRequestURI().getPath().split("/");
//
//            try {
//                return Optional.of(Integer.parseInt(pathParts[2]));
//            } catch (NumberFormatException e) {
//                return Optional.empty();
//            }
//        }

        enum Endpoint {
            GET_TASK,
            GET_TASK_BY_ID,
            POST_TASK,
            DELETE_TASK_BY_ID,
            DELETE_TASKS,

            GET_SUBTASK,
            GET_SUBTASK_BY_ID,
            POST_SUBTASK,
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
