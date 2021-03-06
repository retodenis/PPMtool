package com.github.ppmtool.controller;

import com.github.ppmtool.domain.Project;
import com.github.ppmtool.domain.dto.ProjectResponseDto;
import com.github.ppmtool.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.github.ppmtool.validation.ValidationHelper.getBadRequestErrors;

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
            return getBadRequestErrors(bindingResult);;

        Project newProject = service.saveOrUpdate(project);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @GetMapping("{projectLabel}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectLabel) {
        Project project = service.findProjectByUniqueLabel(projectLabel);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Project>> getAllProjects() {
        Iterable<Project> projects = service.findAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @DeleteMapping("{projectLabel}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectLabel) {
        service.deleteProjectByLabel(projectLabel);
        return new ResponseEntity<>(new ProjectResponseDto(projectLabel, "Is deleted"), HttpStatus.OK);
    }

}
