package org.example.administrationservice.service.cloudinary.impl;

import com.cloudinary.Cloudinary;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.common.JsonUtils;
import org.example.administrationservice.service.cloudinary.CloudinaryService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public void upload(MultipartFile file) {
        Map<String, String> params = new HashMap<>();
        params.put("folder", "");
        params.put("resource_type", "image");

        try {
            Object result = cloudinary.uploader().upload(file.getBytes(), params);
            log.info("result: {}", JsonUtils.toJson(result));
        } catch (Exception exception) {
            log.error("ex: " + exception.getMessage());
        }
    }
}
