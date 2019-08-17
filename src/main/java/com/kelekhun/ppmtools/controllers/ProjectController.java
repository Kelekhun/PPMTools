package com.kelekhun.ppmtools.controllers;

import com.kelekhun.ppmtools.domain.Project;
import com.kelekhun.ppmtools.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects/")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(method = RequestMethod.POST,path = "")
    public ResponseEntity<Project> createOrUpdateProject(@RequestBody Project project){
        return new ResponseEntity<Project>(projectService.saveOrUpdate(project), HttpStatus.CREATED);
    }
}
