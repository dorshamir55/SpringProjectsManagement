package com.example.SpringProjectsManagement.service;

import com.example.SpringProjectsManagement.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IProjectService {
    Project addProject(Project project);
    Project updateProject(Project project);
    List<Project> getAllProjects();
    Project getProjectById(long id);
    Map<String, Boolean> deleteProject(long id);
}
