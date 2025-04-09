package tasks;

import status.Status;

import java.time.LocalDateTime;
import java.util.*;

public class Epic extends Task {
    HashMap<Integer, SubTask> subTasks;
    LocalDateTime startTime;

    public Epic(String name, String description) {
        super(name, description);
        this.startTime = LocalDateTime.now();
        this.subTasks = new HashMap<>();
    }

    public Epic(int id, String name, String description, Status status, HashMap<Integer, SubTask> subTasks) {
        super(id, name, description, status);
        this.startTime = LocalDateTime.now();
        this.subTasks = subTasks;
    }

    public Epic() {
        this.startTime = LocalDateTime.now();
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.startTime = LocalDateTime.now();
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
        this.startTime = LocalDateTime.now();
    }


    @Override
    public Optional<Long> getEndTime() {
        if (this.status == Status.DONE) {
            return subTasks.values().stream()
                    .map(SubTask::getEndTime)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .reduce(Long::sum);
        }
        return Optional.empty();
    }


    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasks, epic.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }

    private List<Integer> getSubtaskId(HashMap<Integer, SubTask> sb) {
        List<Integer> result = new ArrayList<>();
        result.addAll(subTasks.keySet());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Epic{");

        sb.append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", description='").append(description).append('\'')
                .append(", status=").append(status)
                .append('}');

        if (!subTasks.isEmpty()) {
            sb.append("subTasks=").append(getSubtaskId(subTasks)).append(", ");
        }

        return sb.toString();
    }

}
