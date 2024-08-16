package org.example.administrationservice.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractSearchReqDTO {
    private String contractCode;
    private String houseCode;
    private String roomCode;
    private String identityNumber;
    private String fullName;
    private String status;
    private int page;
    private int size;
}
