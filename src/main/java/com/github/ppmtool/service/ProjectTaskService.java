package com.github.ppmtool.service;

import com.github.ppmtool.domain.ProjectTask;
import com.github.ppmtool.repository.BacklogRepository;
import com.github.ppmtool.repository.ProjectTaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectTaskService {
    private BacklogRepository backlogRepository;
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask() {
        return null;
    }
}
