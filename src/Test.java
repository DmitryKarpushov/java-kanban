public class Test {
    public void test(TaskManager taskManager) {
        Task taskOne = new Task("Уборка", "Погладить вещи, постирать рубашки, помыть полы. ", "NEW");
        Task taskUpdate = new Task("Update.Уборка", "Update.Погладить вещи, постирать рубашки, помыть полы. ", "DONE");
        Task taskTwo = new Task("Прогулка", "Сходить в кафе, прогуляться по парку. ", "NEW");
        taskManager.createTasks(taskOne, taskManager.generateId());
        taskManager.createTasks(taskTwo, taskManager.generateId());

        Epic epicOne = new Epic("Поездка", "Москва", "NEW");
        Epic epicUpdate = new Epic("Update.Поездка", "Update.Москва", "DONE");
        Epic epicTwo = new Epic("Университет", "Написать диплом", "NEW");
        taskManager.createEpic(epicOne, taskManager.generateId());
        taskManager.createEpic(epicTwo, taskManager.generateId());

        SubTask subTaskOne = new SubTask("Сходить в театр", "Посетить самый популярный театр", "IN_PROGRESS", "Поездка",3);
        SubTask subTaskTwo = new SubTask("Написать реализацию", "код", "NEW", "Университет",4);
        SubTask subTaskUpdate = new SubTask("Update.Написать реализацию", "Update.код", "IN_PROGRESS", "Университет",4);
        SubTask subTaskThree = new SubTask("Написать введение", "текст", "NEW", "Университет",4);

        taskManager.createSubTask(subTaskOne, taskManager.generateId());
        taskManager.createSubTask(subTaskTwo, taskManager.generateId());
        taskManager.createSubTask(subTaskThree, taskManager.generateId());

        epicOne.setListSubtasks(subTaskOne);
        epicTwo.setListSubtasks(subTaskTwo);
        epicTwo.setListSubtasks(subTaskThree);

        System.out.println("2.1 Вывод всего списка задач Task: ");
        System.out.println(taskManager.printAllTask(taskManager.getTaskHashMap()));
        System.out.println("2.1 Вывод всего списка задач Epic: ");
        System.out.println(taskManager.printAllTaskEpic(taskManager.getMapOfEpics()));
        System.out.println("2.1 Вывод всего списка задач Subtask: ");
        System.out.println(taskManager.printAllSubTask(taskManager.getMapOfSubTasks()));
        System.out.println("==========================================================");

        taskManager.updateTask(1,taskUpdate);
        taskManager.updateEpic(3,epicUpdate,taskManager.contentEpic(3));
        taskManager.updateSubtask(6,subTaskUpdate,4,subTaskTwo);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Проверка после обновления!");
        System.out.println("2.1 Вывод всего списка задач Task: ");
        System.out.println(taskManager.printAllTask(taskManager.getTaskHashMap()));
        System.out.println("2.1 Вывод всего списка задач Epic: ");
        System.out.println(taskManager.printAllTaskEpic(taskManager.getMapOfEpics()));
        System.out.println("2.1 Вывод всего списка задач Subtask: ");
        System.out.println(taskManager.printAllSubTask(taskManager.getMapOfSubTasks()));
    }
}
