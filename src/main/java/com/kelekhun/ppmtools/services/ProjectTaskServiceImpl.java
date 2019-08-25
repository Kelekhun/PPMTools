package com.kelekhun.ppmtools.services;

import com.kelekhun.ppmtools.domain.Backlog;
import com.kelekhun.ppmtools.domain.ProjectTask;
import com.kelekhun.ppmtools.repositories.BacklogRepository;
import com.kelekhun.ppmtools.repositories.ProjectTaskRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

    private BacklogRepository backlogRepository;
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTaskServiceImpl(BacklogRepository backlogRepository, ProjectTaskRepository projectTaskRepository) {
        this.backlogRepository = backlogRepository;
        this.projectTaskRepository = projectTaskRepository;
    }

    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        // Design consideration for implementation

        // ProjectTask to be added to a specific Project -> "Project != null" means Backlog exists
        // Exceptions: Project not found
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        // Set the "Backlog" to ProjectTask"
        projectTask.setBacklog(backlog);

        Integer BacklogSequence = backlog.getPTSequence();

        // Update the Backlog sequence "PTSequence"
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);

        // Project Sequece "projectSequence" should be like this: IDPRO-1, IDPRO-2 ... 100, 101
        // Add Sequence to ProjectTask
        projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
        projectTask.setProjectIdentifer(backlog.getProjectIdentifier());

        // INITIAL Priority "3" when priority null: Lowest Priority
        if (projectTask.getPriority() == null) {
            projectTask.setPriority(3);
        }

        // INITIAL Status "To-DO " when status null:
        if ((projectTask.getStatus() == null) || (projectTask.getStatus() == "")) {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);

    }
}
