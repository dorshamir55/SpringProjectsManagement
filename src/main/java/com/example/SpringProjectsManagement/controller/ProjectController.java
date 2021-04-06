package com.example.SpringProjectsManagement.controller;

import com.example.SpringProjectsManagement.model.Project;
import com.example.SpringProjectsManagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    ProjectService service;

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok().body(service.getAllProjects());
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable long projectId) {
        return ResponseEntity.ok().body(service.getProjectById(projectId));
    }

    @PostMapping("/projects")
    public ResponseEntity<Project> addProject(@RequestBody Project project) {
        return ResponseEntity.ok().body(service.addProject(project));
    }

    @PutMapping("/projects/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable long projectId,@RequestBody Project project) {
        project.setId(projectId);
        return ResponseEntity.ok().body(service.updateProject(project));
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Map<String, Boolean>> deleteProject(@PathVariable long projectId) {
        service.deleteProject(projectId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return ResponseEntity.ok(response);
    }
}
