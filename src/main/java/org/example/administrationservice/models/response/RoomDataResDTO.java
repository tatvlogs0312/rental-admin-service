package org.example.administrationservice.models.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomDataResDTO {
    private String id;
    private String code;
    private String houseCode;
    private String name;
    private String position;
    private String status;
    private Long price;
}
