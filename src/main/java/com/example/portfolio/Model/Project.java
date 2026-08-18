package com.example.portfolio.Model;


import com.example.portfolio.Model.enums.ProjectType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectType projectType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "img_id", nullable = false)
    private Image thumbnail;

    @Column(length = 2000, nullable = false)
    private String description;

    @Column(nullable = false)
    private String repoLink;

    private String liveLink;

    private String apkDownloadLink;
}
