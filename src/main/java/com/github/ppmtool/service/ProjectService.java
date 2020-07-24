package com.github.ppmtool.service;

import com.github.ppmtool.domain.Project;
import com.github.ppmtool.exceptions.ProjectIdException;
import com.github.ppmtool.repository.ProjectRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Project saveOrUpdate(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return repository.save(project);
        } catch (DataIntegrityViolationException ex) {
            throw new ProjectIdException("Project ID: " + project.getProjectIdentifier() + " already exists");
        }
    }
}
