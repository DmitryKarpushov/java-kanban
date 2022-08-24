package service;

import Manager.HistoryManager;
import Task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    public static class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "task=" + task +
                    ", prev=" + (prev == null ? null : prev.task) +
                    ", next=" + (next == null ? null : next.task) +
                    '}';
        }
    }

    private Map<Integer, Node> nodeMap = new HashMap<>();
    private Node first;
    private Node last;

    private List<Task> getTask() {
        List<Task> tasks = new ArrayList<>();
        Node node = first;
        while (node != null) {
            tasks.add(node.task);
            node = node.next;
        }
        return tasks;
    }

    private void linkLast(Task task) {
        final Node node = new Node(task, last, null);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
    }

    @Override
    public List<Task> getHistory() {
        return getTask();
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        int id = task.getId();
        removeNode(id);
        linkLast(task);
        nodeMap.put(id, last);
    }

    private void removeNode(int id) {
        Node node = nodeMap.remove(id);
        if (node == null) {
            return;
        } else if (node.prev != null) {
            node.prev.next = node.next;
            if (node.next == null) {
                last = node.prev;
            } else {
                node.next.prev = node.prev;
            }
        } else {
            first = node.next;
            if (first == null) {
                last = null;
            } else {
                first.prev = null;
            }
        }
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "nodeMap=" + nodeMap +
                ", first=" + first +
                ", last=" + last +
                '}';
    }
}
