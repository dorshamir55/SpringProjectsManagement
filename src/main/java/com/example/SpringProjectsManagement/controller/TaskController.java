package com.example.SpringProjectsManagement.controller;

import com.example.SpringProjectsManagement.model.Task;
import com.example.SpringProjectsManagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    TaskService service;

    @GetMapping("/tasksOfProject/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProjectId(@PathVariable long projectId) {
        return ResponseEntity.ok().body(service.getTasksByProjectId(projectId));
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable long taskId) {
        return ResponseEntity.ok().body(service.getTaskById(taskId));
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        return ResponseEntity.ok().body(service.addTask(task));
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable long taskId, @RequestBody Task task) {
        task.setId(taskId);
        return ResponseEntity.ok().body(service.updateTask(task));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Map<String, Boolean>> deleteTask(@PathVariable long taskId) {
        service.deleteTask(taskId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return ResponseEntity.ok(response);
    }
}
