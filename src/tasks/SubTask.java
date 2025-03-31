package tasks;


import status.Status;

import java.util.Objects;

public class SubTask extends Task {
    Epic epic;

    public SubTask(String name, String description, Status status, Epic epic) {
        super(name, description, status);
        this.epic = epic;
    }

    public SubTask(int id, String name, String description, Status status, Epic epic) {
        super(id, name, description, status);
        this.epic = epic;
    }

    public SubTask() {
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
        int newCounter = 0;
        int doneCounter = 0;

        for (SubTask sub : epic.getSubTasks().values()) {
            if (sub.getStatus().equals(Status.NEW)) {
                newCounter++;
            } else if (sub.getStatus().equals(Status.DONE)) {
                doneCounter++;
            }
        }

        if (newCounter == epic.getSubTasks().size()) {
            epic.setStatus(Status.NEW);
        } else if (doneCounter == epic.getSubTasks().size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(epic, subTask.epic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epic);
    }

    @Override
    public String toString() {
        return "SubTask{"  +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", epic=" + epic.getId() +
                '}';
    }
}
