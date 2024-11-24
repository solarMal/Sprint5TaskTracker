package inmemory;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomLinkedList {
    private final Map<Integer, Node> integerNodeMap;
    private Node first;
    private Node last;

    public CustomLinkedList() {
        integerNodeMap = new HashMap<>();
    }

    public void remote(int id) {
        if (integerNodeMap.containsKey(id)) {
            Node currentNode = integerNodeMap.get(id);
            Node prevNode = currentNode.prev;
            Node nextNode = currentNode.next;
            setPrevNodeWithConsists(prevNode, nextNode);
            setNextNodeWithConsists(prevNode, nextNode);
            integerNodeMap.remove(id);
        }
    }

    void linkLast(Task task) {
        Node existingNode = integerNodeMap.get(task.getId());

        if (existingNode != null) {
            removeNode(existingNode);
        }
        Node newNode = new Node(task, last, null);

        if (last != null) {
            last.next = newNode;
        } else {
            first = newNode;
        }

        last = newNode;
        integerNodeMap.put(task.getId(), newNode);
    }

    List<Task> getTasks() {
        List<Task> result = new ArrayList<>();
        Node currentNode = first;
        while (currentNode != null) {
            result.add(currentNode.task);
            currentNode = currentNode.next;
        }

        return List.copyOf(result);
    }

    private void removeNode(Node node) {
        if (integerNodeMap.size() == 1 && integerNodeMap.containsKey(node.task.getId())) {
            return;
        }

        setPrevNodeWithConsists(node.prev, node.next);
        setNextNodeWithConsists(node.prev, node.next);
        integerNodeMap.remove(node.task.getId());
    }

    private void setNextNodeWithConsists(Node prevNode, Node nextNode) {
        if (nextNode != null) {
            if(prevNode != null) {
                nextNode.prev = prevNode;
            } else {
                nextNode.prev = null;
                first = nextNode;
            }
        }

        if (integerNodeMap.size() == 1) {
            first = nextNode;
        }

    }

    private void setPrevNodeWithConsists(Node prevNode, Node nextNode) {
        if (prevNode != null) {
            if(nextNode != null) {
                prevNode.next = nextNode;
            } else {
                prevNode.next = null;
                last = prevNode;
            }
        }
        if (integerNodeMap.size() == 1) {
            last = prevNode;
        }
    }

    private static class Node {
        private final Task task;
        private Node prev;
        private Node next;

        public Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }
}
