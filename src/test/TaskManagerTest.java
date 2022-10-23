package test;

import enums.TaskStatus;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.manager.TaskManager;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Дмитрий Карпушов 21.10.2022
 */
public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    public void creatTasksTest() {
        taskManager.addTasks(new Task("Уборка", "Погладить вещи", TaskStatus.NEW, LocalDateTime.of(2022, Month.MAY, 23, 15, 15), 15));
        taskManager.addTasks(new Task("Прогулка", "Сходить в кафе", TaskStatus.NEW, LocalDateTime.of(2022, Month.MAY, 24, 15, 15), 15));
        taskManager.addEpic(new Epic("Университет", "Написать диплом", TaskStatus.NEW, null, 0, null));
        taskManager.addSubTask(new SubTask("Написать вывод", "вывод", TaskStatus.DONE, 3,
                LocalDateTime.of(2022, Month.MAY, 23, 15, 30), 15));
        taskManager.addSubTask(new SubTask("Написать реализацию", "код", TaskStatus.NEW, 3,
                LocalDateTime.of(2022, Month.MAY, 23, 16, 30), 15));
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование получения списка всех задач")
    void getAllTasksTest() {
        assertAll(
                () -> assertNotEquals(0, taskManager.getTasks().size(), "Список должен включать 2 задачи"),
                () -> assertEquals(2, taskManager.getTasks().size(), "Список не должен быть пустым")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование получения списка всех Epic")
    void getAllEpicsTest() {
        assertAll(
                () -> assertEquals(1, taskManager.getEpics().size(), "Список должен включать 1 задачи"),
                () -> assertNotEquals(0, taskManager.getEpics().size(), "Список не должен быть пустым")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование получения списка всех SubTask")
    void getAllSubTasksTest() {
        assertAll(
                () -> assertEquals(2, taskManager.getSubTasks().size(), "Список должен включать 2 задачи"),
                () -> assertNotEquals(0, taskManager.getSubTasks().size(), "Список не должен быть пустым")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование удаления всех Task")
    void deleteTasksTest() {
        taskManager.deleteTasks();
        assertAll(
                () -> assertEquals(Collections.emptyList(), taskManager.getTasks(), "Список не пустой")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование удаления всех Epics")
    void deleteEpicsTest() {
        taskManager.deleteEpics();
        assertAll(
                () -> assertEquals(Collections.emptyList(), taskManager.getEpics(),
                        "Список не пустой")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование удаления всех SubTask")
    void deleteSubTasksTest() {
        taskManager.deleteSubtasks();
        assertAll(
                () -> assertEquals(Collections.emptyList(), taskManager.getSubTasks(),
                        "Не пустой список")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование получения Task по id")
    void getTaskByIdTest() {
        assertAll(
                () -> assertNotNull(taskManager.getTaskById(2), "Задача не найдена"),
                () -> assertNull(taskManager.getTaskById(100), "Ожидание: null при несуществующем id")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование получения Epic по id")
    void getEpicByIdTest() {
        assertAll(
                () -> assertNotNull(taskManager.getEpicById(3), "Задача не найдена"),
                () -> assertNull(taskManager.getEpicById(999), "Ожидание: null при несуществующем id")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование получения SubTask по id")
    void getSubTaskByIdTest() {
        assertAll(
                () -> assertNotNull(taskManager.getSubtaskById(5), "Задача не найдена"),
                () -> assertNull(taskManager.getSubtaskById(999), "Ожидание: null при несуществующем id")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование добавления Task")
    void addTaskTest() {
        Task firstTask = (new Task("Написать 7 спринт", "дописать тесты", TaskStatus.NEW,
                LocalDateTime.of(2024, Month.MAY, 2, 22, 22), 5));
        taskManager.addTasks(firstTask);
        Integer id = firstTask.getId();
        assertAll(
                () -> assertNotNull(taskManager.getTaskById(id), "Задача не найдена"),
                () -> assertEquals(firstTask, taskManager.getTaskById(id), "Задачи не совпадают")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование добавления Epic")
    void addEpicTest() {
        Epic firstEpic = (new Epic("Университет", "Написать диплом", TaskStatus.NEW,
                LocalDateTime.of(2022, Month.FEBRUARY, 2, 22, 30), 0, null));
        taskManager.addEpic(firstEpic);
        assertAll(
                () -> assertNotNull(taskManager.getEpicById(firstEpic.getId()), "Эпик не найдена"),
                () -> assertEquals(firstEpic, taskManager.getEpicById(firstEpic.getId()), "Эпики не совпадают")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование добавления новой SubTask")
    void addSubTaskTest() {
        Epic firstEpic = new Epic("Университет", "Написать диплом", TaskStatus.NEW,
                null, 0, null);
        SubTask firstSubTask = new SubTask("Написать вывод", "вывод", TaskStatus.NEW,
                firstEpic.getId(), LocalDateTime.of(2022, Month.FEBRUARY, 2, 22, 30), 5);
        taskManager.addEpic(firstEpic);
        taskManager.addSubTask(firstSubTask);
        firstEpic.addSubtasks(firstSubTask);
        assertAll(
                () -> assertNotNull(taskManager.getSubtaskById(firstSubTask.getId()), "Задача не найдена"),
                () -> assertEquals(firstSubTask, taskManager.getSubtaskById(firstSubTask.getId()), "Задачи не совпадают")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование обновления Task")
    void updateTaskTest() {
        Task firstTask = new Task("Спринт", "Начать 8ой спринт", TaskStatus.NEW,
                LocalDateTime.of(2024, Month.FEBRUARY, 22, 22, 22), 50);
        taskManager.addTasks(firstTask);
        firstTask.setEndTime(firstTask.getEndTime());
        firstTask.setId(1);
        firstTask.setTitle("updateTaskTest");
        taskManager.updateTask(firstTask);
        assertAll(
                () -> assertEquals(taskManager.getTaskById(1), firstTask, "Задача не обновлена.")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование обновления SubTask")
    void updateSubTaskTest() {
        Epic secondEpic = (new Epic("Университет", "Написать диплом", TaskStatus.NEW, null, 0, null));
        taskManager.addEpic(secondEpic);
        SubTask newSubTask = (new SubTask("Написать вывод", "вывод", TaskStatus.NEW,
                secondEpic.getId(), LocalDateTime.of(2022, Month.FEBRUARY, 2, 22, 30), 5));
        taskManager.addSubTask(newSubTask);
        secondEpic.addSubtasks(newSubTask);
        newSubTask.setEndTime(newSubTask.getEndTime());
        newSubTask.setTitle("Университет 2.0");
        taskManager.updateSubtask(newSubTask);
        assertAll(
                () -> assertNotEquals(taskManager.getSubtaskById(1), newSubTask, "Задача не обновлена.")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование удаления Task по id")
    void removeTaskByIdTest() {
        taskManager.deleteTask(1);
        assertAll(
                () -> assertNull(taskManager.getTaskById(1), "Задача не удалена")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование удаления SubTask по id")
    void removeSubTaskByIdTest() {
        taskManager.deleteSubtask(4);
        assertAll(
                () -> assertNull(taskManager.getSubtaskById(4), "Задача не удалена")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование удаления Epic по id")
    void removeEpicByIdTest() {
        taskManager.deleteEpic(3);
        assertAll(
                () -> assertNull(taskManager.getEpicById(3), "Задача не удалена")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование получения всех SubTask для Epic")
    void getSubTuskListByEpic() {
        assertAll(
                () -> assertNotEquals(taskManager.getSubtasksEpicId(3).size(), 1, "Задачи не совпадают")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование автоматического изменения duration Epic'а ")
    void automaticChangeDurationEpicTest() {
        Epic epic = taskManager.getEpicById(3);
        taskManager.updateTimeEpic(epic);
        assertAll(
                () -> assertNotEquals(epic.getDuration(), 51, "Время не совпадает")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование изменения статуса Epic")
    void automaticChangeEpicStatusTest() {
        Epic epic1 = taskManager.getEpicById(3);
        Task subTask3 = taskManager.getSubtaskById(5);
        subTask3.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask((SubTask) subTask3);
        assertAll(
                () -> assertEquals(epic1.getStatus(), TaskStatus.DONE, "Время не совпадает")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование автоматического изменения startTime и endTime Epic'а ")
    void automaticChangeEpicStartTimeAndEndTimeTest() {
        Epic epic1 = taskManager.getEpicById(3);
        Task subTask2 = taskManager.getSubtaskById(4);
        Task subTask3 = taskManager.getSubtaskById(5);
        taskManager.updateTimeEpic(epic1);
        assertAll(
                () -> assertEquals(subTask2.getStartTime(), epic1.getStartTime(),
                        "Время самой ранней подзадачи не совпадает со временем начала Epic'а"),
                () -> assertEquals(subTask3.getEndTime(), epic1.getEndTime(),
                        "Время самой поздней подзадачи не совпадает со временем начала Epic'а")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование на пересечение задач")
    void automaticCheckIntersectionsTest() {
        Task firstTask = new Task("Уборка 2.0", "Погладить вещи", TaskStatus.NEW, LocalDateTime.of(2030, Month.FEBRUARY, 2, 22, 22), 5);
        SubTask firstSubTask = new SubTask("Написать вывод", "вывод", TaskStatus.NEW, 3, LocalDateTime.of(2030, Month.FEBRUARY, 2, 22, 30), 5);
        taskManager.addTasks(firstTask);
        taskManager.addSubTask(firstSubTask);
        assertAll(
                () -> assertTrue(taskManager.getTasks().contains(firstTask),
                        "Задача не должна была быть добавлена так как пересекается"),
                () -> assertTrue(taskManager.getSubTasks().contains(firstSubTask),
                        "Подзадача не должна была быть добавлена так как пересекается")
        );
        firstTask.setId(1);
        firstSubTask.setId(5);
        taskManager.updateTask(firstTask);
        taskManager.updateSubtask(firstSubTask);
        assertAll(
                () -> assertTrue(taskManager.getTasks().contains(firstTask),
                        "Задача не должна была быть обновлена так как пересекается"),
                () -> assertTrue(taskManager.getSubTasks().contains(firstSubTask),
                        "Подзадача не должна была быть обновлена так как пересекается")
        );
    }

    @Test
    @DisplayName("TaskManagerTest.Тестирование получения списка приоритетных задач")
    void getPrioritizedTasksTest() {
        List<Task> expectedList = List.of(
                taskManager.getTaskById(1),
                taskManager.getSubtaskById(4),
                taskManager.getSubtaskById(5),
                taskManager.getTaskById(2));
        List<Task> prioritizedTasks = taskManager.getPrioritizedTasks();

        assertEquals(expectedList, prioritizedTasks, "Неправильный временной порядок");
    }

}
