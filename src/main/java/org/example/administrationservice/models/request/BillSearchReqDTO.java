package org.example.administrationservice.models.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillSearchReqDTO {
    private String billId;
    private String contractId;
    private String status;
    private int month;
    private int year;
    private int page;
    private int size;
}
