package com.example.SpringProjectsManagement.service_tests;

import com.example.SpringProjectsManagement.model.Project;
import com.example.SpringProjectsManagement.repository.IProjectRepository;
import com.example.SpringProjectsManagement.service.ProjectService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { IProjectRepository.class, ProjectService.class })
public class ProjectServiceTest {

    private Project project1;
    private Project project2;
    private List<Project> projects = new ArrayList<>();

//    @Rule
//    public final ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IProjectRepository projectRepository;

    @InjectMocks
    ProjectService projectService;

    @Before
    public void setup() {
        project1 = new Project("ZaramT");
        project2 = new Project("inventory-management");
    }

    @Test
    public void addProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(project1);
        
        Project created = projectService.addProject(project1);

        assertThat(created.getName()).isSameAs(project1.getName());
    }

    @Test
    public void updateProject() {
        Project project3 = new Project("Checkers");
        project3.setId(2);
        project2.setId(2);
        projectService.addProject(project2);

        when(projectRepository.findById(project2.getId())).thenReturn(java.util.Optional.ofNullable(project2));
        Project updated = projectService.updateProject(project3);

        assertThat(updated.getName()).isSameAs(project2.getName());
        assertThat(updated.getId()).isSameAs(project2.getId());
    }

    @Test
    public void getAllProjects() {
        projects.add(project1);
        projects.add(project2);
        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> projectsFound = projectService.getAllProjects();

        assertThat(projectsFound).isSameAs(projects);
    }

    @Test
    public void getProjectById() {
        project1.setId(2);
        when(projectRepository.findById(any(long.class))).thenReturn(java.util.Optional.ofNullable(project1));

        Project projectFound = projectService.getProjectById(project1.getId());

        assertThat(projectFound).isSameAs(project1);
    }

//    @Test(expected = ResourceNotFoundException.class)
//    public void getProjectByIdAndException() throws Throwable {
//        project1.setId(2);
//        when(projectRepository.findById(any(long.class))).thenReturn(java.util.Optional.ofNullable(project1));
//
//        projectService.getProjectById(project1.getId());
//        assertFalse(throw new ResourceNotFoundException(project1.getId());
//    }

    @Test
    public void deleteProject() {
        project1.setId(5);
        Map<String, Boolean> response;

        when(projectRepository.findById(project1.getId())).thenReturn(java.util.Optional.ofNullable(project1));
        response = projectService.deleteProject(project1.getId());
        assertTrue(response.get("deleted"));
    }
}