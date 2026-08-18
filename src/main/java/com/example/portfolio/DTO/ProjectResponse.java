package com.example.portfolio.DTO;

import com.example.portfolio.Model.Project;
import com.example.portfolio.Model.enums.ProjectType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {

        private Long id;
        private String title;
        private String description;
        private String repoLink;
        private String liveLink;
        private String apkDownloadLink;
        private ProjectType projectType;
        private Long thumbnailId;

    public ProjectResponse(Project project) {
    }
}
