import inmemory.InMemoryTaskManager;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import tasks.Epic;
import tasks.SubTask;

class EpicTest {

    InMemoryTaskManager inMemoryTaskManager;
    Epic firstEpic;
    SubTask firstSubTaskFirstEpic;
    SubTask secondSubTaskFirstEpic;
    SubTask thirdSubTaskFirstEpic;

    @BeforeEach
    void beforeEach() {
        inMemoryTaskManager = new InMemoryTaskManager();
        firstEpic = new Epic("firstEpic", "descriptionFirstEpic");
    }

    @Test
    void shouldBeNewStatusWithNullSubtask() {
        inMemoryTaskManager.createEpic(firstEpic);
        Status newStatus = Status.NEW;
        assertEquals(newStatus, firstEpic.getStatus());

    }

    @Test
    void shouldBeNewStatusWithAllNewSubtask() {
        inMemoryTaskManager.createEpic(firstEpic);
        createThreeNewSubtask();
        firstSubTaskFirstEpic.setStatus(Status.NEW);
        secondSubTaskFirstEpic.setStatus(Status.NEW);
        thirdSubTaskFirstEpic.setStatus(Status.NEW);

        Status newStatus = Status.NEW;
        assertEquals(newStatus, firstEpic.getStatus());
    }

    @Test
    void shouldBeDoneStatusWithAllDoneSubtask() {
        inMemoryTaskManager.createEpic(firstEpic);
        createThreeNewSubtask();
        firstSubTaskFirstEpic.setStatus(Status.DONE);
        secondSubTaskFirstEpic.setStatus(Status.DONE);
        thirdSubTaskFirstEpic.setStatus(Status.DONE);

        Status doneStatus = Status.DONE;
        assertEquals(doneStatus, firstEpic.getStatus());
    }

    @Test
    void shouldBeInProgressStatusWithNewAndDoneSubtask() {
        inMemoryTaskManager.createEpic(firstEpic);
        createThreeNewSubtask();
        firstSubTaskFirstEpic.setStatus(Status.NEW);
        secondSubTaskFirstEpic.setStatus(Status.DONE);
        thirdSubTaskFirstEpic.setStatus(Status.DONE);

        Status inProgressStatus = Status.IN_PROGRESS;
        assertEquals(inProgressStatus, firstEpic.getStatus());
    }

    @Test
    void shouldBeInProgressStatusWithAllInProgressSubtask() {
        inMemoryTaskManager.createEpic(firstEpic);
        createThreeNewSubtask();
        firstSubTaskFirstEpic.setStatus(Status.IN_PROGRESS);
        secondSubTaskFirstEpic.setStatus(Status.IN_PROGRESS);
        thirdSubTaskFirstEpic.setStatus(Status.IN_PROGRESS);

        Status inProgressStatus = Status.IN_PROGRESS;
        assertEquals(inProgressStatus, firstEpic.getStatus());
    }

    private void createThreeNewSubtask() {
        firstSubTaskFirstEpic = new SubTask("firstSubTaskFirstEpic"
                , "descriptionFirstSubTaskFirstEpic"
                , Status.NEW
                , firstEpic);
        secondSubTaskFirstEpic = new SubTask("secondSubTaskFirstEpic"
                , "descriptionSecondSubTaskFirstEpic"
                , Status.NEW
                , firstEpic);
        thirdSubTaskFirstEpic = new SubTask("thirdSubTaskFirstEpic"
                , "descriptionThirdSubTaskFirstEpic"
                , Status.NEW
                , firstEpic);
        inMemoryTaskManager.createSubTask(firstSubTaskFirstEpic);
        inMemoryTaskManager.createSubTask(secondSubTaskFirstEpic);
        inMemoryTaskManager.createSubTask(thirdSubTaskFirstEpic);
    }
}