package com.example.SpringProjectsManagement.controller_tests;

import com.example.SpringProjectsManagement.controller.ProjectController;
import com.example.SpringProjectsManagement.model.Project;
import com.example.SpringProjectsManagement.service.ProjectService;
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
@WebMvcTest(ProjectController.class)
@ContextConfiguration(classes = { ProjectController.class, ProjectService.class })
public class ProjectControllerTest {

    private Project project1;
    private Project project2;
    private List<Project> projects = new ArrayList<>();

    @MockBean
    private ProjectService projectService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        project1 = new Project("ZaramT");
        project2 = new Project("inventory-management");
    }

    @Test
    public void addProject() throws Exception {
        when(projectService.addProject(any(Project.class))).thenReturn(project1);

        mockMvc.perform(post("/projects")
        .content(objectMapper.writeValueAsString(project1))
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ZaramT"));
    }

    @Test
    public void testUpdateProjectInProjectController() throws Exception {
        Project project3 = new Project("Checkers");
        project3.setId(2);
        when(projectService.updateProject(any(Project.class))).thenReturn(project3);

        mockMvc.perform(put("/projects/{id}", 2)
                .content(objectMapper.writeValueAsString(project3))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Checkers"))
                .andExpect(jsonPath("$.id").value("2"));
    }

    @Test
    public void testGetAllProjectsInProjectController() throws Exception {
        projects.add(project1);
        projects.add(project2);
        when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/projects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetProjectByIdInProjectController() throws Exception {
        project1.setId(2);
        when(projectService.getProjectById(any(long.class))).thenReturn(project1);

        mockMvc.perform(get("/projects/{id}", 2)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"));
    }

    @Test
    public void testDeleteProjectInProjectController() throws Exception {
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        when(projectService.deleteProject(any(long.class))).thenReturn(response);
        mockMvc.perform(delete("/projects/{id}", any(long.class))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(true));
    }
}