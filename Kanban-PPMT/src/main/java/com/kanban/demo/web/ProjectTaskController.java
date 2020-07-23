package com.kanban.demo.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.kanban.demo.domain.ProjectTask;
import com.kanban.demo.services.MapValidationErrorService;
import com.kanban.demo.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class ProjectTaskController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private MapValidationErrorService mapValidationError;

	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask
			, BindingResult result, @PathVariable String backlog_id){
		ResponseEntity<?> errorMap = mapValidationError.MapValidationError(result);
		if(errorMap != null) return errorMap;

		ProjectTask projectTaskObj = projectTaskService.addProjectTask(backlog_id, projectTask);
		return new ResponseEntity<ProjectTask>(projectTaskObj,HttpStatus.CREATED);

	}

	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getBacklogById(@PathVariable String backlog_id){

		return projectTaskService.findByBacklogId(backlog_id);
	}

	@GetMapping("/{backlog_id}/{project_id}")
	public ResponseEntity<?> getProjectTaskBySequence(@PathVariable String backlog_id, @PathVariable String project_id) {
		
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, project_id);
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
		
	}

	@PatchMapping("/{backlog_id}/{project_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updateProjectTask, BindingResult result,
											   @PathVariable String backlog_id, @PathVariable String project_id){
		ResponseEntity<?> errorMap = mapValidationError.MapValidationError(result);
		if(errorMap != null) return errorMap;

		ProjectTask updatedProjectTask = projectTaskService.updatePTByProjectSequence(updateProjectTask,backlog_id,project_id);
		return  new ResponseEntity<ProjectTask>(updatedProjectTask,HttpStatus.OK);
	}
}
