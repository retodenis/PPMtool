package com.github.ppmtool.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.ppmtool.domain.Project;
import com.github.ppmtool.domain.ProjectTask;
import com.github.ppmtool.domain.Views;
import com.github.ppmtool.service.ProjectService;
import com.github.ppmtool.service.ProjectTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Set;

import static com.github.ppmtool.validation.ValidationHelper.getBadRequestErrors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/projectTask")
@CrossOrigin
public class ProjectTaskController {

    private ProjectTaskService projectTaskService;
    private ProjectService projectService;

    @PostMapping("{projectLabel}")
    public ResponseEntity<?> addProject(
            @PathVariable String projectLabel,
            @Valid @RequestBody ProjectTask projectTask,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return getBadRequestErrors(bindingResult);

        ProjectTask newProjectTask = projectTaskService.addProjectTask(projectLabel, projectTask);

        return new ResponseEntity<>(newProjectTask, HttpStatus.CREATED);
    }

    @JsonView(Views.Tasks.class)
    @GetMapping("{projectLabel}")
    public ResponseEntity<Set<ProjectTask>> getProjectTasks(@PathVariable String projectLabel) {
        return new ResponseEntity<>
                (projectService.findProjectByUniqueLabel(projectLabel).getPtTasks(), HttpStatus.OK);
    }

    @GetMapping("{projectLabel}/{ptSeq}")
    public ResponseEntity<ProjectTask> getProjectTaskBySeq(
            @PathVariable String projectLabel,
            @PathVariable String ptSeq) {
        return new ResponseEntity<>
            (projectTaskService.findByProjectLabelAndSeq(projectLabel, ptSeq), HttpStatus.OK);
    }
}
