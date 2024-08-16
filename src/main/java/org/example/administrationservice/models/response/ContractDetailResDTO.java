package org.example.administrationservice.models.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContractDetailResDTO {
    private String id;
    private String contractCode;
    private String fullName;
    private String birthdate;
    private String genderCode;
    private String identityCardNumber;
    private String placeOfOrigin;
    private String placeOfResidence;
    private String createDate;
    private String phoneNumber;
    private String otherPhoneNumber;
    private String status;
    private List<ContractUploadResDTO> files;
    private RoomInfoResDTO roomInfo;
}
