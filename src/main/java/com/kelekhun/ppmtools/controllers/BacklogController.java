package com.kelekhun.ppmtools.controllers;

import com.kelekhun.ppmtools.domain.ProjectTask;
import com.kelekhun.ppmtools.services.MapValidationErrorService;
import com.kelekhun.ppmtools.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/backlogs")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @PostMapping("/{projectIdentifier}")
    public ResponseEntity<?> addProjectTaskToBacklog(@PathVariable String projectIdentifier,
                                                     @Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult result){
        //show delete
        //custom exception

        ResponseEntity<?> errorMap = mapValidationErrorService.getMapValidationErrors(result);
        if (errorMap != null) return errorMap;

        return new ResponseEntity<>(projectTaskService.addProjectTask(projectIdentifier, projectTask), HttpStatus.CREATED);
    }

    @GetMapping("/{projectIdentifier}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String projectIdentifier){

        return projectTaskService.findBacklogByProjectIdentifier(projectIdentifier);

    }

    @GetMapping("/{projectIdentifier}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String projectIdentifier, @PathVariable String pt_id){
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(projectIdentifier, pt_id);
        return new ResponseEntity<ProjectTask>( projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{projectIdentifier}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                               @PathVariable String projectIdentifier, @PathVariable String pt_id ){

        ResponseEntity<?> errorMap = mapValidationErrorService.getMapValidationErrors(result);
        if (errorMap != null) return errorMap;

        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, projectIdentifier, pt_id);

        return new ResponseEntity<ProjectTask>(updatedTask,HttpStatus.OK);

    }

}
