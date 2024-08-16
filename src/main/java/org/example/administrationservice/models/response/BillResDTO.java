package org.example.administrationservice.models.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillResDTO {
    private String id;
    private Integer month;
    private Integer year;
    private String contractCode;
    private String roomCode;
    private String roomName;
    private String status;
    private Long total;
    private List<BillDetailResDTO> billDetails;
}
