package com.github.ppmtool.service;

import com.github.ppmtool.domain.Backlog;
import com.github.ppmtool.domain.PTPriority;
import com.github.ppmtool.domain.Project;
import com.github.ppmtool.domain.ProjectTask;
import com.github.ppmtool.exceptions.ProjectIdException;
import com.github.ppmtool.repository.BacklogRepository;
import com.github.ppmtool.repository.ProjectRepository;
import com.github.ppmtool.repository.ProjectTaskRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.github.ppmtool.domain.PTStatus.TO_DO;

@Service
@Log4j2
public class ProjectTaskService {
    private final BacklogRepository backlogRepository;
    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectRepository projectRepository;
    private final Lock lock;

    public ProjectTaskService
            (BacklogRepository backlogRepository,
             ProjectTaskRepository projectTaskRepository,
             ProjectRepository projectRepository) {
        this.backlogRepository = backlogRepository;
        this.projectTaskRepository = projectTaskRepository;
        this.projectRepository = projectRepository;
        this.lock = new ReentrantLock();
    }

    @SneakyThrows
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        Backlog backlog = null;
        Integer backlogSequence = 0;
        boolean isLockAcquired = lock.tryLock(2, TimeUnit.SECONDS);
        if(isLockAcquired) {
            try {
                backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

                if(backlog == null)
                    throw new ProjectIdException("Project id " + projectIdentifier + " does not exists");

                backlogSequence = backlog.getPtSequence();
                backlogSequence++;
                backlog.setPtSequence(backlogSequence);
                backlogRepository.save(backlog);
                projectTask.setBacklog(backlog);
                projectTask.setProjectSequence(projectIdentifier + "_" + backlogSequence);
                projectTask.setProjectIdentifier(projectIdentifier);

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

    public Iterable<ProjectTask> findProjectTasksById(String projectIdentifier) {

        Project project = projectRepository.findByProjectIdentifier(projectIdentifier);

        if(project == null) {
            throw new ProjectIdException("Project " + projectIdentifier + " does not exists ");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
    }
}
