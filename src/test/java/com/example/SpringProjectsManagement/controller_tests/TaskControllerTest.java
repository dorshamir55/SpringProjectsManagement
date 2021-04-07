package com.example.SpringProjectsManagement.controller_tests;

import com.example.SpringProjectsManagement.controller.TaskController;
import com.example.SpringProjectsManagement.model.Project;
import com.example.SpringProjectsManagement.model.Task;
import com.example.SpringProjectsManagement.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
@ContextConfiguration(classes = { TaskController.class, TaskService.class })
public class TaskControllerTest {

    private Task task1;
    private Task task2;
    private List<Task> tasks = new ArrayList<>();

    @MockBean
    private TaskService taskService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        task1 = new Task(1, "Create some classes", false);
        task2 = new Task(2, "Design components", false);
    }

    @Test
    public void addTask() throws Exception {
        when(taskService.addTask(any(Task.class))).thenReturn(task1);

        mockMvc.perform(post("/tasks")
        .content(objectMapper.writeValueAsString(task1))
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Create some classes"))
                .andExpect(jsonPath("$.projectId").value("1"))
                .andExpect(jsonPath("$.checked").value(false));
    }

    @Test
    public void updateTask() throws Exception {
        Task task3 = new Task(3, "Create some animations", false);
        task3.setId(22);
        when(taskService.updateTask(any(Task.class))).thenReturn(task3);

        mockMvc.perform(put("/tasks/{id}", 22)
                .content(objectMapper.writeValueAsString(task3))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Create some animations"))
                .andExpect(jsonPath("$.checked").value(false))
                .andExpect(jsonPath("$.projectId").value("3"))
                .andExpect(jsonPath("$.id").value("22"));
    }

    @Test
    public void getTasksByProjectId() throws Exception {
        task1.setProjectId(5);
        task2.setProjectId(5);
        tasks.add(task1);
        tasks.add(task2);
        when(taskService.getTasksByProjectId(5)).thenReturn(tasks);

        mockMvc.perform(get("/projects/{id}/tasks", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].projectId").value("5"));
    }

    @Test
    public void getTaskById() throws Exception {
        task1.setId(3);
        when(taskService.getTaskById(any(long.class))).thenReturn(task1);

        mockMvc.perform(get("/tasks/{id}", 3)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"));
    }

    @Test
    public void deleteTask() throws Exception {
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        when(taskService.deleteTask(any(long.class))).thenReturn(response);
        mockMvc.perform(delete("/tasks/{id}", any(long.class))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(true));
    }
}