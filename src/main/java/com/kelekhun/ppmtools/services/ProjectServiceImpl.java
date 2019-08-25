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
    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);

        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }

    }

    @Override
    public Project findProjectByProjectIdentifier(String projectIdentifier){

        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID '"+projectIdentifier+"' does not exist");
        }

        return project;
    }

    @Override
    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    @Override
    public void deleteProjectByProjectIdentifier(String projectIdentifier){
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

        if(project == null){
            throw  new  ProjectIdException("Cannot Project with ID '"+projectIdentifier+"'. This project does not exist");
        }

        projectRepository.delete(project);
    }
}
