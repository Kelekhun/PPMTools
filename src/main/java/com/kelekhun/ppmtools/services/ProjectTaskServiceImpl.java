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
public class ProjectTaskServiceImpl implements ProjectTaskService  {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        // Design consideration for implementation

        // ProjectTask to be added to a specific Project -> "Project != null" means Backlog exists
        // Exceptions: Project not found
        try {

            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

            // Set the "Backlog" to ProjectTask"
            projectTask.setBacklog(backlog);

            //we want our project sequence to be like this: IDPRO-1  IDPRO-2  ...100 101
            Integer BacklogSequence = backlog.getPTSequence();

            // Update the Backlog sequence "PTSequence"
            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            // Project Sequece "projectSequence" should be like this: IDPRO-1, IDPRO-2 ... 100, 101
            // Add Sequence to ProjectTask
            projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            // INITIAL Status "To-DO " when status null:
            if(projectTask.getStatus()==""|| projectTask.getStatus()==null){
                projectTask.setStatus("TO_DO");
            }

            // INITIAL Priority "3" when priority null: Lowest Priority
            if(projectTask.getPriority()==null){
                projectTask.setPriority(3);
            }

            return projectTaskRepository.save(projectTask);

        }catch (Exception e){
            throw new ProjectNotFoundException("Project not Found");
        }

    }

    @Override
    public Iterable<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier){

        Project project = projectRepository.findByProjectIdentifier(projectIdentifier);

        if(project==null){
            throw new ProjectNotFoundException("Project with ID: '"+projectIdentifier+"' does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
    }

    @Override
    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){

        //make sure we are searching on an existing backlog
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog==null){
            throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' does not exist");
        }

        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' not found");
        }

        //make sure that the backlog/project id in the path corresponds to the right project
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project: '"+backlog_id);
        }


        return projectTask;
    }

    @Override
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id){
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }
    //Update project task

    //find existing project task

    //replace it with updated task

    //save update
}
