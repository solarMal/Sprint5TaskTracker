package tasks;

import status.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    HashMap<Integer, SubTask> subTasks;

    public Epic(String name, String description) {
        super(name, description);
        this.subTasks = new HashMap<>();
    }

    public Epic(int id, String name, String description, Status status, HashMap<Integer, SubTask> subTasks) {
        super(id, name, description, status);
        this.subTasks = subTasks;
    }

    public Epic() {
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
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
