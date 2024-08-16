package org.example.administrationservice.models.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContractResDTO {
    private String id;
    private String contractCode;
    private String houseCode;
    private String roomCode;
    private String fullName;
    private String identityNumber;
    private String status;
}
