package org.example.administrationservice.models.request;

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
public class BillCreateReqDTO {
    private String billId;
    private String contractId;
    private String contractCode;
    private String roomCode;
    private Integer month;
    private Integer year;
    private Boolean isContinueRent;
    List<BillDetailReqDTO> details;
}
