package Manager;

import Manager.*;

import service.*;

import java.io.File;

//
public class Managers {

    //Для класса InMemoryTaskManager
    /*
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }*/

//    public static TaskManager getDefault() {
//        return new FileBackedTasksManager();
//    }

    public static TaskManager getInMemoryTaskManager(HistoryManager historyManager) {
       // return new InMemoryTaskManager(historyManager);
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
