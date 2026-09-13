package com.example.portfolio.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cert")
public class Cert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certId;

    private String certName;

    @Column(nullable = false, unique = true)
    private Long displayOrder;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "img_id", nullable = false)
    private Image certThumbnail;

    @Column(name = "cert_pdf", columnDefinition = "BYTEA")
    private byte[] certPdf;

}
