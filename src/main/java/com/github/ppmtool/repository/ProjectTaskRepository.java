package com.github.ppmtool.repository;

import com.github.ppmtool.domain.Project;
import com.github.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
    Set<ProjectTask> findByProject(Project project);

    ProjectTask findByProjectAndPtSeq(Project project, String ptSeq);
}
