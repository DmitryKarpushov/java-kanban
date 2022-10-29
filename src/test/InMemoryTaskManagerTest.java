package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.manager.InMemoryTaskManager;


import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Дмитрий Карпушов 21.10.2022
 */
public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    void createTaskManagerTest() {
        taskManager = new InMemoryTaskManager();
        creatTasksTest();
    }

    @AfterEach
    void deleteAllTaskTest() {
        taskManager.deleteTasks();
        taskManager.deleteSubtasks();
        taskManager.deleteEpics();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование получения списка всех Tasks")
    void getAllTasksTest() {
        super.getAllTasksTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование получения списка всех Epics")
    void getAllEpicsTest() {
        super.getAllEpicsTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование получения списка всех SubTasks")
    void getAllSubTasksTest() {
        super.getAllSubTasksTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование удаления всех задач(Task)")
    void deleteTasksTest() {
        super.deleteTasksTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование удаления всех задач(Epics)")
    void deleteEpicsTest() {
        super.deleteTasksTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование удаления всех задач(SubTask)")
    void deleteSubTasksTest() {
        super.deleteSubTasksTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование получения задачи по id(Task)")
    void getTaskByIdTest() {
        super.getTaskByIdTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование получения эпика по id(Epic)")
    void getEpicByIdTest() {
        super.getEpicByIdTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование получения подзадачи по id(SubTask)")
    void getSubTaskByIdTest() {
        super.getSubTaskByIdTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование добавления новой задачи(Task)")
    void addTaskTest() {
        super.addTaskTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование добавления нового эпика(Epic)")
    void addEpicTest() {
        super.addEpicTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование добавления новой подзадачи(SubTask)")
    void addSubTaskTest() {
        super.addSubTaskTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование обновления задачи(Task)")
    void updateTaskTest() {
        super.updateTaskTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование обновления подзадачи(SubTask)")
    void updateSubTaskTest() {
        super.updateSubTaskTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование удаления задачи по id(Task)")
    void removeTaskByIdTest() {
        super.removeTaskByIdTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование удаления задачи по id(SubTask)")
    void removeSubTaskByIdTest() {
        super.removeSubTaskByIdTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование удаления эпика по id(Epic)")
    void removeEpicByIdTest() {
        super.removeEpicByIdTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование получения всех подзадач для эпика")
    void getSubTuskListByEpic() {
        super.getSubTuskListByEpic();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование изменения статуса Epic")
    void automaticChangeEpicStatusTest() {
        super.automaticChangeEpicStatusTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование автоматического изменения duration эпика")
    void automaticChangeDurationEpicTest() {
        super.automaticChangeDurationEpicTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование автоматического изменения startTime и endTime эпика")
    void automaticChangeEpicStartTimeAndEndTimeTest() {
        super.automaticChangeEpicStartTimeAndEndTimeTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование на пересечение задач")
    void automaticCheckIntersectionsTest() {
        super.automaticCheckIntersectionsTest();
    }

    @Test
    @DisplayName("InMemoryTaskManagerTest.Тестирование получения списка приоритетных задач")
    void getPrioritizedTasksTest() {
        super.getPrioritizedTasksTest();
    }

}
