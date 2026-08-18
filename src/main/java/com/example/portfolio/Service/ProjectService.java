package com.example.portfolio.Service;

import com.example.portfolio.DTO.ProjectDTO;
import com.example.portfolio.DTO.ProjectResponse;
import com.example.portfolio.Model.Image;
import com.example.portfolio.Model.Project;
import com.example.portfolio.Model.enums.ProjectType;
import com.example.portfolio.Repository.ImageRepo;
import com.example.portfolio.Repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class ProjectService {

    @Autowired
    private ProjectRepo repo;

    @Autowired
    private ImageRepo imgRepo;

    public List<ProjectResponse> getAllProject() {
        return repo.findAllProjects();
    }

    public ProjectResponse getProjectById(Long id){
        return repo.findProjectDetailsById(id).orElseThrow(() -> new RuntimeException("Project with id " + id + " " + "not found"));
    }

    public ResponseEntity<String> addProject(ProjectDTO projectDTO){
        try {

            Image image = Image.builder()
                    .name(projectDTO.getThumbnail().getOriginalFilename())
                    .type(projectDTO.getThumbnail().getContentType())
                    .imageData(projectDTO.getThumbnail().getBytes())
                    .build();

            Project project = new Project();
            setProject(project, projectDTO, image);
            repo.save(project);
            System.out.println("Successfully Added !!!");
            return new ResponseEntity<>("Successfully Added !!!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void setProject(Project project, ProjectDTO projectDTO, Image image) {
        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());
        project.setThumbnail(image);
        project.setProjectType(projectDTO.getProjectType());
        project.setRepoLink(projectDTO.getRepoLink());

        if(project.getProjectType() == ProjectType.WEB){
            project.setLiveLink(projectDTO.getLiveLink());
        } else if(project.getProjectType() == ProjectType.ANDROID){
            project.setApkDownloadLink(projectDTO.getApkDownloadLink());
        }
    }


    public ResponseEntity<String> updateProject(Long projectId, ProjectDTO updatedProjectDTO) {
        try {

            Project project = repo.findById(projectId)
                    .orElseThrow(() -> new RuntimeException("Project not found"));


                Image image = project.getThumbnail();
                image.setName(updatedProjectDTO.getThumbnail().getOriginalFilename());
                image.setType(updatedProjectDTO.getThumbnail().getContentType());
                image.setImageData(updatedProjectDTO.getThumbnail().getBytes());

           setProject(project, updatedProjectDTO, image);
            repo.save(project);
            System.out.println("Successfully Updated !!!");
            return new ResponseEntity<>("Successfully Updated !!!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteProjectById(Long id) {
            if(repo.existsById(id)) {
                repo.deleteById(id);
                return new ResponseEntity<>("Successfully Deleted this project!!", HttpStatus.OK);
            }
            return  new ResponseEntity<>("Resource not found", HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<byte[]> getImage(Long id) {
        Image image = imgRepo.findById(id).orElseThrow(() -> new RuntimeException("Image with id=" + id + " not found"));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getType()))
                .body(image.getImageData());
    }



}
