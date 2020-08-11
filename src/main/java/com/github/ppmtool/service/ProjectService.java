package com.github.ppmtool.service;

import com.github.ppmtool.domain.Backlog;
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
        final String projectIdentifier = project.getProjectIdentifier().toUpperCase();
        try {
            project.setProjectIdentifier(projectIdentifier);

            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectIdentifier);
            }

            return repository.save(project);
        } catch (DataIntegrityViolationException ex) {
            throw new ProjectIdException("Project ID: " + projectIdentifier + " already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = repository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) {
            throw new ProjectIdException("Project ID: " + projectId.toUpperCase() + " does not exists");
        }

        return project;
    }

    public Iterable<Project> findAllProjects() {
        return repository.findAll();
    }

    public void deleteProjectById(String projectId) {
        Project project = repository.findByProjectIdentifier(projectId);

        if(project == null) {
            throw new ProjectIdException("Cannot delete project : " + projectId.toUpperCase() + " does not exists");
        }
        repository.delete(project);
    }
}
