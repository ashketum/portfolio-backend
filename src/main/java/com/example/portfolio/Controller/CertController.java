package com.example.portfolio.Controller;

import com.example.portfolio.DTO.CertDTO;
import com.example.portfolio.Model.Cert;
import com.example.portfolio.Service.CertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = {
                "https://adityasantoshgupta223.github.io"
 })
@RestController
@RequestMapping("/certs")
public class CertController {

    @Autowired
    private CertService service;

    @GetMapping
    public List<Cert> getAllCert(){
        return service.getAllCert();
    }


    @PostMapping("/uploads")
    public ResponseEntity<String> addCert(@ModelAttribute CertDTO certDTO) throws IOException {
        return service.addCert(certDTO);
    }

    @GetMapping("/{certId}/pdf")
    public ResponseEntity<byte[]> getCertPdf(@PathVariable Long certId) {
        return service.getCertPDF(certId);
    }

    @PutMapping("/{certId}")
    public ResponseEntity<String> updateCert(@PathVariable Long certId, @RequestParam Cert cert) {
        return service.updateCert(certId, cert);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCertById(@PathVariable Long id) {
        return service.deleteCertById(id);
    }

}
