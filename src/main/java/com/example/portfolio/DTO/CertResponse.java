package com.example.portfolio.DTO;

import com.example.portfolio.Model.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CertResponse {

    private Long certId;
    private String certName;
    private Image certThumbnail;

}
