package com.example.todolist.list;

import com.sun.net.httpserver.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TaskItemController {

    private final TaskItemService taskItemService;

    @Autowired
    public TaskItemController(TaskItemService taskItemService) {
        this.taskItemService = taskItemService;
    }

    @GetMapping("/tasks")
    public List<TaskItem> getAllTasks() {
        return taskItemService.getAllTasks();
    }

    @GetMapping("/task/{id}")
    public TaskItem getTaskById(@PathVariable("id") Long id) {
        return taskItemService.getTaskById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addTask(@RequestBody TaskItem taskItem) {
        HttpHeaders headers = new HttpHeaders();
        Response success = new Response(200, "Success");

        taskItemService.addTask(taskItem);

        return new ResponseEntity<Response>(success, headers, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateTask(@PathVariable("id") Long id, @RequestBody Map<String, String> json) {
        HttpHeaders headers = new HttpHeaders();
        Response success = new Response(200, "Success");

        taskItemService.updateTask(id, json.get("subject"), json.get("body"), json.get("status"));

        return new ResponseEntity<Response>(success, headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteTask(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        Response success = new Response(200, "Success");

        taskItemService.deleteTask(id);

        return new ResponseEntity<Response>(success, headers, HttpStatus.OK);
    }

}
