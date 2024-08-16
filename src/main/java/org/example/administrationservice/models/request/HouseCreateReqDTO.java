package org.example.administrationservice.models.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HouseCreateReqDTO {
    private String code;
    private String position;
    private Integer roomNumber;
    List<RoomCreateReqDTO> roomCreateReqDTOS;
}
