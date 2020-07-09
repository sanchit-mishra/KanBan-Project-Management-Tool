package com.kanban.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.demo.domain.Project;
import com.kanban.demo.exceptions.ProjectIdException;
import com.kanban.demo.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	
	public Project saveOrUpdate(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		}
		catch(Exception ex) {
			throw new ProjectIdException("Project Identifier "+ project.getProjectIdentifier().toUpperCase() +" is already taken");
		}
	
	}
	
	public Project findProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Project ID"+ projectId +" does not exists");
		}
		return project;
	}
}
