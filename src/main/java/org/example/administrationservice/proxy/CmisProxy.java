package org.example.administrationservice.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.exception.ApplicationException;
import org.example.administrationservice.exception.ExceptionEnums;
import org.example.administrationservice.models.request.CmisUploadReq;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class CmisProxy {

    private final BaseProxy baseProxy;

    @Value("${custom.properties.cmis-url}")
    private String CMIS_URL;

    public String upload(CmisUploadReq req) {
        String url = CMIS_URL + "/file/v1/upload";
        try {
            MultiValueMap<Object, Object> maps = new LinkedMultiValueMap<>();
            maps.add("file", new FileSystemResource(req.getFile()));
            maps.add("fileType", "IMAGE");
            return baseProxy.post(url, baseProxy.initHeaderFormData(), maps, String.class);
        } catch (Exception e) {
            throw new ApplicationException(ExceptionEnums.EX_INTERNAL_SERVER.getMessage());
        }
    }

    public byte[] getIdentity(String id) {
        try {
            String url = CMIS_URL + "/file/v1/download/image/" + id;
            return baseProxy.get(url, baseProxy.initHeader(), byte[].class);
        } catch (Exception e) {
            throw new ApplicationException(ExceptionEnums.EX_INTERNAL_SERVER.getMessage());
        }
    }
}
