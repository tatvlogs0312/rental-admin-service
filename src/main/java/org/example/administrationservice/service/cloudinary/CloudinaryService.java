package org.example.administrationservice.service.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    void upload(MultipartFile file);
}
