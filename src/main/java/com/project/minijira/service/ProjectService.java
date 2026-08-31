package com.project.minijira.service;

import com.project.minijira.model.Project;
import com.project.minijira.model.ProjectRequest;
import com.project.minijira.model.ProjectResponse;
import com.project.minijira.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public ProjectResponse createProject(ProjectRequest projectRequest){

        Project newProject = new Project();

        LocalDateTime now = LocalDateTime.now();

        newProject.setName(projectRequest.getName());
        newProject.setDescription(projectRequest.getDescription());

        newProject.setCreatedAt(now);
        newProject.setUpdatedAt(now);

        Project savedProject = projectRepository.save(newProject);

        return mapToResponse(savedProject);

    }

    public ProjectResponse getProject(Long id) {

        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Project Not Found");
        }

        Project project = optionalProject.get();

        return mapToResponse(project);

    }

    public List<ProjectResponse> getAllProjects(){

        List<Project> projects = projectRepository.findAll();
        List<ProjectResponse> projectResponses = new ArrayList<>();

        for(Project project : projects){
            projectResponses.add(mapToResponse(project));
        }
        return projectResponses;
    }

    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest){

        Optional<Project> optionalProject = projectRepository.findById(id);

        if(optionalProject.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
        }

        Project project = optionalProject.get();

        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setUpdatedAt(LocalDateTime.now());

        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);
    }

    private ProjectResponse mapToResponse(Project project){

        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setUpdatedAt(project.getUpdatedAt());
        projectResponse.setName(project.getName());
        projectResponse.setCreatedAt(project.getCreatedAt());
        projectResponse.setDescription(project.getDescription());
        projectResponse.setId(project.getId());

        return projectResponse;

    }

    public void deleteProject(Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);

        if(optionalProject.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
        }
        projectRepository.deleteById(id);
    }
}
