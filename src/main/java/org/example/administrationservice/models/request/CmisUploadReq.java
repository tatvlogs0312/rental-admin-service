package org.example.administrationservice.models.request;

import lombok.*;

import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CmisUploadReq {
    private File file;
    private String fileType;
}
