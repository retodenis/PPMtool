package com.github.ppmtool.repository;

import com.github.ppmtool.domain.Backlog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {
    @Query("select b, p, pt from Backlog b join fetch b.project p left join fetch b.projectTasks pt where b.projectIdentifier = :projectIdentifier")
    Backlog findByProjectIdentifier(@Param("projectIdentifier") String projectIdentifier);
}
