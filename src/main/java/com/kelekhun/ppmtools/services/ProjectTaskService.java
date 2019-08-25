package com.kelekhun.ppmtools.services;

import com.kelekhun.ppmtools.domain.Backlog;
import com.kelekhun.ppmtools.domain.Project;
import com.kelekhun.ppmtools.domain.ProjectTask;
import com.kelekhun.ppmtools.exceptions.ProjectNotFoundException;
import com.kelekhun.ppmtools.repositories.BacklogRepository;
import com.kelekhun.ppmtools.repositories.ProjectRepository;
import com.kelekhun.ppmtools.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public interface ProjectTaskService {
    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask);
    Iterable<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier);
    ProjectTask findPTByProjectSequence(String projectIdentifier, String pt_id);
    ProjectTask updateByProjectSequence(ProjectTask updatedTask, String projectIdentifier, String pt_id);
}

