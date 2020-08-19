package com.github.ppmtool.repository;

import com.github.ppmtool.domain.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByUniqueLabel(String uniqueLabel);

    @Override
    @EntityGraph(attributePaths = {"ptTasks"})
    Iterable<Project> findAll();
}
