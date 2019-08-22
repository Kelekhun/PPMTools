package com.kelekhun.ppmtools.services;

import com.kelekhun.ppmtools.domain.ProjectTask;

public interface ProjectTaskService {
    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask);
}
