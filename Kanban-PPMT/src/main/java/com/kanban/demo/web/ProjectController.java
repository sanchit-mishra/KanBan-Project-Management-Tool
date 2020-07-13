package com.kanban.demo.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kanban.demo.domain.Project;
import com.kanban.demo.services.MapValidationErrorService;
import com.kanban.demo.services.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MapValidationErrorService mapValidationError;
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){
		
		ResponseEntity<?> errorMap = mapValidationError.MapValidationError(result);
		if(errorMap != null) return errorMap;
		
		Project project1 = projectService.saveOrUpdate(project);
		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<?> findByProjectId(@PathVariable String projectId){
		Project project = projectService.findProjectByIdentifier(projectId);
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public Iterable<Project> findAllProject(){
		return projectService.findProjects();
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId){
		projectService.deleteProject(projectId);
		
		return new ResponseEntity<String>("Project Identifier "+ projectId + " is deleted", HttpStatus.OK);
	}
	
}
