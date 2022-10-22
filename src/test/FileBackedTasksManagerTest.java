package test;

import enums.TaskStatus;
import model.Epic;
import org.junit.jupiter.api.*;
import service.filedao.FileBackedTasksManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Дмитрий Карпушов 22.10.2022
 */
public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private File file;
    private Path path;

    @BeforeEach
    @DisplayName("Создание taskManager")
    void createFileBackedTasksManagerTest() {
        path = Path.of("test.csv");
        file = new File(String.valueOf(path));
        taskManager = new FileBackedTasksManager(file);
        creatTasksTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование получения списка всех задач")
    void getAllTasksTest() {
        super.getAllTasksTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование получения списка всех Epics")
    void getAllEpicsTest() {
        super.getAllEpicsTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование получения списка всех SubTasks")
    void getAllSubTasksTest() {
        super.getAllSubTasksTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование удаления всех задач(Task)")
    void deleteTasksTest() {
        super.deleteTasksTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование удаления всех задач(Epics)")
    void deleteEpicsTest() {
        super.deleteTasksTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование удаления всех задач(SubTask)")
    void deleteSubTasksTest() {
        super.deleteSubTasksTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование получения задачи по id(Task)")
    void getTaskByIdTest() {
        super.getTaskByIdTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование получения эпика по id(Epic)")
    void getEpicByIdTest() {
        super.getEpicByIdTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование получения подзадачи по id(SubTask)")
    void getSubTaskByIdTest() {
        super.getSubTaskByIdTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование добавления новой задачи(Task)")
    void addTaskTest() {
        super.addTaskTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование добавления нового эпика(Epic)")
    void addEpicTest() {
        super.addEpicTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование добавления новой подзадачи(SubTask)")
    void addSubTaskTest() {
        super.addSubTaskTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование обновления задачи(Task)")
    void updateTaskTest() {
        super.updateTaskTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование обновления подзадачи(SubTask)")
    void updateSubTaskTest() {
        super.updateSubTaskTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование удаления задачи по id(Task)")
    void removeTaskByIdTest() {
        super.removeTaskByIdTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование удаления задачи по id(SubTask)")
    void removeSubTaskByIdTest() {
        super.removeSubTaskByIdTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование удаления эпика по id(Epic)")
    void removeEpicByIdTest() {
        super.removeEpicByIdTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование получения всех подзадач для эпика")
    void getSubTuskListByEpic() {
        super.getSubTuskListByEpic();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование изменения статуса Epic")
    void automaticChangeEpicStatusTest() {
        super.automaticChangeEpicStatusTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование автоматического изменения duration эпика")
    void automaticChangeDurationEpicTest() {
        super.automaticChangeDurationEpicTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование автоматического изменения startTime и endTime эпика")
    void automaticChangeEpicStartTimeAndEndTimeTest() {
        super.automaticChangeEpicStartTimeAndEndTimeTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование на пересечение задач")
    void automaticCheckIntersectionsTest() {
        super.automaticCheckIntersectionsTest();
    }

    @Override
    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование получения списка приоритетных задач")
    void getPrioritizedTasksTest() {
        super.getPrioritizedTasksTest();
    }


    @Test
    @DisplayName("FileBackedTasksManagerTest.Тестирование сохранения и восстановления состояния менеджера из файла")
    void shouldCheckSaveAndLoadManagerFromFile() {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.loadFromFile(file);
        assertAll(
                () -> assertEquals(2, fileBackedTasksManager.getTasks().size(),
                        "1.1)Список загруженных задач не соответствует ожидаемому"),
                () -> assertEquals(1, fileBackedTasksManager.getEpics().size(),
                        "1.2)Список загруженных эпиков не соответствует ожидаемому"),
                () -> assertEquals(2, fileBackedTasksManager.getSubTasks().size(),
                        "1.2)Список загруженных подзадач не соответствует ожидаемому")

        );
        taskManager.getTaskById(1);
        taskManager.getSubtaskById(5);
        taskManager.getSubtaskById(4);
        taskManager.getEpicById(3);
        taskManager.getTaskById(2);
        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(file);
        fileBackedTasksManager1.loadFromFile(file);
        assertAll(
                () -> assertEquals(2, fileBackedTasksManager1.getTasks().size(),
                        "2.1)Список загруженных задач не соответствует ожидаемому"),
                () -> assertEquals(1, fileBackedTasksManager1.getEpics().size(),
                        "2.2)Список загруженных эпиков не соответствует ожидаемому"),
                () -> assertEquals(2, fileBackedTasksManager1.getSubTasks().size(),
                        "2.3)Список загруженных подзадач не соответствует ожидаемому")
        );
        try {
            PrintWriter writer = null;
            writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        FileBackedTasksManager fileBackedTasksManager2 = new FileBackedTasksManager(file);
        assertAll(
                () -> assertEquals(Collections.emptyList(), fileBackedTasksManager2.getTasks(),
                        "3.1)Список загруженных задач не соответствует ожидаемому"),
                () -> assertEquals(Collections.emptyList(), fileBackedTasksManager2.getEpics(),
                        "3.2)Список загруженных эпиков не соответствует ожидаемому"),
                () -> assertEquals(Collections.emptyList(), fileBackedTasksManager2.getSubTasks(),
                        "3.3)Список загруженных подзадач не соответствует ожидаемому")
        );

        List<Integer> subTasksIds = new ArrayList<>();
        subTasksIds.add(0);
        Epic newEpic = new Epic("Университет", "Написать диплом", TaskStatus.NEW, LocalDateTime.MIN, 0, LocalDateTime.MIN);
        taskManager.addEpic(newEpic);

        FileBackedTasksManager fileBackedTasksManager3 = new FileBackedTasksManager(file);
        fileBackedTasksManager3.loadFromFile(file);
        assertAll(
                () -> assertEquals(2, fileBackedTasksManager3.getEpics().size(),
                        "4)Пустой эпик не загрузился из файла")
        );
    }
}
