package com.example.SpringProjectsManagement.service;

import com.example.SpringProjectsManagement.exception.ResourceNotFoundException;
import com.example.SpringProjectsManagement.model.Task;
import com.example.SpringProjectsManagement.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService implements ITaskService {

    @Autowired
    public ITaskRepository repository;

    @Override
    public Task addTask(Task task) {
        return repository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        Optional<Task> taskFromDb = repository.findById(task.getId());

        if(taskFromDb.isPresent()) {
            Task updatedTask = taskFromDb.get();

            updatedTask.setContent(task.getContent());
            updatedTask.setChecked(task.getChecked());
            repository.save(updatedTask);

            return updatedTask;
        } else {
            throw new ResourceNotFoundException(task.getId());
        }
    }

    @Override
    public List<Task> getTasksByProjectId(long projectId) {
        return repository.findByProjectId(projectId);
    }

    @Override
    public Task getTaskById(long id) {
        Optional<Task> taskFromDb = repository.findById(id);

        if(taskFromDb.isPresent()) {
            return taskFromDb.get();
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public Map<String, Boolean> deleteTask(long id) {
        Optional<Task> taskFromDb = repository.findById(id);

        if(taskFromDb.isPresent()) {
            repository.delete(taskFromDb.get());
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", true);
            return response;
        } else {
            throw new ResourceNotFoundException(id);
        }
    }
}
