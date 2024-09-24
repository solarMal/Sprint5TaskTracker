package tasksmanager;

import tasks.Epic;
import tasks.SubTask;
import tasksinterface.EpicInterface;
import tasksinterface.SubTaskInterface;

import java.util.HashMap;

public class InMemoryEpicManager implements EpicInterface, SubTaskInterface {
    int dynamicEpicId = 1;
    int dynamicSubTaskId = 1;

    private static final HashMap<Integer, Epic> epics = new HashMap<>();

    @Override
    public void createEpic(Epic epic) {
        if (epic != null) {
            epic.setId(dynamicEpicId++);
            epic.setStatus("NEW");
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Эпик не создан");
        }
    }

    @Override
    public void getEpics() {
        if (epics.isEmpty()) {
            System.out.println("нет активных эпиков");
        } else {
            for (Integer epicId : epics.keySet()) {
                Epic epic = epics.get(epicId);
                System.out.println("Эпическая задача № " + epicId + " " + epic);

                for (Integer subTaskId : epic.getSubTasks().keySet()) {
                    SubTask subTask = epic.getSubTasks().get(subTaskId);
                    System.out.println("Подзадача № " + subTaskId + " " + subTask);
                }
                System.out.println();
            }
        }
    }

    @Override
    public void deleteAllEpics() {
        if (!epics.isEmpty()) {
            epics.clear();
            System.out.println("все эпики удалены");
        } else {
            System.out.println("нет активных эпиков");
        }
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = null;
        if (epics.containsKey(id)) {
            epic = epics.get(id);
        } else {
            System.out.println("Эпик с id " + id + " не существует");
        }
        return epic;
    }

    @Override
    public void updateEpic(int id, Epic epic) {
        if (epic != null && epics.containsKey(id)) {
            epic.setId(id);
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Эпик с id " + id + " не существует, его нельзя обновить");
        }
    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
            System.out.println("Эпик с id № " + id + " успешно удалён");
        } else {
            System.out.println("Эпик с id № " + id + " нельзя удалить, он не существует");
        }
    }

    @Override
    public void createSubTask(SubTask subTask) {
        if (subTask != null && epics.containsKey(subTask.getEpic().getId())) {
            Epic epic = epics.get(subTask.getEpic().getId());

            if (epic.getSubTasks().isEmpty()) {
                dynamicSubTaskId = 1;
            }

            epic.getSubTasks().put(dynamicSubTaskId++, subTask);

            updateEpicStatus(epic);
        } else {
            System.out.println("Эпик не существует");
        }
    }

    @Override
    public void getAllSubTasks(Epic epic) {
        if (!epic.getSubTasks().isEmpty()) {
            for (Integer i : epic.getSubTasks().keySet()) {
                SubTask subTask = epic.getSubTasks().get(i);
                System.out.println(subTask);
            }
        } else {
            System.out.println(epic + " не имеет подзадач");
        }

    }

    @Override
    public void deleteAllSubTasks(Epic epic) {
        if (!epic.getSubTasks().isEmpty()) {
            epic.getSubTasks().clear();
            epic.setStatus("NEW");
            System.out.println("подзадачи успешно удалены");
        } else {
            System.out.println(epic + " не имеет подзадач");
        }
    }

    @Override
    public void deleteSubTaskById(int id, Epic epic) {
        if (!epic.getSubTasks().isEmpty() && epic.getSubTasks().containsKey(id)) {
            epic.getSubTasks().remove(id);
            System.out.println("подзадача с id " + id + " удалена");
            updateEpicStatus(epic);
        } else {
            System.out.println("ошибка удаления. Подзадачи с id " + id + " не существует");
        }
    }

    @Override
    public SubTask getSubTaskById(int id, Epic epic) {
        SubTask subTask = null;
        if (epic.getSubTasks().containsKey(id)) {
            subTask = epic.getSubTasks().get(id);
        } else {
            System.out.println(epic + " не имеет подзадачи с id " + id);
        }
        return subTask;
    }

    @Override
    public void updateSubTaskById(int id, SubTask subTask) {
        if (epics.containsKey(subTask.getEpic().getId())) {
            Epic epic = epics.get(subTask.getEpic().getId());

            if (epic.getSubTasks().containsKey(id)) {
                subTask.setId(id);
                epic.getSubTasks().put(subTask.getId(), subTask);
            }

            updateEpicStatus(epic);
        } else {
            System.out.println("ошибка обновления эпика");
        }
    }

    private void updateEpicStatus(Epic epic) {
        int newCounter = 0;
        int doneCounter = 0;

        for (SubTask sub : epic.getSubTasks().values()) {
            if (sub.getStatus().equals("NEW")) {
                newCounter++;
            } else if (sub.getStatus().equals("DONE")) {
                doneCounter++;
            }
        }

        if (newCounter == epic.getSubTasks().size()) {
            epic.setStatus("NEW");
        } else if (doneCounter == epic.getSubTasks().size()) {
            epic.setStatus("DONE");
        } else {
            epic.setStatus("IN_PROGRESS");
        }
    }

}
