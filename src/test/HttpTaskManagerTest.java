package test;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import enums.TaskStatus;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.filedao.FileBackedTasksManager;
import service.http.HttpTaskServer;

import service.manager.Managers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class HttpTaskManagerTest {
    HttpTaskServer server;
    FileBackedTasksManager fileBackedTasksManager;
    Gson gson = Managers.getGson();

    @BeforeEach
    void createHttpTaskManagerTest() throws IOException {
        server = new HttpTaskServer();
        fileBackedTasksManager = Managers.getDefaultFileBackedTasks();
        Task taskOne = new Task("Уборка", "Погладить вещи", TaskStatus.NEW,
                LocalDateTime.of(2099, Month.FEBRUARY, 2, 22, 22), 5);
        Epic epicTwo = new Epic("Университет", "Написать диплом", TaskStatus.NEW, null, 0, null);

        fileBackedTasksManager.addTasks(taskOne);
        fileBackedTasksManager.addEpic(epicTwo);
        SubTask subTaskOne = new SubTask("Написать вывод", "вывод", TaskStatus.DONE, epicTwo.getId(),
                LocalDateTime.of(2022, Month.FEBRUARY, 2, 22, 30), 5);
        fileBackedTasksManager.addSubTask(subTaskOne);
        epicTwo.addSubtasks(subTaskOne);

        server.start();
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getTasks.Проверка получения Tasks")
    void getTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        List<Task> list = gson.fromJson(response.body(), taskType);
        assertNotNull(list, "Задачи не возвращаются");
        assertEquals(1, list.size(), "Не верное количество задач");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getEpics.Проверка получения Epic")
    void getEpics() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        List<Task> list = gson.fromJson(response.body(), taskType);
        assertNotNull(list, "Задачи не возвращаются");
        assertEquals(1, list.size(), "Не верное количество задач");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getSubTasks.Проверка получения SubTasks")
    void getSubTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        List<Task> list = gson.fromJson(response.body(), taskType);
        assertNotNull(list, "Задачи не возвращаются");
        assertEquals(0, list.size(), "Не верное количество задач");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getTaskById.Проверка получения Task по id")
    void getTaskById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8081/tasks/task?id=1")).
                GET().
                build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<Task>() {
        }.getType();
        Task received = gson.fromJson(response.body(), taskType);
        assertNull(received, "Задачи не возвращаются");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getEpicById.Проверка получения Epic по id")
    void getEpicById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8081/tasks/epic?id=2")).
                GET().
                build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<Task>() {
        }.getType();
        Task received = gson.fromJson(response.body(), taskType);
        assertNull(received, "Задачи не возвращаются");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getSubtaskById.Проверка получения Subtask по id")
    void getSubtaskById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8081/tasks/subtask?id=3")).
                GET().
                build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<Task>() {
        }.getType();
        Task received = gson.fromJson(response.body(), taskType);
        assertNotNull(received, "Задачи не возвращаются");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.addTask.Проверка создания задачи")
    void addTask() throws IOException, InterruptedException {
        Task taskTwo = new Task("Уборка", "Погладить вещи", TaskStatus.NEW, LocalDateTime.of(2097, Month.FEBRUARY, 2, 22, 22), 5);
        fileBackedTasksManager.addTasks(taskTwo);
        System.out.println();
        String str = gson.toJson(taskTwo);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        client = HttpClient.newHttpClient();
        url = URI.create("http://localhost:8081/tasks/task?id=3");
        request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }


    @Test
    @DisplayName("HttpTaskManagerTest.addSubTask.Проверка создания SubTask")
    void addSubTask() throws IOException, InterruptedException {
        SubTask subTaskOne = new SubTask("Написать вывод", "вывод", TaskStatus.DONE, 0,
                LocalDateTime.of(2022, Month.FEBRUARY, 2, 22, 30), 5);
        fileBackedTasksManager.addSubTask(subTaskOne);
        String str = gson.toJson(subTaskOne);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        client = HttpClient.newHttpClient();
        url = URI.create("http://localhost:8081/tasks/subtask?id=3");
        request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }


    @Test
    @DisplayName("HttpTaskManagerTest.deleteAllTask.Проверка удаления удаления Task")
    void deleteAllTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotEquals(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.deleteAllEpic.Проверка удаления удаления Epic")
    void deleteAllEpic() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotEquals(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.deleteAllSubTask.Проверка удаления удаления SubTask")
    void deleteAllSubTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Успешный статус кода: 200");
    }


    @Test
    @DisplayName("HttpTaskManagerTest.SubtaskRemoveById.Проверка удаления Subtask по id")
    void subtaskRemoveById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/subtask?id=3");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET() //
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Успешный статус кода: 200");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.taskRemoveById.Проверка удаления Task по id")
    void taskRemoveById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/task?id=1");
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotEquals(response.statusCode() == 200, "Успешный статус кода: 200");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.epicRemoveById.Проверка удаления Epic по id")
    void epicRemoveById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/epic?id=2");
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotEquals(response.statusCode() == 200, "Успешный статус кода: 200");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getHistory.Проверка получения истории")
    void getHistory() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8081/tasks/history")).
                GET().
                build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }
}