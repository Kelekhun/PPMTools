package com.kelekhun.ppmtools.controllers;

import com.kelekhun.ppmtools.domain.Project;
import com.kelekhun.ppmtools.services.MapValidationErrorService;
import com.kelekhun.ppmtools.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class ProjectController {

    private ProjectService projectService;

    private MapValidationErrorService mapValidationErrorService;

    public ProjectController(ProjectService projectService, MapValidationErrorService mapValidationErrorService) {
        this.projectService = projectService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.getMapValidationErrors(result);
        if(errorMap!=null) return errorMap;

        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectByProjectIdentifier(@PathVariable String projectIdentifier){

        return new ResponseEntity<>(projectService.findProjectByProjectIdentifier(projectIdentifier), HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProjects();
    }


    @DeleteMapping("/{projectIdentifier}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier){
        projectService.deleteProjectByProjectIdentifier (projectIdentifier);
        return new ResponseEntity<String>("Project with ID: '"+projectIdentifier+"' was deleted", HttpStatus.OK);
    }
}
