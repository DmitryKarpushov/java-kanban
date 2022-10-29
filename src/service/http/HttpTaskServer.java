package service.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import model.Epic;
import model.SubTask;
import model.Task;
import service.filedao.FileBackedTasksManager;
import service.manager.Managers;
import service.manager.TaskManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

/**
 * @author Дмитрий Карпушов 28.10.2022
 */

public class HttpTaskServer {

    public static final int PORT = 8081;
    private final HttpServer server;
    private final Gson gson;
    private FileBackedTasksManager taskManager;

    public HttpTaskServer() throws IOException {
        this(Managers.getDefaultFileBackedTasks());
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        Path path = Path.of("test.csv");
        File file = new File(String.valueOf(path));
        this.taskManager = Managers.getDefaultFileBackedTasks();
        this.taskManager.loadFromFile(file);
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handler);
    }

    private void handler(HttpExchange exchange) {
        try {
            final String path = exchange.getRequestURI().getPath().replaceFirst("/tasks/", "");
            switch (path) {
                case "task":
                    handleTasks(exchange);
                    break;
                case "subtask":
                    handleSubtask(exchange);
                    break;
                case "epic":
                    handleEpic(exchange);
                    break;
                case "history":
                    handleHistory(exchange);
                    break;
                default:
            }
        } catch (IOException exception) {
            System.out.println("Ошибка при обработке запроса");
        } finally {
            exchange.close();
        }
    }

    private void handleTasks(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery();
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        switch (requestMethod) {
            case "GET": {
                if (query != null) {
                    String idTask = query.substring(3);
                    Task task = taskManager.getTaskById(Integer.parseInt(idTask));
                    String response = gson.toJson(task);
                    sendText(exchange, response);
                    return;
                } else {
                    String response = gson.toJson(taskManager.getTasks());
                    sendText(exchange, response);
                    return;
                }
            }
            case "POST": {
                try {
                    Task task = gson.fromJson(body, Task.class);
                    taskManager.addTasks(task);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            case "DELETE": {
                if (query != null) {
                    String idTask = query.substring(3);
                    taskManager.deleteTask(Integer.parseInt(idTask));
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    taskManager.deleteTasks();
                    exchange.sendResponseHeaders(200, 0);
                }
            }
        }
    }
    private void handleEpic(HttpExchange exchange) throws IOException {

        String requestMethod = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery(); //параметры запроса
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        switch (requestMethod) {
            case "GET": {
                if (query != null) {
                    String idEpic = query.substring(3);
                    Epic epic = taskManager.getEpicById( Integer.parseInt(idEpic));
                    String response = gson.toJson(epic);
                    sendText(exchange, response);
                    return;
                } else {
                    String response = gson.toJson(taskManager.getEpics());    //для отладки заменить на inMemoryTaskManager для работы вернуть строчку выше
                    sendText(exchange, response);
                    return;
                }
            }
            case "POST": {
                try {
                    Epic epic = gson.fromJson(body, Epic.class);
                    taskManager.addEpic(epic);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            case "DELETE": {
                if (query != null) {
                    String idEpic = query.substring(3);
                    taskManager.deleteEpic(Integer.parseInt(idEpic));
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    taskManager.deleteEpics();
                    exchange.sendResponseHeaders(200, 0);
                }
            }
        }
    }

    private void handleSubtask(HttpExchange exchange) throws IOException {

        String requestMethod = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery();
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        switch (requestMethod) {
            case "GET": {
                if (query != null) {
                    String idSubTask = query.substring(3);
                    SubTask subtask = (SubTask) taskManager.getSubtaskById(Integer.parseInt(idSubTask));
                    String response = gson.toJson(subtask);
                    sendText(exchange, response);
                    return;
                } else {
                    String response = gson.toJson(taskManager.getSubTasks());
                    sendText(exchange, response);
                    return;
                }
            }
            case "POST": {
                try {
                    SubTask subtask = gson.fromJson(body, SubTask.class);  //не работает gson
                    taskManager.addSubTask(subtask);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            case "DELETE": {
                if (query != null) {
                    String idSubTask = query.substring(3);
                    taskManager.deleteSubtask(Integer.parseInt(idSubTask));
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    taskManager.deleteSubtasks();
                    exchange.sendResponseHeaders(200, 0);
                }
            }
        }
    }
    private void handleHistory(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equals("GET")) {
            List<Task> history = taskManager.getHistoryView();
            String response = gson.toJson(history);
            sendText(exchange, response);
        }
    }
    public void start() {
        System.out.println("Запускаем сервер Задач " + PORT);
        System.out.println("http://localhost:" + PORT + "/tasks");
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту" + PORT);
    }

    protected void sendText(HttpExchange exchange, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, resp.length);
        exchange.getResponseBody().write(resp);
    }
}
