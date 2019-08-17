package com.kelekhun.ppmtools.services;

import com.kelekhun.ppmtools.domain.Project;
import com.kelekhun.ppmtools.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project saveOrUpdate(Project project) {
        return projectRepository.save(project);
    }
}
