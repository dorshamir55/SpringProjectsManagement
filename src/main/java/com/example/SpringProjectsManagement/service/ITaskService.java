package com.example.SpringProjectsManagement.service;

import com.example.SpringProjectsManagement.model.Task;

import java.util.List;
import java.util.Map;

public interface ITaskService {
    Task addTask(Task task);
    Task updateTask(Task task);
    List<Task> getTasksByProjectId(long projectId);
    Task getTaskById(long id);
    Map<String, Boolean> deleteTask(long id);
}
