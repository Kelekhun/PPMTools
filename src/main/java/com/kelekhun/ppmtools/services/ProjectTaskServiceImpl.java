package com.kelekhun.ppmtools.services;

import com.kelekhun.ppmtools.domain.Backlog;
import com.kelekhun.ppmtools.domain.ProjectTask;
import com.kelekhun.ppmtools.repositories.BacklogRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {
    private BacklogRepository backlogRepository;

    public ProjectTaskServiceImpl(BacklogRepository backlogRepository) {
        this.backlogRepository = backlogRepository;
    }

    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        projectTask.setBacklog(backlog);


        return null;
    }
}
