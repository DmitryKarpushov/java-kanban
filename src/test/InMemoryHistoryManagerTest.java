package test;
import enums.TaskStatus;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.history.InMemoryHistoryManager;
import service.manager.InMemoryTaskManager;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * @author Дмитрий Карпушов 21.10.2022
 */
public class InMemoryHistoryManagerTest {
    private Task taskTest;
    private Epic epicTest;
    private SubTask subTaskTest;
    InMemoryHistoryManager historyManagerTest;

    @BeforeEach
    @DisplayName("Создание задачи, эпика и подзадачи перед каждым тестом")
    void createInMemoryHistoryManagerTest() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        historyManagerTest = new InMemoryHistoryManager();
        taskTest = new Task("Уборка", "Погладить вещи", TaskStatus.NEW, LocalDateTime.of(2022, Month.MAY, 23, 15, 15), 15);
        taskManager.addTasks(taskTest);
        epicTest = new Epic("Университет", "Написать диплом", TaskStatus.NEW);
        taskManager.addEpic(epicTest);
        subTaskTest =new SubTask("Написать вывод", "вывод", TaskStatus.NEW, 3,LocalDateTime.of(2022, Month.MAY, 23, 15, 30), 15);
        taskManager.addSubTask(subTaskTest);
    }

    @AfterEach
    @DisplayName("InMemoryHistoryManagerTest.Очистка после каждого теста")
    void deleteHistory() {
        List<Task> task = historyManagerTest.getHistory();
        for(Task tasks : task){
            historyManagerTest.remove(tasks.getId());
        }
    }

    @Test
    @DisplayName("InMemoryHistoryManagerTest.Добавления Task в историю")
    void add() {
        Integer sizeHistory = historyManagerTest.getHistory().size();
        historyManagerTest.add(taskTest);
        assertEquals(sizeHistory+1, historyManagerTest.getHistory().size(),
                "Размер истории вызовов не изменился");
    }

    @Test
    @DisplayName("InMemoryHistoryManagerTest.Тест истории Task")
    void getHistory() {
        Integer sizeHistory = historyManagerTest.getHistory().size();
        historyManagerTest.add(taskTest);
        historyManagerTest.add(epicTest);
        historyManagerTest.add(subTaskTest);
        assertEquals(sizeHistory+3, historyManagerTest.getHistory().size(),
                "Размер истории вызовов не изменился");
    }

    @Test
    @DisplayName("InMemoryHistoryManagerTest.Тест удаления из истории")
    void remove() {
        Integer sizeHistory = historyManagerTest.getHistory().size();
        historyManagerTest.add(epicTest);
        assertEquals(sizeHistory+1, historyManagerTest.getHistory().size(),
                String.format("Размер истории вызовов не изменился"));
        historyManagerTest.remove(epicTest.getId());
        assertEquals(sizeHistory, historyManagerTest.getHistory().size(),
                "Размер менеджера историй не изменился");
    }

    @Test
    @DisplayName("InMemoryHistoryManagerTest.Тест пустого списка истории")
    void isEmptyHistoryTest(){
        assertEquals(0, historyManagerTest.getHistory().size(), "Список истории не пустой");
    }

    @Test
    @DisplayName("InMemoryHistoryManagerTest.Тест пересечения задач в истории")
    void addTwoViewTaskOneTest(){
        Integer sizeHistory = historyManagerTest.getHistory().size();
        historyManagerTest.add(taskTest);
        historyManagerTest.add(taskTest);
        assertEquals(sizeHistory+1, historyManagerTest.getHistory().size(),
                "Некорректный размер истории");
    }

}
