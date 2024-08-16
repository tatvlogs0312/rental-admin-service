package org.example.administrationservice.controller.cloudinary;

import lombok.RequiredArgsConstructor;
import org.example.administrationservice.service.cloudinary.CloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cloudinary")
@RequiredArgsConstructor
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam MultipartFile file) {
        cloudinaryService.upload(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
