package org.example.administrationservice.models.response;

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
public class RoomInfoResDTO {
    private String houseCode;
    private String roomCode;
    private String roomName;
    private String housePosition;
    private String roomPosition;
    private String roomPrice;
}
