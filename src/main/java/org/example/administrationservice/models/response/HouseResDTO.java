package org.example.administrationservice.models.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HouseResDTO {
    private String id;
    private String code;
    private String position;
    private List<RoomDataResDTO> rooms;
}
