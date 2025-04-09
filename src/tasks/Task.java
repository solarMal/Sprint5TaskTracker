package tasks;

import status.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class Task {
    int id;
    String name;
    String description;
    Status status;
    LocalDateTime startTime;
    LocalDateTime endTime;

    public Task() {
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        setEndTimeIfTaskCreateWithDoneStatus();
        this.startTime = LocalDateTime.now();
    }

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        setEndTimeIfTaskCreateWithDoneStatus();
        this.startTime = LocalDateTime.now();
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.startTime = LocalDateTime.now();
    }

    public Optional<Long> getEndTime() {
        if (status == Status.DONE && startTime != null && endTime != null) {
            return Optional.of(Duration.between(startTime, endTime).getSeconds());
        }
        return Optional.empty();
    }

    public void getStartTime() {
        System.out.println("начало задачи: " + startTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (this.status != Status.DONE && status == Status.DONE) {
            endTime = LocalDateTime.now();
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status);
    }

    private void setEndTimeIfTaskCreateWithDoneStatus() {
        if (this.status == Status.DONE) {
            endTime = LocalDateTime.now();
        }
    }
}
