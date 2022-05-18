public class Test {
    public void test(TaskManager inMemoryTaskManager) {
        Task taskOne = new Task("Уборка", "Погладить вещи, постирать рубашки, помыть полы. ", Status.NEW);
        Task taskUpdate = new Task("Update.Уборка", "Update.Погладить вещи, постирать рубашки, помыть полы. ", Status.DONE);
        Task taskTwo = new Task("Прогулка", "Сходить в кафе, прогуляться по парку. ", Status.NEW);
        inMemoryTaskManager.addTasks(taskOne);
        inMemoryTaskManager.addTasks(taskTwo);
        System.out.println("=======================================================================================================================");

        Epic epicOne = new Epic("Поездка", "Москва", Status.NEW);
        Epic epicUpdate = new Epic("Update.Поездка", "Update.Москва", Status.DONE);
        Epic epicTwo = new Epic("Университет", "Написать диплом", Status.NEW);
        inMemoryTaskManager.addEpic(epicOne);
        inMemoryTaskManager.addEpic(epicTwo);

        SubTask subTaskOne = new SubTask("Сходить в театр", "Посетить самый популярный театр", Status.IN_PROGRESS, 3);
        SubTask subTaskTwo = new SubTask("Написать реализацию", "код", Status.NEW, 4);
        SubTask subTaskUpdate = new SubTask("Update.Написать реализацию", "Update.код", Status.IN_PROGRESS, 4);
        SubTask subTaskThree = new SubTask("Написать введение", "текст", Status.NEW, 4);

        inMemoryTaskManager.addSubTask(subTaskOne);
        inMemoryTaskManager.addSubTask(subTaskTwo);
        inMemoryTaskManager.addSubTask(subTaskThree);

        epicOne.addSubtasks(subTaskOne);
        epicTwo.addSubtasks(subTaskTwo);
        epicTwo.addSubtasks(subTaskThree);

        System.out.println("2.1 Вывод всего списка задач Task: ");
        System.out.println(inMemoryTaskManager.printAllTask());
        System.out.println("2.1 Вывод всего списка задач Epic: ");
        System.out.println(inMemoryTaskManager.printAllTaskEpic());
        System.out.println("2.1 Вывод всего списка задач Subtask: ");
        System.out.println(inMemoryTaskManager.printAllSubTask());
        System.out.println("==========================================================");

       // inMemoryTaskManager.deleteAllTask();

        inMemoryTaskManager.updateTask(1, taskUpdate);
        inMemoryTaskManager.updateEpic(3, epicUpdate, inMemoryTaskManager.getEpicSubtasks(3));
        inMemoryTaskManager.updateSubtask(6, subTaskUpdate, 4, subTaskTwo);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Проверка после обновления!");
        System.out.println("2.1 Вывод всего списка задач Task: ");
        System.out.println(inMemoryTaskManager.printAllTask());
        System.out.println("2.1 Вывод всего списка задач Epic: ");
        System.out.println(inMemoryTaskManager.printAllTaskEpic());
        System.out.println("2.1 Вывод всего списка задач Subtask: ");
        System.out.println(inMemoryTaskManager.printAllSubTask());

        System.out.println("Просмотр задач: ");
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(3);

        System.out.println(inMemoryTaskManager.getHistoryManager().getHistory());
        System.out.println("=================================================");

    }
}
