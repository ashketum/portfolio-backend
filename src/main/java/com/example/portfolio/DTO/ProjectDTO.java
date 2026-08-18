package com.example.portfolio.DTO;


import com.example.portfolio.Model.Image;
import com.example.portfolio.Model.enums.ProjectType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@RestControllerAdvice
public class ProjectDTO {

    private Long id;
    private ProjectType projectType;

    private String title;

    private String description;

    private String repoLink;
    private String liveLink;

    private MultipartFile thumbnail;
    private String apkDownloadLink;

}
