package com.github.ppmtool.service;

import com.github.ppmtool.domain.Backlog;
import com.github.ppmtool.domain.PTPriority;
import com.github.ppmtool.domain.ProjectTask;
import com.github.ppmtool.repository.BacklogRepository;
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
    private BacklogRepository backlogRepository;
    private ProjectTaskRepository projectTaskRepository;
    private Lock lock;

    public ProjectTaskService(BacklogRepository backlogRepository, ProjectTaskRepository projectTaskRepository) {
        this.backlogRepository = backlogRepository;
        this.projectTaskRepository = projectTaskRepository;
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
                backlogSequence = backlog.getPtSequence();
                backlogSequence++;
                backlog.setPtSequence(backlogSequence);
                backlogRepository.save(backlog);
            } finally {
                lock.unlock();
                log.info("lock released");
            }
        }

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
    }
}
