package org.example.administrationservice.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractUploadReqDTO {
    private String contractId;
    private String contractCode;
    private MultipartFile front;
    private MultipartFile back;
}
