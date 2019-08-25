package com.kelekhun.ppmtools.services;

import com.kelekhun.ppmtools.domain.Project;

public interface ProjectService {
    Project saveOrUpdateProject(Project project);
    Project findProjectByProjectIdentifier(String projectIdentifier);
    Iterable<Project> findAllProjects();
    void deleteProjectByProjectIdentifier(String projectIdentifier);
}
