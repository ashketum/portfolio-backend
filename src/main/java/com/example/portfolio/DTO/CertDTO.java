package com.example.portfolio.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@RestControllerAdvice
public class CertDTO {

    private Long certId;

    private String certName;
    private MultipartFile certThumbnail;

    private MultipartFile certPdf;
}
