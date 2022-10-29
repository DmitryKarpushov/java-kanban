package test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.TaskStatus;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.*;
import service.filedao.FileBackedTasksManager;
import service.http.HttpTaskServer;
import service.http.KVServer;
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

/**
 * @author Дмитрий Карпушов 29.10.2022
 */
class HttpTaskManagerTest {
    private static KVServer kvServer;
    private static HttpTaskServer server;
    FileBackedTasksManager fileBackedTasksManager;
    Gson gson = Managers.getGson();

    @BeforeEach
    void setUp() throws IOException {
        server = new HttpTaskServer();
        fileBackedTasksManager = Managers.getDefaultFileBackedTasks();


        fileBackedTasksManager.addTasks(new Task("Прогулка", "Сходить в кафе", TaskStatus.NEW,
                LocalDateTime.of(2022, Month.MAY, 24, 15, 15), 15));
        Epic epic = new Epic("Университет", "Написать диплом", TaskStatus.NEW,
                LocalDateTime.of(2025, Month.MAY, 24, 15, 15), 15,   LocalDateTime.of(2025, Month.MAY, 24, 15, 30));
        fileBackedTasksManager.addEpic(epic);
        SubTask subTask1 = new SubTask("Написать вывод", "вывод", TaskStatus.DONE, 2,
                LocalDateTime.of(2022, Month.MAY, 23, 15, 30), 15);
        fileBackedTasksManager.addSubTask(subTask1);
        epic.addSubtasks(subTask1);
        kvServer.start();
        server.start();
    }
    @AfterEach
    void tearDown() {
        kvServer.stop();
        server.stop();
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getTaskById")
    void getTaskById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8081/tasks/task?id=1")).
                GET().
                build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getEpicById")
    void getEpicById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8081/tasks/epic?id=2")).
                GET().
                build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getSubTaskById")
    void getSubTaskById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8081/tasks/subtask?id=3")).
                GET().
                build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    @DisplayName("HttpTaskManagerTest.creationSubTask")
    void creationSubTask() throws IOException, InterruptedException {
        SubTask subTask3 = new SubTask("Написать вывод", "вывод", TaskStatus.DONE, 2,
                LocalDateTime.of(2022, Month.MAY, 23, 15, 30), 15);
        fileBackedTasksManager.addSubTask(subTask3);

        String str = gson.toJson(subTask3);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }


    @Test
    @DisplayName("HttpTaskManagerTest.creationEpic")
    void creationEpic() throws IOException, InterruptedException {
        Epic epicThree = new Epic("Университет", "Написать диплом", TaskStatus.NEW);
        fileBackedTasksManager.addEpic(epicThree);

        String str = gson.toJson(epicThree);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());

    }

    @Test
    @DisplayName("HttpTaskManagerTest.creationTask")
    void creationTask() throws IOException, InterruptedException {
        Task task = new Task("Прогулка", "Сходить в кафе", TaskStatus.NEW,
                LocalDateTime.of(2022, Month.MAY, 24, 15, 15), 15);
        fileBackedTasksManager.addTasks(task);

        String str = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    @DisplayName("HttpTaskManagerTest.removeAllTask")
    void removeAllTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        URI url = URI.create("http://localhost:8081/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
    }
    @Test
    @DisplayName("HttpTaskManagerTest.removeAllSubtask")
    void removeAllSubtask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        URI url = URI.create("http://localhost:8081/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
    }
    @Test
    @DisplayName("HttpTaskManagerTest.removeAllEpics")
    void removeAllEpics() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        URI url = URI.create("http://localhost:8081/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getEpics")
    void getEpics() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8081/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());

    }

    @Test
    @DisplayName("HttpTaskManagerTest.getSubTasks")
    void getSubTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8081/tasks/subtask")).
                GET().
                build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getSubTasks")
    void getTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8081/tasks/task")).
                GET().
                build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }


}