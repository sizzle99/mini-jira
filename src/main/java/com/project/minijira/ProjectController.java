package com.project.minijira;

import com.project.minijira.model.Project;
import com.project.minijira.model.ProjectRequest;
import com.project.minijira.model.ProjectResponse;
import com.project.minijira.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
@Slf4j
public class ProjectController {

    private final ProjectService projectService;
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @PostMapping(value = "createProject")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest projectRequest){

        log.info("Creating project...");

        ProjectResponse projectResponse = projectService.createProject(projectRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(projectResponse);

    }

    @GetMapping(value = "/getProject/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id){

        log.info("Finding project in database....");

        ProjectResponse projectResponse = projectService.getProject(id);
        return ResponseEntity.ok(projectResponse);

    }

    @GetMapping(value = "/getAllProjects")
    public ResponseEntity<List<ProjectResponse>> getAllProject(){
        log.info("Finding all projects...");

        List<ProjectResponse> responses = projectService.getAllProjects();

        return ResponseEntity.ok().body(responses);
    }

    @PutMapping(value = "/updateProject/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody ProjectRequest projectRequest){
        log.info("updating project: " + projectRequest.getName());

        ProjectResponse projectResponse = projectService.updateProject(id,projectRequest);
        return ResponseEntity.ok().body(projectResponse);
    }

    @DeleteMapping("/deleteProject/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        log.info("Deleting project: {} ", id);

        projectService.deleteProject(id);
        log.info("Project with id: {} ", id + " has been deleted");

        return ResponseEntity.noContent().build();
    }

}
