package tasksmanager;

import tasks.Epic;
import tasks.SubTask;

import java.util.HashMap;

public class EpicManager {
    int dynamicEpicId = 0;
    int dynamicSubTaskId = 0;

    HashMap<Integer, Epic> epics = new HashMap<>();

    public void createEpic(Epic epic) {
        if (epic != null) {
            epic.setId(dynamicEpicId++);
            epic.setStatus("NEW");
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Эпик не создан");
        }
    }

    public void getEpics() {
        if (epics.isEmpty()) {
            System.out.println("нет активных эпиков");
        } else {
            for (Integer epicId : epics.keySet()) {
                Epic epic = epics.get(epicId);
                System.out.println("Эпическая задача № " + (epicId + 1) + " " + epic);

                for (Integer subTaskId : epic.getSubTasks().keySet()) {
                    SubTask subTask = epic.getSubTasks().get(subTaskId);
                    System.out.println("Подзадача № " + (subTaskId + 1) + " " + subTask);
                }
                System.out.println();
            }
        }
    }

    public void deleteAllEpics() {
        if (!epics.isEmpty()) {
            epics.clear();
            System.out.println("все эпики удалены)))");
        } else {
            System.out.println("нет активных эпиков");
        }
    }

    public void getEpicById(int id) {
        id-=1;
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            System.out.println("Эпик № " + (id + 1) + " " + epic);

            for (Integer subTaskId : epic.getSubTasks().keySet()) {
                SubTask subTask = epic.getSubTasks().get(subTaskId);
                System.out.println("Подзадача № " + (subTaskId + 1) + " " + subTask);
            }
        } else {
            System.out.println("Эпик с id " + (id + 1) + " не существует");
        }
    }

    public void updateEpic(int id, Epic epic) {
        id-=1;
        if (epic != null && epics.containsKey(id)) {
            epic.setId(id);
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Эпик с id " + id + " не существует, его нельзя обновить");
        }
    }

    public void deleteEpicById(int id) {
        id-=1;
        if (epics.containsKey(id)) {
            epics.remove(id);
            System.out.println("Эпик с id № " + (id + 1) + " успешно удалён");
        } else {
            System.out.println("Эпик с id № " + (id + 1) + " нельзя удалить, он не существует");
        }
    }

    public void createSubTask(SubTask subTask) {
        if (subTask != null && epics.containsKey(subTask.getEpic().getId())) {
            Epic epic = epics.get(subTask.getEpic().getId());

            if (epic.getSubTasks().isEmpty()) {
                dynamicSubTaskId = 0;
            }

            epic.getSubTasks().put(dynamicSubTaskId++, subTask);

            updateEpicStatus(epic);
        } else {
            System.out.println("Эпик не существует");
        }
    }

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

    public void deleteAllSubTasks(Epic epic) {
        if (!epic.getSubTasks().isEmpty()) {
            epic.getSubTasks().clear();
            epic.setStatus("NEW");
            System.out.println("подзадачи успешно удалены");
        } else {
            System.out.println(epic + " не имеет подзадач");
        }
    }

    public void getSubTaskById(int id, Epic epic) {
        id -= 1;
        if (epic.getSubTasks().containsKey(id)) {
            System.out.println(epic.getSubTasks().get(id));
        } else {
            System.out.println(epic + " не имеет подзадачи с id " + (id + 1));
        }
    }

    public void updateSubTaskById(int id, SubTask subTask) {
        id -= 1;
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
