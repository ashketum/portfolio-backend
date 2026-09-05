package com.example.portfolio.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@RestControllerAdvice
public class CertDTO {

    private Long certId;

    private String certName;
    private MultipartFile certThumbnail;

    private MultipartFile certPdf;
}
