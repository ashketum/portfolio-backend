package com.example.portfolio.Service;

import com.example.portfolio.DTO.CertDTO;
import com.example.portfolio.DTO.CertResponse;
import com.example.portfolio.Exception.ResourceNotFoundException;
import com.example.portfolio.Model.Cert;
import com.example.portfolio.Model.Image;
import com.example.portfolio.Repository.CertRepo;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class CertService {

    @Autowired
    private CertRepo repo;


    public List<CertResponse> getAllCert() {
        return repo.findAllCerts();
    }

    private byte[] createToImage(byte[] pdfBytes) throws IOException {

        try (PDDocument document = Loader.loadPDF(pdfBytes)) {

            PDFRenderer renderer = new PDFRenderer(document);

            BufferedImage image = renderer.renderImageWithDPI(0, 150);

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            ImageIO.write(image, "jpg", output);

            return  output.toByteArray();
        }
    }


    public ResponseEntity<String> addCert(CertDTO certDTO) throws IOException {
        System.out.println(certDTO.toString());
        if(certDTO == null || certDTO.getCertName().isBlank() || certDTO.getCertPdf().getSize() == 0){
            return new ResponseEntity<>("Missing Info", HttpStatus.BAD_REQUEST);
        }
        Image thumbnail = Image.builder()
                          .type("image/jpeg")
                          .name(certDTO.getCertName())
                          .imageData(createToImage(certDTO.getCertPdf().getBytes()))
                          .build();

        Cert cert = Cert.builder()
                        .certName(certDTO.getCertName())
                        .certThumbnail(thumbnail)
                        .certPdf(certDTO.getCertPdf().getBytes())
                        .build();

        repo.save(cert);
        return new ResponseEntity<>("Successfully Added !!!", HttpStatus.OK);
    }

    public ResponseEntity<String> updateCert(Long certId, Cert cert) {

       Cert oldCert = repo.findById(certId).orElseThrow(() ->
               new ResourceNotFoundException("Cert with CertID: " + certId + " not found"));

        if(cert.getCertName().isBlank() || cert.getCertPdf().length == 0){
            return new ResponseEntity<>("Missing Info", HttpStatus.BAD_REQUEST);
        }

        oldCert.setCertName(cert.getCertName());
        oldCert.setCertPdf(cert.getCertPdf());

        repo.save(oldCert);
        return new ResponseEntity<>("Successfully Updated !!!", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteCertById(Long id) {
        if(!repo.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Cert with CertID: " + id + " not found"
            );
        }
            repo.deleteById(id);
            return new ResponseEntity<>("Successfully Deleted this Cert!!", HttpStatus.OK);
    }



    public ResponseEntity<byte[]> getCertPDF(Long certId) {

        Cert cert = repo.findById(certId).orElseThrow(() ->
                new ResourceNotFoundException(
                "Cert with CertID: " + certId + " not found"
        ));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .contentType(MediaType.APPLICATION_PDF)
                .body(cert.getCertPdf());
    }
}
