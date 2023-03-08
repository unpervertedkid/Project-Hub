package com.silah.projecthub.services;

import com.silah.projecthub.entities.Category;
import com.silah.projecthub.entities.Project;
import com.silah.projecthub.exceptions.InvalidProjectException;
import com.silah.projecthub.exceptions.ProjectNotFoundException;
import com.silah.projecthub.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test create project with valid project")
    void testCreateProjectWithValidProject() throws InvalidProjectException {
        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("This is a test project");
        project.setCategory(Category.WEB);

        when(projectRepository.save(project)).thenReturn(project);

        long id = projectService.createProject(project);

        verify(projectRepository, times(1)).save(project);

        assertEquals(project.getId(), id);
    }

    @Test
    @DisplayName("Test create project with invalid project")
    void testCreateProjectWithInvalidProject() {
        Project project = new Project();
        project.setName(null);
        project.setDescription("This is a test project");
        project.setCategory(Category.WEB);

        assertThrows(InvalidProjectException.class, () -> projectService.createProject(project));

        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    @DisplayName("Test get all projects")
    void testGetAllProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        projects.add(new Project());
        projects.add(new Project());

        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectService.getAllProjects();

        verify(projectRepository, times(1)).findAll();

        assertEquals(projects.size(), result.size());
    }

    @Test
    @DisplayName("Test get project with existing id")
    void testGetProjectWithExistingId() throws ProjectNotFoundException {
        long id = 1;
        Project project = new Project();
        project.setId(id);
        project.setName("Test Project");
        project.setDescription("This is a test project");
        project.setCategory(Category.WEB);

        when(projectRepository.findById(id)).thenReturn(Optional.of(project));

        Project result = projectService.getProject(id);

        verify(projectRepository, times(1)).findById(id);

        assertEquals(project.getId(), result.getId());
        assertEquals(project.getName(), result.getName());
        assertEquals(project.getDescription(), result.getDescription());
        assertEquals(project.getCategory(), result.getCategory());
    }

    @Test
    @DisplayName("Test get project with non-existing id")
    void testGetProjectWithNonExistingId() {
        long id = 1;

        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.getProject(id));

        verify(projectRepository, times(1)).findById(id);
    }

}
