package com.example.todolist.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class TaskItemService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskItemService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskItem> getAllTasks() {
        return taskRepository.findAll();
    }

    public TaskItem getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Task Item with id = " + id + " does not exist"));
    }

    public void addTask(TaskItem item) {
        taskRepository.save(item);
    }

    public boolean deleteTask(Long id) {
        boolean exists = taskRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Task with ID " + id + " does not exist");
        }
        taskRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void updateTask(Long id, String title, String body, String status) {
        TaskItem item = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Task with ID " + id + " does not exist"));

        if (title != null &&
            title.length() > 0 &&
            !Objects.equals(item.getSubject(), title)) {
            item.setSubject(title);
        }

        if (body != null && !Objects.equals(item.getBody(), body)) {
            item.setBody(body);
        }

        if (status != null &&
            !Objects.equals(item.getStatus().toString(), status)) {
            TaskStatus statusEnum = TaskStatus.valueOf(status);
            item.setStatus(statusEnum);
        }
    }

}
