package org.example.administrationservice.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractReqDTO {
    private String contractCode;
    private String fullName;
    private String birthdate;
    private String identityCardNumber;
    private String gender;
    private String placeOfOrigin;
    private String placeOfResidence;
    private String phoneNumber;
    private String otherPhoneNumber;
    private String roomCode;
    private String status;
}
