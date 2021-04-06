package com.example.SpringProjectsManagement.service;

import com.example.SpringProjectsManagement.controller.TaskController;
import com.example.SpringProjectsManagement.model.Project;
import com.example.SpringProjectsManagement.model.Task;
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

import java.util.HashMap;
import java.util.Map;

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

    @MockBean
    private TaskService taskService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        task1 = new Task(0, "Create some classes", false);
        task2 = new Task(1, "Design components", false);
    }

/*    @BeforeAll
    public static void PopulateDataForTests(){

    }

    @AfterAll
    public static void clearDataForTests(){
        clearDataForAddProjectTest();
    }*/

    @Test
    public void addTask() throws Exception {
        when(taskService.addTask(any(Task.class))).thenReturn(task1);

        mockMvc.perform(post("/tasks")
        .content(objectMapper.writeValueAsString(task1))
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(task1.getContent()));
    }

    @Test
    public void updateTask() throws Exception {
        Task task3 = new Task(3, "Create some animations", false);
        task3.setId(22);
        when(taskService.updateTask(any(Task.class))).thenReturn(task3);

        mockMvc.perform(put("/task/{id}", 22)
                .content(objectMapper.writeValueAsString(task3))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Create some animations"))
                .andExpect(jsonPath("$.id").value("22"))
                .andExpect(jsonPath("$.checked").value(false));
    }

    @Test
    public void getTaskById() throws Exception {
        task1.setId(3);
        when(taskService.getTaskById(any(long.class))).thenReturn(task1);

        mockMvc.perform(get("/tasks/{id}", 3)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    public void deleteTask() throws Exception {
        task1.setId(4);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        when(taskService.deleteTask(any(long.class))).thenReturn(response);
        mockMvc.perform(delete("/tasks/{id}", 4)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(true));
    }
}