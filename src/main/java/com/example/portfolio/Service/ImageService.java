package com.example.portfolio.Service;

import com.example.portfolio.Exception.ResourceNotFoundException;
import com.example.portfolio.Model.Image;
import com.example.portfolio.Repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepo repo;

    public ResponseEntity<byte[]> getImage(Long thumbnailId) {

        Image thumbnail = repo.findById(thumbnailId).orElseThrow(() ->
                new ResourceNotFoundException("Image with id" + thumbnailId + "not found."));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(thumbnail.getType()))
                .body(thumbnail.getImageData());
    }
}
