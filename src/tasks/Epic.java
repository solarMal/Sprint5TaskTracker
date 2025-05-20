package tasks;

import status.Status;

import java.time.LocalDateTime;
import java.util.*;

public class Epic extends Task {
    HashMap<Integer, SubTask> subTasks = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description, Status status, HashMap<Integer, SubTask> subTasks) {
        super(id, name, description, status);
    }

    public Epic() {
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
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
        return new ArrayList<>(sb.keySet());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Epic{");

        sb.append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", description='").append(description).append('\'')
                .append(", status=").append(status);

        if (subTasks != null && !subTasks.isEmpty()) {
            sb.append(", subTasks=").append(getSubtaskId(subTasks));
        }

        sb.append('}');
        return sb.toString();
    }

}
