package com.silah.projecthub.controllers;

import com.silah.projecthub.entities.Project;
import com.silah.projecthub.exceptions.InvalidProjectException;
import com.silah.projecthub.exceptions.ProjectNotFoundException;
import com.silah.projecthub.services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<Long> createProject(@RequestBody Project project) throws InvalidProjectException, URISyntaxException {
        return ResponseEntity.created(new URI("/api/v1/projects/" + projectService.createProject(project))).build();
    }

    @GetMapping
    public ResponseEntity<?> getProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable long id) throws ProjectNotFoundException {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @GetMapping("/category/{category}/")
    public ResponseEntity<?> getProjectByCategory(@PathVariable String category) throws ProjectNotFoundException {
        return ResponseEntity.ok(projectService.getProjectByCategory(category));
    }

    @GetMapping("/category/{category}/impressions")
    public ResponseEntity<?> getProjectByCategoryByImpressions(@PathVariable String category) throws ProjectNotFoundException {
        return ResponseEntity.ok(projectService.getProjectByCategorySortedByImpression(category));
    }


    @GetMapping("/category/{category}/likes")
    public ResponseEntity<?> getProjectByCategoryByLikes(@PathVariable String category) throws ProjectNotFoundException {
        return ResponseEntity.ok(projectService.getProjectByCategorySortedByLikes(category));
    }

    @GetMapping("/impressions")
    public ResponseEntity<?> getProjectsByImpressions() {
        return ResponseEntity.ok(projectService.getProjectsByImpressions());
    }

    @GetMapping("/likes")
    public ResponseEntity<?> getProjectsByLikes() {
        return ResponseEntity.ok(projectService.getProjectsByLikes());
    }

}
