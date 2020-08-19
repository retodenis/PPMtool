package com.github.ppmtool.service;

import com.github.ppmtool.domain.PTPriority;
import com.github.ppmtool.domain.Project;
import com.github.ppmtool.domain.ProjectTask;
import com.github.ppmtool.exceptions.ProjectIdException;
import com.github.ppmtool.repository.ProjectRepository;
import com.github.ppmtool.repository.ProjectTaskRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.github.ppmtool.domain.PTStatus.TO_DO;

@Service
@Log4j2
public class ProjectTaskService {
    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectRepository projectRepository;
    private final Lock lock;

    public ProjectTaskService
            (ProjectTaskRepository projectTaskRepository,
             ProjectRepository projectRepository) {
        this.projectTaskRepository = projectTaskRepository;
        this.projectRepository = projectRepository;
        this.lock = new ReentrantLock();
    }

    @SneakyThrows
    @Transactional
    public ProjectTask addProjectTask(String projectLabel, ProjectTask projectTask) {

        Integer ptSeq = 0;
        Project project = null;
        boolean isLockAcquired = lock.tryLock(1, TimeUnit.SECONDS);
        if(isLockAcquired) {
            try {
                project = projectRepository.findByUniqueLabel(projectLabel);

                if(project == null)
                    throw new ProjectIdException("Project Label " + projectLabel + " does not exists");

                ptSeq = project.getPtSeq();
                ptSeq++;
                project.setPtSeq(ptSeq);
                projectRepository.save(project);

                projectTask.setProject(project);
                projectTask.setPtSeq(projectLabel + "_" + ptSeq);

                if (projectTask.getPtPriority() == null) {
                    projectTask.setPtPriority(PTPriority.LOW);
                }

                if (projectTask.getStatus() == null) {
                    projectTask.setStatus(TO_DO);
                }

                return projectTaskRepository.save(projectTask);
            } finally {
                lock.unlock();
                log.info("lock released");
            }
        }

        return null;
    }

    public Set<ProjectTask> findProjectTasksByProjectLabel(String projectLabel) {

        Project project = projectRepository.findByUniqueLabel(projectLabel);

        if(project == null) {
            throw new ProjectIdException("Project " + projectLabel + " does not exists ");
        }

        return projectTaskRepository.findByProject(project);
    }

    public ProjectTask findByProjectByLabelAndSeq
            (String projectLabel, String projectSeq) {

        Project project = projectRepository.findByUniqueLabel(projectLabel);

        if(project == null)
            throw new ProjectIdException("Project {0} does not exists", projectLabel);

        ProjectTask ptTask = projectTaskRepository.findByProjectAndPtSeq(project, projectSeq);

        if(ptTask == null)
            throw new ProjectIdException("Project Task {0} does not exists", projectSeq);

        return ptTask;
    }

    public ProjectTask updatePtTask(String projectLabel, ProjectTask projectTask) {

        Project projectFromDb = projectRepository.findByUniqueLabel(projectLabel);

        if(projectFromDb == null)
            throw new ProjectIdException("Project {0} does not exists", projectLabel);

        ProjectTask projectTaskFromDb =
                projectTaskRepository.findById(
                        projectTask.getId())
                        .orElseThrow(() -> new ProjectIdException("Project Task {0} does not exists", String.valueOf(projectTask.getId())));

        BeanUtils.copyProperties(projectTask, projectTaskFromDb, "id", "ptSeq");
        return projectTaskRepository.save(projectTaskFromDb);
    }

    public void deleteByPtSeq(String projectLabel, String ptSeq) {
        Project projectFromDb = projectRepository.findByUniqueLabel(projectLabel);

        if(projectFromDb == null)
            throw new ProjectIdException("Project {0} does not exists", projectLabel);

        ProjectTask projectTask = projectTaskRepository.findByProjectAndPtSeq(projectFromDb, ptSeq);

        if(projectTask == null)
            throw new ProjectIdException("Project task {0} does not exists", ptSeq);

        projectTaskRepository.deleteById(projectTask.getId());
    }
}
