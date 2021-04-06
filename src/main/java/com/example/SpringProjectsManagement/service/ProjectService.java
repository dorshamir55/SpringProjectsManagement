package com.example.SpringProjectsManagement.service;

import com.example.SpringProjectsManagement.exception.ResourceNotFoundException;
import com.example.SpringProjectsManagement.model.Project;
import com.example.SpringProjectsManagement.repository.IProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    public IProjectRepository repository;

    @Override
    public Project addProject(Project project) {
        return repository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        Optional<Project> projectFromDb = repository.findById(project.getId());

        if(projectFromDb.isPresent()) {
            Project updatedProject = projectFromDb.get();

            updatedProject.setName(project.getName());
            repository.save(updatedProject);

            return updatedProject;
        } else {
            throw new ResourceNotFoundException(project.getId());
        }
    }

    @Override
    public List<Project> getAllProjects() {
        return repository.findAll();
    }

    @Override
    public Project getProjectById(long id) {
        Optional<Project> projectFromDb = repository.findById(id);

        if(projectFromDb.isPresent()) {
            return projectFromDb.get();
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public Map<String, Boolean> deleteProject(long id) {
        Optional<Project> projectFromDb = repository.findById(id);

        if(projectFromDb.isPresent()) {
            repository.delete(projectFromDb.get());
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", true);
            return response;
        } else {
            throw new ResourceNotFoundException(id);
        }
    }
}
