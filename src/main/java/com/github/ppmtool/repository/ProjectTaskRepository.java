package com.github.ppmtool.repository;

import com.github.ppmtool.domain.Project;
import com.github.ppmtool.domain.ProjectTask;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
    @EntityGraph(attributePaths = {"project"})
    Iterable<ProjectTask> findByProject(Project project);

    ProjectTask findByProjectAndPtSeq(Project project, String ptSeq);
}
