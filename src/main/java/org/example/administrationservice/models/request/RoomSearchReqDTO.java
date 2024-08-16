package org.example.administrationservice.models.request;

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
public class RoomSearchReqDTO {
    private String houseCode;
    private String roomCode;
    private String status;
    private int page;
    private int size;
}
