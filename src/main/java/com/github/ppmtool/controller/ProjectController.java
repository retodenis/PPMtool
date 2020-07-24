package com.github.ppmtool.controller;

import com.github.ppmtool.domain.Project;
import com.github.ppmtool.service.ProjectService;
import com.github.ppmtool.validation.ValidationHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody Project project,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors())
            return new ResponseEntity<>(ValidationHelper.getErrors(bindingResult), HttpStatus.BAD_REQUEST);

        Project newProject = service.saveOrUpdate(project);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

}
