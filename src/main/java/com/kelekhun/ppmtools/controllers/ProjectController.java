package com.kelekhun.ppmtools.controllers;

import com.kelekhun.ppmtools.domain.Project;
import com.kelekhun.ppmtools.services.MapValidationErrorService;
import com.kelekhun.ppmtools.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/projects/")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @RequestMapping(method = RequestMethod.POST,path = "")
    public ResponseEntity<?> createOrUpdateProject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.getMapValidationErrors(result);
        if(errorMap!=null) return errorMap;

        return new ResponseEntity<Project>(projectService.saveOrUpdate(project), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{projectIdentifier}", method = RequestMethod.GET)
    public ResponseEntity<?> getProjectByProjectIdentifier(@PathVariable("projectIdentifier") String projectIdentifier) {

        return new ResponseEntity(projectService.fetchProjectByProjectIdentifier(projectIdentifier), HttpStatus.OK);
    }

}
