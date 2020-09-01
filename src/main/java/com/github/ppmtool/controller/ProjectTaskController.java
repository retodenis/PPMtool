package com.github.ppmtool.controller;

import com.github.ppmtool.domain.ProjectTask;
import com.github.ppmtool.service.ProjectTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.github.ppmtool.validation.ValidationHelper.getBadRequestErrors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/projectTask")
@CrossOrigin
public class ProjectTaskController {

    private ProjectTaskService projectTaskService;

    @PostMapping("{projectLabel}")
    public ResponseEntity<?> add(
            @PathVariable String projectLabel,
            @Valid @RequestBody ProjectTask projectTask,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return getBadRequestErrors(bindingResult);

        ProjectTask newProjectTask = projectTaskService.addProjectTask(projectLabel, projectTask);

        return new ResponseEntity<>(newProjectTask, HttpStatus.CREATED);
    }

    @GetMapping("{projectLabel}")
    public ResponseEntity<Set<ProjectTask>> getAll(@PathVariable String projectLabel) {
        return new ResponseEntity<>
                (projectTaskService.findProjectTasksByProjectLabel(projectLabel), HttpStatus.OK);
    }

    @GetMapping("{projectLabel}/{ptSeq}")
    public ResponseEntity<ProjectTask> getOne(
            @PathVariable String projectLabel,
            @PathVariable String ptSeq) {
        return new ResponseEntity<>
            (projectTaskService.findByProjectByLabelAndSeq(projectLabel, ptSeq), HttpStatus.OK);
    }

    @PutMapping("{projectLabel}/{ptSeq}")
    public ResponseEntity<?> update(
            @PathVariable String projectLabel,
            @Valid @RequestBody ProjectTask projectTask,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return getBadRequestErrors(bindingResult);

        return new ResponseEntity<>
                (projectTaskService.updatePtTask(projectLabel, projectTask), HttpStatus.OK);
    }

    @DeleteMapping("{projectLabel}/{ptSeq}")
    public ResponseEntity<?> delete(
            @PathVariable String projectLabel,
            @PathVariable String ptSeq) {
        projectTaskService.deleteByPtSeq(projectLabel, ptSeq);
        Map<String, String> result = new HashMap<>();
        result.put(ptSeq, "Project task is deleted");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
