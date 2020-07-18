package com.kanban.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.demo.domain.Backlog;
import com.kanban.demo.domain.ProjectTask;
import com.kanban.demo.repository.BacklogRepository;
import com.kanban.demo.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		//Exceptions: Project not found
		
		//PTs to be added to a specific project, project != null , BL exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		//set BL to PT
		projectTask.setBacklog(backlog);
		// ProjectSequence like :-TEST1-1, TEST1-2 ... so on!
		Integer BacklogSequence = backlog.getPTSequence();
		//Update backlog Sequence by 1
		BacklogSequence++;
		
		//Add sequence to Project Task
		projectTask.setProjectSequence(projectIdentifier +"-"+ BacklogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
		
		//Initial priority when priority is null
//		if(projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
//			projectTask.setPriority(3);
//		}
		
		//Initial status when status is null
		if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TO-DO");
		}
		
	
		return projectTaskRepository.save(projectTask);
		
	}
}
