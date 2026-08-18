package com.example.portfolio.Repository;

import com.example.portfolio.DTO.ProjectResponse;
import com.example.portfolio.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {

    @Query("""
    SELECT new com.example.portfolio.DTO.ProjectResponse(
        p.id,
        p.title,
        p.description,
        p.repoLink,
        p.liveLink,
        p.apkDownloadLink,
        p.projectType,
        p.thumbnail.imgId
    )
    FROM Project p
    WHERE p.id = :id
""")
    Optional<ProjectResponse> findProjectDetailsById(@Param("id") Long id);

    @Query("""
     SELECT new com.example.portfolio.DTO.ProjectResponse(
        p.id,
        p.title,
        p.description,
        p.repoLink,
        p.liveLink,
        p.apkDownloadLink,
        p.projectType,
        p.thumbnail.imgId
    )
    FROM Project p
    ORDER BY p.id DESC
""")
    List<ProjectResponse> findAllProjects();


}
