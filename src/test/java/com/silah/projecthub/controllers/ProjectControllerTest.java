package com.silah.projecthub.controllers;

import com.silah.projecthub.entities.Project;
import com.silah.projecthub.exceptions.InvalidProjectException;
import com.silah.projecthub.exceptions.ProjectNotFoundException;
import com.silah.projecthub.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProjectControllerTest {
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projectController = new ProjectController(projectService);
    }

    @Test
    void createProject_withValidProject_shouldReturnCreated() throws InvalidProjectException, URISyntaxException {
        Project project = new Project();
        project.setId(1L);
        project.setName("Project 1");
        project.setDescription("Description of project 1");

        when(projectService.createProject(any(Project.class))).thenReturn(1L);

        ResponseEntity<Long> response = projectController.createProject(project);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(new URI("/api/v1/projects/1"), response.getHeaders().getLocation());
        verify(projectService, times(1)).createProject(project);
    }

    @Test
    void createProject_withInvalidProject_shouldThrowInvalidProjectException() throws InvalidProjectException {
        Project project = new Project();
        project.setName(null);

        when(projectService.createProject(project)).thenThrow(new InvalidProjectException("Invalid project"));

        assertThrows(InvalidProjectException.class, () -> projectController.createProject(project));
        verify(projectService, times(1)).createProject(project);
    }

    @Test
    void getProjects_shouldReturnListOfProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        projects.add(new Project());

        when(projectService.getAllProjects()).thenReturn(projects);

        ResponseEntity<?> response = projectController.getProjects();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    void getProject_withExistingProject_shouldReturnProject() throws ProjectNotFoundException {
        Project project = new Project();
        project.setId(1L);
        project.setName("Project 1");
        project.setDescription("Description of project 1");

        when(projectService.getProject(1L)).thenReturn(project);

        ResponseEntity<Project> response = projectController.getProject(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project, response.getBody());
        verify(projectService, times(1)).getProject(1L);
    }

    @Test
    void getProject_withNonExistingProject_shouldThrowProjectNotFoundException() throws ProjectNotFoundException {
        when(projectService.getProject(1L)).thenThrow(new ProjectNotFoundException("Project not found"));

        assertThrows(ProjectNotFoundException.class, () -> projectController.getProject(1L));
        verify(projectService, times(1)).getProject(1L);
    }
}
