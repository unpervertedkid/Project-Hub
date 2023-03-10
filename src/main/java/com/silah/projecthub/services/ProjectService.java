package com.silah.projecthub.services;

import com.silah.projecthub.entities.Project;
import com.silah.projecthub.exceptions.InvalidProjectException;
import com.silah.projecthub.exceptions.ProjectNotFoundException;
import com.silah.projecthub.repositories.ProjectRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public long createProject(Project project) throws InvalidProjectException {
        if (project.isValid()) {
            return projectRepository.save(project).getId();
        } else {
            throw new InvalidProjectException("Project is not valid");
        }
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProject(long id) throws ProjectNotFoundException {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
    }

    public List<Project> getProjectByCategory(String category) throws ProjectNotFoundException {
        List<Project> projectList = projectRepository.findByCategory(category);
        if (projectList.isEmpty()) {
            throw new ProjectNotFoundException("Project not found");
        }
        return projectList;
    }

    public List<Project> getProjectsByImpressions() {
        return projectRepository.findAllByOrderByImpressionsDesc(Pageable.ofSize(10));
    }
}
