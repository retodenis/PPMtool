package com.github.ppmtool.repository;

import com.github.ppmtool.domain.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByProjectIdentifier(String projectId);

    @Override
    @Query("select p, b, pt from Project p join fetch p.backlog b left join fetch b.projectTasks pt")
    Iterable<Project> findAll();
}
