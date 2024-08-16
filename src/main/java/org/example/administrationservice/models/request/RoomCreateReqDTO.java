package org.example.administrationservice.models.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomCreateReqDTO {
    private String houseCode;
    private String roomCode;
    private String name;
    private String position;
    private long price;
}
