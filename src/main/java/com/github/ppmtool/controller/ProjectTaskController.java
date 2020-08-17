package com.github.ppmtool.controller;

import com.github.ppmtool.domain.ProjectTask;
import com.github.ppmtool.service.ProjectTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.github.ppmtool.validation.ValidationHelper.getBadRequestErrors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/projectTask")
@CrossOrigin
public class ProjectTaskController {

    private ProjectTaskService projectTaskService;

    @PostMapping("{projectIdentifier}")
    public ResponseEntity<?> addProject(
            @PathVariable String projectIdentifier,
            @Valid @RequestBody ProjectTask projectTask,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return getBadRequestErrors(bindingResult);

        ProjectTask newProjectTask = projectTaskService.addProjectTask(projectIdentifier, projectTask);

        return new ResponseEntity<>(newProjectTask, HttpStatus.CREATED);
    }

    @GetMapping("{projectIdentifier}")
    public ResponseEntity<Iterable<ProjectTask>> getProjectTasks(@PathVariable String projectIdentifier) {
        return new ResponseEntity<>
                (projectTaskService.findProjectTasksById(projectIdentifier), HttpStatus.OK);
    }

    @GetMapping("{projectIdentifier}/{projectSequence}")
    public ResponseEntity<ProjectTask> getProjectTaskBySequence(
            @PathVariable String projectIdentifier,
            @PathVariable String projectSequence) {
        return new ResponseEntity<>
            (projectTaskService.findByProjectIdentifierAndProjectSequence(
                    projectIdentifier,
                    projectSequence), HttpStatus.OK);
    }
}
