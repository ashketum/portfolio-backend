package com.example.portfolio.Controller;

import com.example.portfolio.DTO.ProjectDTO;
import com.example.portfolio.DTO.ProjectResponse;
import com.example.portfolio.Model.Image;
import com.example.portfolio.Model.Project;
import com.example.portfolio.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService service;


    @GetMapping
    public List<ProjectResponse> getAllProject(){
        return service.getAllProject();
    }

    @GetMapping("/{id}")
    public ProjectResponse getProjectById(@PathVariable Long id){
        return service.getProjectById(id);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
       return service.getImage(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProject(@ModelAttribute ProjectDTO projectDTO) {
       return service.addProject(projectDTO);
    }

    @PutMapping(path = "/{projectId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProject(@PathVariable Long projectId ,@ModelAttribute ProjectDTO projectDTO) {
        return service.updateProject(projectId, projectDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProjectById(@PathVariable Long id) {
        return service.deleteProjectById(id);
    }


}
