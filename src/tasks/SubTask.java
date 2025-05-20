package tasks;


import status.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class SubTask extends Task {
    Epic epic;

    public SubTask(String name, String description, Status status, Epic epic) {
        super(name, description, status);
        setEndTimeIfSubTaskCreateWithDoneStatus();
        this.epic = epic;
    }

    public SubTask(int id, String name, String description, Status status, Epic epic) {
        super(id, name, description, status);
        setEndTimeIfSubTaskCreateWithDoneStatus();
        this.epic = epic;
    }

    public SubTask() {
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public void setStatus(Status status) {
        if (this.status != Status.DONE && status == Status.DONE) {
            endTime = LocalDateTime.now();
        }
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
    public void getStartTime() {
        System.out.println("начало подзадачи: " + startTime);
    }

    @Override
    public Optional<Long> getEndTime() {
        if (status == Status.DONE && startTime != null && endTime != null) {
            return Optional.of(Duration.between(startTime, endTime).getSeconds());
        }
        return Optional.empty();
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

    private void setEndTimeIfSubTaskCreateWithDoneStatus() {
        if (this.status == Status.DONE) {
            endTime = LocalDateTime.now();
        }
    }
}
