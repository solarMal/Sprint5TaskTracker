import inmemory.InMemoryTaskManager;

public class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    public InMemoryTasksManagerTest() {
        super(new InMemoryTaskManager());
    }
}
