package com.kelekhun.ppmtools.services;

import com.kelekhun.ppmtools.domain.Backlog;
import com.kelekhun.ppmtools.domain.Project;
import com.kelekhun.ppmtools.exceptions.ProjectIdException;
import com.kelekhun.ppmtools.repositories.BacklogRepository;
import com.kelekhun.ppmtools.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private BacklogRepository backlogRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, BacklogRepository backlogRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
    }

    @Override
    public Project saveOrUpdate(Project project) {
        try {
            // Making "projectIdentifier" field consistent across the method invocation so as to ease fetch and retrieval
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            // If no "Project" Object exist: Create a Backlog Item immediately
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            // If "Project" exists: Get the "projectIdentifier" from "BacklogRepository"
            if (project.getId() != null) {
                Backlog projectIdentifierFromBacklog = backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase());
                project.setBacklog(projectIdentifierFromBacklog);
            }

            return projectRepository.save(project);
        }
        catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    @Override
    public Project fetchProjectByProjectIdentifier(String projectIdentifier) {

        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project with projectIdentifier '" + projectIdentifier + "' does not exist");
        }
        return project;
    }

    @Override
    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public String deleteProjectByProjectIdentifier(String projectIdentifier) {

        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project with projectIdentifier '" + projectIdentifier + "' does not exist");
        }
        String messageOnDeletion = "Project with projectIdentifier '" + projectIdentifier + "' has been deleted for " + project.getId();
        projectRepository.delete(project);
        return messageOnDeletion;

    }

}
