package com.kelekhun.ppmtools.services;

import com.kelekhun.ppmtools.domain.ProjectTask;
import org.springframework.stereotype.Service;

@Service
public interface ProjectTaskService {
    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask);
}
