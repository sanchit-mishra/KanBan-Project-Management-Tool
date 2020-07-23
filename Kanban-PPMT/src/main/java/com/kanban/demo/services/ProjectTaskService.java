package com.kanban.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kanban.demo.domain.Backlog;
import com.kanban.demo.domain.Project;
import com.kanban.demo.domain.ProjectTask;
import com.kanban.demo.exceptions.ProjectNotFoundException;
import com.kanban.demo.exceptions.ProjectNotFoundExceptionResponse;
import com.kanban.demo.repository.BacklogRepository;
import com.kanban.demo.repository.ProjectRepository;
import com.kanban.demo.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		//Exceptions: Project not found

		try {
			//PTs to be added to a specific project, project != null , BL exists
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			//set BL to PT
			projectTask.setBacklog(backlog);
			// ProjectSequence like :-TEST1-1, TEST1-2 ... so on!
			Integer BacklogSequence = backlog.getPTSequence();
			//Update backlog Sequence by 1
			BacklogSequence++;

			//Set to new sequence
			backlog.setPTSequence(BacklogSequence);

			//Add sequence to Project Task
			projectTask.setProjectSequence(projectIdentifier +"-"+ BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);

			//Initial priority when priority is null
			if(projectTask.getPriority() == null) { 
				projectTask.setPriority(3);
			}

			//Initial status when status is null
			if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO-DO");
			}


			return projectTaskRepository.save(projectTask);
		} catch(Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}

	}

	public Iterable<ProjectTask> findByBacklogId(String backlog_id) {

		Project project = projectRepository.findByProjectIdentifier(backlog_id);
		if(project == null) {
			throw new ProjectNotFoundException("Project with ID "+ backlog_id + " does not exist");
		}
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String project_id){
		
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		
		//To check if backlog exists or not
		if(backlog == null) {
			throw new ProjectNotFoundException("Project with ID '"+ backlog_id + "' does not exist");
		}
		
		//To check if ProjectTask exits or not
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(project_id);
		if(projectTask == null) {
			throw new ProjectNotFoundException("Project Task with ID '"+ project_id + "' does not exist");
		}
		//To check if backlog/projectTask path is correct or not
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
//			System.out.println(backlog.getProjectIdentifier());
//			System.out.println(projectTask.getProjectIdentifier());
			throw new ProjectNotFoundException("Project Task with this ID "+ project_id + " does not exist for this backlog ID: "+ backlog_id);
	}
		
		return projectTask;
	}

	public ProjectTask updatePTByProjectSequence(ProjectTask updateProjectTask, String backlog_id, String project_id){

		ProjectTask projectTask = findPTByProjectSequence(backlog_id,project_id);

		projectTask = updateProjectTask;
		return projectTaskRepository.save(projectTask);
	}
}
