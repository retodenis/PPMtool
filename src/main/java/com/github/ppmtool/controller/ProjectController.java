package com.github.ppmtool.controller;

import com.github.ppmtool.domain.Project;
import com.github.ppmtool.domain.dto.ProjectResponseDto;
import com.github.ppmtool.service.ProjectService;
import com.github.ppmtool.validation.ValidationHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
    private ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> create(
            @Valid @RequestBody Project project,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors())
            return new ResponseEntity<>(ValidationHelper.getErrors(bindingResult), HttpStatus.BAD_REQUEST);

        Project newProject = service.saveOrUpdate(project);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @GetMapping("{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {
        Project project = service.findProjectByIdentifier(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Project>> getAllProjects() {
        Iterable<Project> projects = service.findAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @DeleteMapping("{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
        service.deleteProjectById(projectId);
        return new ResponseEntity<>(new ProjectResponseDto(projectId, "Is deleted"), HttpStatus.OK);
    }

}
