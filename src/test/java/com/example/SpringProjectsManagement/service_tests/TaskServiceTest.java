package com.example.SpringProjectsManagement.service_tests;

import com.example.SpringProjectsManagement.model.Project;
import com.example.SpringProjectsManagement.model.Task;
import com.example.SpringProjectsManagement.repository.IProjectRepository;
import com.example.SpringProjectsManagement.repository.ITaskRepository;
import com.example.SpringProjectsManagement.service.ProjectService;
import com.example.SpringProjectsManagement.service.TaskService;
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
public class TaskServiceTest {

    private Task task1;
    private Task task2;
    private List<Task> tasks = new ArrayList<>();

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ITaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    @Before
    public void setup() {
        task1 = new Task(1, "Create some classes", false);
        task2 = new Task(2, "Design components", false);
    }

    @Test
    public void addTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task1);
        
        Task created = taskService.addTask(task1);

        assertThat(created.getContent()).isSameAs(task1.getContent());
        assertThat(created.getProjectId()).isSameAs(task1.getProjectId());
        assertThat(created.getChecked()).isSameAs(task1.getChecked());
    }

    @Test
    public void updateTask() {
        Task task3 = new Task(3, "Create some animations", false);
        task3.setId(2);
        task2.setId(2);
        taskService.addTask(task2);

        when(taskRepository.findById(task2.getId())).thenReturn(java.util.Optional.ofNullable(task2));
        Task updated = taskService.updateTask(task3);

        assertThat(updated.getContent()).isSameAs(task2.getContent());
        assertThat(updated.getProjectId()).isSameAs(task2.getProjectId());
        assertThat(updated.getChecked()).isSameAs(task2.getChecked());
        assertThat(updated.getId()).isSameAs(task2.getId());
    }

//    @Test
//    public void getAllProjects() {
//        projects.add(project1);
//        projects.add(project2);
//        when(projectRepository.findAll()).thenReturn(projects);
//
//        List<Project> projectsFound = projectService.getAllProjects();
//
//        assertThat(projectsFound).isSameAs(projects);
//    }

    @Test
    public void getTaskById() {
        task1.setId(7);
        when(taskRepository.findById(any(long.class))).thenReturn(java.util.Optional.ofNullable(task1));

        Task taskFound = taskService.getTaskById(task1.getId());

        assertThat(taskFound).isSameAs(task1);
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
        task1.setId(6);
        Map<String, Boolean> response;

        when(taskRepository.findById(task1.getId())).thenReturn(java.util.Optional.ofNullable(task1));
        response = taskService.deleteTask(task1.getId());
        assertTrue(response.get("deleted"));
    }
}