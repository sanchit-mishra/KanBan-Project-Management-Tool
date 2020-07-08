package com.kanban.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.demo.domain.Project;
import com.kanban.demo.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	
	public Project saveOrUpdate(Project project) {
		
		//Logic
		
		return projectRepository.save(project);
	}
}
