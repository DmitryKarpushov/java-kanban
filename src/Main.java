public class Main {
    public static void main(String[] args) {
        //InMemoryTaskManager taskManager = new InMemoryTaskManager();
        //Managers taskManager = new Managers();
        TaskManager manager = (TaskManager) Managers.getDefault();
        Test test = new Test();
        test.test((InMemoryTaskManager) manager);
    }
}
