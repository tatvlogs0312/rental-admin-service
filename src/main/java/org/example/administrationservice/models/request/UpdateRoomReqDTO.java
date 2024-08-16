package org.example.administrationservice.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateRoomReqDTO {
    private String id;
    private String roomCode;
    private String roomName;
    private String status;
    private String name;
    private String position;
    private String price;
}
