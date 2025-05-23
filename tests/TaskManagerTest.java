import manager.TaskManager;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import tasks.SubTask;
import tasks.Task;
import tasks.Epic;

import java.util.List;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;
    protected Task firstTask;
    protected Task secondTask;

    protected Epic firstEpic;
    protected Epic secondEpic;
    protected SubTask firstSubTaskFirstEpic;
    protected SubTask secondSubTaskFirstEpic;
    protected SubTask thirdSubTaskFirstEpic;

    public TaskManagerTest(T taskManager) {
        this.taskManager = taskManager;
    }

    @BeforeEach
    void beforeEach() {
        createAllTasks();
    }

    @Test
    void shouldCreateTask() {
        taskManager.createTask(firstTask);
        Task task = taskManager.getTaskById(firstTask.getId());

        assertNotNull(task);
        assertEquals(task, firstTask);
    }

    @Test
    void shouldBeNotEmptyTask() {
        taskManager.createTask(firstTask);
        List<Task> emptyTasks = taskManager.getTasks();

        assertNotNull(emptyTasks);
        assertFalse(emptyTasks.isEmpty());
    }

    @Test
    void shouldBeIncorrectIdForTask() {
        taskManager.createTask(firstTask);
        Task inCorrectId = taskManager.getTaskById(secondTask.getId());

        assertNull(inCorrectId);
    }

    @Test
    void shouldCreateEpic() {
        taskManager.createEpic(firstEpic);
        Epic resultEpic = taskManager.getEpicById(firstEpic.getId());

        assertNotNull(resultEpic);
        assertEquals(resultEpic, firstEpic);
    }

    @Test
    void shouldBeNotEmptyEpic() {
        taskManager.createEpic(firstEpic);
        List<Epic> emptyEpic = taskManager.getEpics();

        assertNotNull(emptyEpic);
        assertFalse(emptyEpic.isEmpty());
    }

    @Test
    void shouldBeIncorrectIdForEpic() {
        taskManager.createEpic(firstEpic);
        Epic incorrectId = taskManager.getEpicById(secondEpic.getId());

        assertNull(incorrectId);
    }

    @Test
    void shouldCreateSubtask() {
        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);
        SubTask subTask = taskManager.getSubTaskById(firstSubTaskFirstEpic.getId(), firstEpic);


        assertNotNull(subTask);
        assertEquals(subTask, firstSubTaskFirstEpic);
        assertEquals(firstEpic, firstSubTaskFirstEpic.getEpic());
    }

    @Test
    void shouldBeNotEmptySubtask() {
        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);

        List<SubTask> emptyList = taskManager.getAllSubTasks();

        assertNotNull(emptyList);
        assertFalse(emptyList.isEmpty());
    }

    @Test
    void shouldBeIncorrectIdForSubTask() {
        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);

        SubTask incorrectId = taskManager.getSubTaskById(secondSubTaskFirstEpic.getId(), firstEpic);

        assertNull(incorrectId);
    }

    @Test
    void shouldBeReturnAllTasks() {
        taskManager.createTask(firstTask);
        taskManager.createTask(secondTask);

        List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(firstTask));
        assertTrue(tasks.contains(secondTask));
    }

    @Test
    void shouldBeReturnNotEmptyListForAllTasks() {
        taskManager.createTask(firstTask);
        taskManager.createTask(secondTask);

        List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        assertTrue(tasks.contains(firstTask));
        assertTrue(tasks.contains(secondTask));
    }

    @Test
    void shouldBeEmptyListForAllTasks() {
        List<Task> emptyTasks = taskManager.getTasks();

        assertNotNull(emptyTasks);
        assertTrue(emptyTasks.isEmpty());
    }

    @Test
    void shouldBeReturnAllEpics() {
        taskManager.createEpic(firstEpic);
        taskManager.createEpic(secondEpic);

        List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics);
        assertEquals(2, epics.size());
        assertTrue(epics.contains(firstEpic));
        assertTrue(epics.contains(secondEpic));
    }

    @Test
    void shouldBeReturnNotEmptyListForAllEpics() {
        taskManager.createEpic(firstEpic);
        taskManager.createEpic(secondEpic);

        List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics);
        assertFalse(epics.isEmpty());
        assertTrue(epics.contains(firstEpic));
        assertTrue(epics.contains(secondEpic));
    }

    @Test
    void shouldBeEmptyListForAllEpics() {
        List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics);
        assertTrue(epics.isEmpty());
    }

    @Test
    void shouldBeReturnAllSubTasks() {
        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);
        taskManager.createSubTask(secondSubTaskFirstEpic);
        taskManager.createSubTask(thirdSubTaskFirstEpic);

        List<SubTask> subTasks = taskManager.getAllSubTasks();

        assertNotNull(subTasks);
        assertEquals(3, subTasks.size());
        assertTrue(subTasks.contains(firstSubTaskFirstEpic));
        assertTrue(subTasks.contains(secondSubTaskFirstEpic));
        assertTrue(subTasks.contains(thirdSubTaskFirstEpic));
    }

    @Test
    void shouldBeReturnNotEmptyListForAllSubTasks() {
        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);
        taskManager.createSubTask(secondSubTaskFirstEpic);
        taskManager.createSubTask(thirdSubTaskFirstEpic);

        List<SubTask> subTasks = taskManager.getAllSubTasks();

        assertNotNull(subTasks);
        assertFalse(subTasks.isEmpty());
        assertTrue(subTasks.contains(firstSubTaskFirstEpic));
        assertTrue(subTasks.contains(secondSubTaskFirstEpic));
        assertTrue(subTasks.contains(thirdSubTaskFirstEpic));
    }

    @Test
    void shouldBeEmptyListForAllSubTasks() {
        List<SubTask> emptyList = taskManager.getAllSubTasks();

        assertNotNull(emptyList);
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void shouldBeUpdateTask() {
        taskManager.createTask(firstTask);
        Task task = taskManager.getTaskById(firstTask.getId());

        assertNotNull(task);
        assertEquals(firstTask, task);

        Task updatedTask = new Task("updated Task", "Updated description", Status.IN_PROGRESS);
        updatedTask.setId(firstTask.getId());

        Task resultTask = taskManager.updateTask(firstTask.getId(), updatedTask);

        assertNotNull(resultTask);
        assertEquals(updatedTask.getName(), resultTask.getName());
        assertEquals(updatedTask.getDescription(), resultTask.getDescription());
        assertEquals(updatedTask.getStatus(), resultTask.getStatus());
    }

    @Test
    void shouldReturnNullWhenUpdatingTaskInEmptyList() {
        assertTrue(taskManager.getTasks().isEmpty(), "Список задач должен быть пустым перед тестом.");

        Task updatedTask = new Task("Updated Task", "Updated Description", Status.IN_PROGRESS);

        Task result = taskManager.updateTask(1, updatedTask);

        assertNull(result, "Метод updateTask() должен вернуть null при обновлении несуществующей задачи.");

        assertTrue(taskManager.getTasks().isEmpty(), "Список задач должен оставаться пустым.");
    }

    @Test
    void shouldBeNullForUpdateIncorrectTask() {
        taskManager.createTask(firstTask);
        Task task = taskManager.getTaskById(firstTask.getId());

        assertNotNull(task);
        assertEquals(firstTask, task);

        Task updatedTask = new Task("updated Task", "Updated description");

        Task resultTask = taskManager.updateTask(firstTask.getId() + 1, updatedTask);
        assertNull(resultTask);
    }

    @Test
    void shouldBeUpdateEpic() {
        taskManager.createEpic(firstEpic);
        Epic epic = taskManager.getEpicById(firstEpic.getId());

        assertNotNull(epic, "Эпик должен существовать после создания.");
        assertTrue(taskManager.getEpics().contains(epic), "Менеджер должен содержать созданный эпик.");

        Epic updateEpic = new Epic("Updated Epic", "Updated Description");
        updateEpic.setId(epic.getId());

        taskManager.createSubTask(firstSubTaskFirstEpic);
        taskManager.createSubTask(secondSubTaskFirstEpic);

        int subTaskCountBeforeUpdate = taskManager.getAllSubTasks().size();

        Epic resultEpic = taskManager.updateEpic(epic.getId(), updateEpic);

        assertNotNull(resultEpic, "Обновлённый эпик не должен быть null.");
        assertEquals(updateEpic.getId(), resultEpic.getId(), "ID должен совпадать.");
        assertEquals(updateEpic.getName(), resultEpic.getName(), "Название должно совпадать.");
        assertEquals(updateEpic.getDescription(), resultEpic.getDescription(), "Описание должно совпадать.");

        int subTaskCountAfterUpdate = taskManager.getAllSubTasks().size();
        assertEquals(subTaskCountBeforeUpdate, subTaskCountAfterUpdate, "Количество подзадач должно сохраниться.");
    }

    @Test
    void shouldBeReturnNullWhenUpdatingEpicInEmptyList() {
        assertTrue(taskManager.getEpics().isEmpty());

        Epic epic = firstEpic;
        Epic updateEpic = taskManager.updateEpic(1, epic);

        assertNull(updateEpic);
        assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void shouldBeNullForUpdateIncorrectEpicId() {
        taskManager.createEpic(firstEpic);
        Epic epic = taskManager.getEpicById(firstEpic.getId());

        assertNotNull(epic);
        assertEquals(firstEpic, epic);

        Epic updateEpic = new Epic("updateEpic", "desc");
        updateEpic.setId(epic.getId());

        Epic result = taskManager.updateEpic(epic.getId() + 1, updateEpic);
        assertNull(result);
    }

    @Test
    void shouldBeUpdateSubtask() {
        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);
        SubTask subTask = taskManager.getSubTaskById(firstSubTaskFirstEpic.getId(), firstEpic);

        assertTrue(taskManager.getAllSubTasks().contains(subTask));
        assertNotNull(subTask);

        SubTask updateSubTask = new SubTask("u", "d", Status.NEW, firstEpic);
        updateSubTask.setId(subTask.getId());

        SubTask result = taskManager.updateSubTaskById(subTask.getId(), updateSubTask);
        assertNotNull(result);

        assertEquals(updateSubTask.getName(), result.getName());
        assertEquals(updateSubTask.getDescription(), result.getDescription());
        assertEquals(updateSubTask.getStatus(), result.getStatus());

        assertFalse(taskManager.getAllSubTasks().contains(subTask));
        assertTrue(taskManager.getAllSubTasks().contains(result));
    }

    @Test
    void shouldBeReturnNullWhenUpdatingSubtaskInEmptyList() {
        assertTrue(taskManager.getAllSubTasks().isEmpty());

        SubTask subTask = firstSubTaskFirstEpic;
        SubTask updateSubTask = taskManager.updateSubTaskById(subTask.getId(), subTask);

        assertNull(updateSubTask);
        assertTrue(taskManager.getAllSubTasks().isEmpty());
    }

    @Test
    void shouldBeNullForUpdateIncorrectSubTaskId() {
        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);
        SubTask subTask = taskManager.getSubTaskById(firstSubTaskFirstEpic.getId(), firstEpic);

        assertNotNull(subTask);
        assertTrue(taskManager.getAllSubTasks().contains(subTask));

        SubTask update = new SubTask("n", "d", Status.IN_PROGRESS, firstEpic);
        update.setId(subTask.getId());

        SubTask result = taskManager.updateSubTaskById(subTask.getId() + 1, update);

        assertNull(result);
        assertTrue(taskManager.getAllSubTasks().contains(subTask));
    }

    @Test
    void shouldBeDeleteTaskById() {
        taskManager.createTask(firstTask);
        Task task = taskManager.getTaskById(firstTask.getId());

        assertNotNull(task);
        assertTrue(taskManager.getTasks().contains(task));

        taskManager.deleteTaskById(task.getId());

        assertNull(taskManager.getTaskById(task.getId()));
        assertFalse(taskManager.getTasks().contains(task));
    }

    @Test
    void DeleteTaskBtIdWithEmptyList() {
        assertTrue(taskManager.getTasks().isEmpty());
        taskManager.deleteTaskById(1);
        assertTrue(taskManager.getTasks().isEmpty());
        assertNull(taskManager.getTaskById(1));
    }

    @Test
    void deleteTaskWithIncorrectId() {
        taskManager.createTask(firstTask);
        Task task = taskManager.getTaskById(firstTask.getId());

        assertNotNull(task);
        assertTrue(taskManager.getTasks().contains(task));

        taskManager.deleteTaskById(task.getId() + Integer.MAX_VALUE);

        assertTrue(taskManager.getTasks().contains(task));
        assertNotNull(taskManager.getTaskById(task.getId()));
    }

    @Test
    void shouldBeDeleteEpicById() {
        taskManager.createEpic(firstEpic);
        Epic epic = taskManager.getEpicById(firstEpic.getId());

        assertNotNull(epic);
        assertTrue(taskManager.getEpics().contains(epic));

        taskManager.deleteEpicById(epic.getId());

        assertTrue(taskManager.getEpics().isEmpty());
        assertNull(taskManager.getEpicById(epic.getId()));
    }

    @Test
    void deleteEpicWithEmptyEpicList() {
        assertTrue(taskManager.getEpics().isEmpty());
        taskManager.deleteEpicById(1);
        assertTrue(taskManager.getEpics().isEmpty());
        assertNull(taskManager.getEpicById(1));
    }

    @Test
    void shouldNotDeleteEpicWithIncorrectId() {
        taskManager.createEpic(firstEpic);
        Epic epic = taskManager.getEpicById(firstEpic.getId());

        assertNotNull(epic);
        assertTrue(taskManager.getEpics().contains(epic));

        taskManager.deleteEpicById(epic.getId() + 999);

        assertTrue(taskManager.getEpics().contains(epic));
        assertNotNull(taskManager.getEpicById(epic.getId()));
    }

    @Test
    void shouldBeDeleteSubTaskById() {
        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);

        SubTask subTask = taskManager.getSubTaskById(firstSubTaskFirstEpic.getId(), firstEpic);

        assertNotNull(subTask);
        assertTrue(taskManager.getAllSubTasks().contains(subTask));
        assertTrue(firstEpic.getSubTasks().containsKey(subTask.getId()));

        taskManager.deleteSubTaskById(subTask.getId());

        assertTrue(taskManager.getAllSubTasks().isEmpty());
        assertNull(taskManager.getSubTaskById(subTask.getId(), firstEpic));
        assertFalse(firstEpic.getSubTasks().containsKey(subTask.getId()));
    }

    @Test
    void shouldNotDeleteSubtaskWithEmptyList() {
        taskManager.createEpic(firstEpic);
        assertTrue(firstEpic.getSubTasks().isEmpty());
        assertTrue(taskManager.getAllSubTasks().isEmpty());
        taskManager.deleteSubTaskById(1);
        assertTrue(taskManager.getAllSubTasks().isEmpty());
        assertTrue(firstEpic.getSubTasks().isEmpty());
    }

    @Test
    void shouldNotDeleteSubtaskWithIncorrectId() {
        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);

        SubTask subTask = taskManager.getSubTaskById(firstSubTaskFirstEpic.getId(), firstEpic);

        assertNotNull(subTask);
        assertTrue(firstEpic.getSubTasks().containsKey(subTask.getId()));
        assertTrue(taskManager.getAllSubTasks().contains(subTask));

        taskManager.deleteSubTaskById(subTask.getId() + 999);

        assertTrue(firstEpic.getSubTasks().containsKey(subTask.getId()));
        assertTrue(taskManager.getAllSubTasks().contains(subTask));

    }

    @Test
    void shouldDeleteAllTasks() {
        taskManager.createTask(firstTask);
        taskManager.createTask(secondTask);

        Task task = taskManager.getTaskById(firstTask.getId());
        Task task2 = taskManager.getTaskById(secondTask.getId());

        assertNotNull(task);
        assertNotNull(task2);
        assertTrue(taskManager.getTasks().contains(task));
        assertTrue(taskManager.getTasks().contains(task2));
        assertEquals(2, taskManager.getTasks().size());

        taskManager.deleteAllTasks();

        assertNull(taskManager.getTaskById(task.getId()));
        assertNull(taskManager.getTaskById(task2.getId()));

        assertFalse(taskManager.getTasks().contains(task));
        assertFalse(taskManager.getTasks().contains(task2));
    }

    @Test
    void shouldNotFailWhenDeletingFromEmptyTaskList() {
        assertTrue(taskManager.getTasks().isEmpty());
        taskManager.deleteAllTasks();
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void shouldDeleteAllEpics() {
        taskManager.createEpic(firstEpic);
        taskManager.createEpic(secondEpic);

        assertFalse(taskManager.getEpics().isEmpty());
        assertEquals(2, taskManager.getEpics().size());

        taskManager.deleteAllEpics();

        assertTrue(taskManager.getEpics().isEmpty());

        assertNull(taskManager.getTaskById(firstEpic.getId()));
        assertNull(taskManager.getTaskById(secondEpic.getId()));
    }

    @Test
    void shouldNotFailWhenDeletingFromEmptyEpicList() {
        assertTrue(taskManager.getEpics().isEmpty());
        taskManager.deleteAllEpics();
        assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void shouldDeleteAllSubTasks() {
        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);
        taskManager.createSubTask(secondSubTaskFirstEpic);
        taskManager.createSubTask(thirdSubTaskFirstEpic);

        assertFalse(taskManager.getAllSubTasks().isEmpty());
        assertEquals(3, taskManager.getAllSubTasks().size());
        assertEquals(3, firstEpic.getSubTasks().size());

        taskManager.deleteAllSubTasks();

        assertTrue(taskManager.getAllSubTasks().isEmpty());
        assertTrue(firstEpic.getSubTasks().isEmpty());

        assertNull(taskManager.getSubTaskById(firstSubTaskFirstEpic.getId(), firstEpic));
        assertNull(taskManager.getSubTaskById(secondSubTaskFirstEpic.getId(), firstEpic));
        assertNull(taskManager.getSubTaskById(thirdSubTaskFirstEpic.getId(), firstEpic));

        assertEquals(Status.NEW, firstEpic.getStatus());
    }

    @Test
    void shouldNotFailWhenDeletingFromEmptySubTaskList() {
        taskManager.createEpic(firstEpic);
        assertTrue(taskManager.getAllSubTasks().isEmpty());
        taskManager.deleteAllSubTasks();
        assertTrue(taskManager.getAllSubTasks().isEmpty());
    }

    @Test
    void shouldCreateGetHistory() {
        taskManager.createTask(firstTask);
        taskManager.createEpic(firstEpic);
        taskManager.createSubTask(firstSubTaskFirstEpic);

        Task task = taskManager.getTaskById(firstTask.getId());
        Epic epic = taskManager.getEpicById(firstEpic.getId());
        SubTask subTask = taskManager.getSubTaskById(firstSubTaskFirstEpic.getId(), firstEpic);

        assertNotNull(task);
        assertNotNull(epic);
        assertNotNull(subTask);

        List<Task> historyList = taskManager.getHistory();

        assertEquals(3, historyList.size());
        assertTrue(taskManager.getHistory().contains(task));
        assertTrue(taskManager.getHistory().contains(epic));
        assertTrue(taskManager.getHistory().contains(subTask));
    }

    @Test
    void shouldNotFailWhenGetHistoryIsEmpty() {
        List<Task> historyList = taskManager.getHistory();
        assertTrue(historyList.isEmpty());
    }

    @Test
    void shouldNotDuplicateTheSameTasks() {
        taskManager.createTask(firstTask);

        Task task = taskManager.getTaskById(firstTask.getId());
        Task task1 = taskManager.getTaskById(firstTask.getId());

        assertNotNull(task);
        assertNotNull(task1);

        assertSame(task, task1);

        List<Task> historyList = taskManager.getHistory();

        assertEquals(1, historyList.size());
        assertTrue(historyList.contains(task1));
        assertTrue(historyList.contains(task));

    }



    private void createAllTasks() {
        firstTask = new Task("firstTask", "description firstTask", Status.NEW);
        secondTask = new Task("secondTask", "description second task", Status.NEW);

        firstEpic = new Epic("firstEpic", "descriptionFirstEpic");
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

        secondEpic = new Epic("secondEpic", "descriptionSecondEpic");
    }
}
