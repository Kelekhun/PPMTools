package com.kelekhun.ppmtools.controllers;

import com.kelekhun.ppmtools.domain.ProjectTask;
import com.kelekhun.ppmtools.services.MapValidationErrorService;
import com.kelekhun.ppmtools.services.ProjectTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/backlogs")
public class BacklogController {

    private ProjectTaskService projectTaskService;

    private MapValidationErrorService mapValidationErrorService;

    public BacklogController(ProjectTaskService projectTaskService, MapValidationErrorService mapValidationErrorService) {
        this.projectTaskService = projectTaskService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("/{projectIdentifier}")
    public ResponseEntity<?> addProjectTaskToBacklog(@PathVariable("projectIdentifier") String projectIdentifier, @Valid @RequestBody ProjectTask projectTask, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationErrorService.getMapValidationErrors(result);
        if (errorMap != null) return errorMap;

        return new ResponseEntity<>((projectTaskService.addProjectTask(projectIdentifier, projectTask)), HttpStatus.CREATED);
    }

}
