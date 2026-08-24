package com.project.minijira.service;

import com.project.minijira.model.Project;
import com.project.minijira.model.ProjectRequest;
import com.project.minijira.model.ProjectResponse;
import com.project.minijira.repositories.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public ProjectResponse createProject(ProjectRequest projectRequest){

        Project newProject = new Project();
        ProjectResponse projectResponse = new ProjectResponse();

        LocalDateTime now = LocalDateTime.now();


        newProject.setName(projectRequest.getName());
        newProject.setDescription(projectRequest.getDescription());

        newProject.setCreatedAt(now);
        newProject.setUpdatedAt(now);


        Project savedProject = projectRepository.save(newProject);

        projectResponse.setName(savedProject.getName());
        projectResponse.setDescription(savedProject.getDescription());
        projectResponse.setId(savedProject.getId());
        projectResponse.setCreatedAt(savedProject.getCreatedAt());
        projectResponse.setUpdatedAt(savedProject.getUpdatedAt());
        return projectResponse;
    }

    public ProjectResponse getProject(Long id) {

        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Project Not Found");
        }

        ProjectResponse projectResponse = new ProjectResponse();
        Project project = optionalProject.get();


        projectResponse.setUpdatedAt(project.getUpdatedAt());
        projectResponse.setName(project.getName());
        projectResponse.setCreatedAt(project.getCreatedAt());
        projectResponse.setDescription(project.getDescription());
        projectResponse.setId(project.getId());

        return projectResponse;
    }

    public List<ProjectResponse> getAllProjects(){

        List<Project> projects = projectRepository.findAll();
        List<ProjectResponse> projectResponses = new ArrayList<>();

        for(Project project : projects){
            ProjectResponse projectResponse = new ProjectResponse();

            projectResponse.setUpdatedAt(project.getUpdatedAt());
            projectResponse.setName(project.getName());
            projectResponse.setCreatedAt(project.getCreatedAt());
            projectResponse.setDescription(project.getDescription());
            projectResponse.setId(project.getId());

            projectResponses.add(projectResponse);

        }
        return projectResponses;
    }
}
