package com.silah.projecthub.services;

import com.silah.projecthub.entities.Project;
import com.silah.projecthub.exceptions.InvalidProjectException;
import com.silah.projecthub.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public double createProject(Project project) throws InvalidProjectException {
        if (project.isValid()) {
            return projectRepository.save(project).getId();
        }else{
            throw new InvalidProjectException("Project is not valid");
        }
    }
}
