package com.github.ppmtool.service;

import com.github.ppmtool.domain.Project;
import com.github.ppmtool.exceptions.ProjectIdException;
import com.github.ppmtool.repository.ProjectRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    private ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Project saveOrUpdate(Project project) {
        final String uniqueLabel = project.getUniqueLabel().toUpperCase();

        if(project.getId() != null) {
            Optional<Project> projectFromDb = repository.findById(project.getId());
            String projectLabelFromDb = projectFromDb.get().getUniqueLabel();
            if(!projectLabelFromDb.equals(uniqueLabel)) {
                throw new ProjectIdException("Project label is not updatable {0}", projectLabelFromDb);
            }
        }

        try {
            project.setUniqueLabel(uniqueLabel);

            return repository.save(project);
        } catch (DataIntegrityViolationException ex) {
            throw new ProjectIdException("Project Label: " + uniqueLabel + " already exists");
        }
    }

    public Project findProjectByUniqueLabel(String uniqueLabel) {
        final String uniqueLabelUpperCase = uniqueLabel.toUpperCase();
        Project project = repository.findByUniqueLabel(uniqueLabelUpperCase);

        if(project == null) {
            throw new ProjectIdException("Project Label: " + uniqueLabelUpperCase + " does not exists");
        }

        return project;
    }

    public Iterable<Project> findAllProjects() {
        return repository.findAll();
    }

    public void deleteProjectByLabel(String uniqueLabel) {
        final String uniqueLabelUpperCase = uniqueLabel.toUpperCase();
        Project project = repository.findByUniqueLabel(uniqueLabel);

        if(project == null) {
            throw new ProjectIdException("Cannot delete project : " + uniqueLabelUpperCase + " does not exists");
        }
        repository.delete(project);
    }
}
