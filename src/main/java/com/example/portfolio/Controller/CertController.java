package com.example.portfolio.Controller;

import com.example.portfolio.DTO.CertDTO;
import com.example.portfolio.DTO.CertResponse;
import com.example.portfolio.Model.Cert;
import com.example.portfolio.Service.CertService;
import com.example.portfolio.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = {
        "${frontend.url}",
        "${admin.url}"
})
@RestController
@RequestMapping("/certs")
public class CertController {

    @Autowired
    private CertService service;


    @GetMapping
    public List<CertResponse> getAllCert(){
        return service.getAllCert();
    }


    @GetMapping("{certId}/images")
    public ResponseEntity<byte[]> getImage(@PathVariable Long certId) {
        return service.getImage(certId);
    }

    @GetMapping("/{certId}/pdf")
    public ResponseEntity<byte[]> getCertPdf(@PathVariable Long certId) {
        return service.getCertPDF(certId);
    }


    @PostMapping("/uploads")
    public ResponseEntity<String> addCert(@ModelAttribute CertDTO certDTO) throws IOException {
        return service.addCert(certDTO);
    }

    @PutMapping("/{certId}")
    public ResponseEntity<String> updateCert(@PathVariable Long certId, @ModelAttribute CertDTO certDTO) throws IOException {
        return service.updateCert(certId, certDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCertById(@PathVariable Long id) {
        return service.deleteCertById(id);
    }

}
