package com.github.ppmtool.repository;

import com.github.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
    Iterable<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);

    ProjectTask findByProjectSequence(String sequence);

    ProjectTask findByProjectIdentifierAndProjectSequence
            (String projectIdentifier, String projectSequence);
}
