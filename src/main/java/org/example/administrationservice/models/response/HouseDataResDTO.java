package org.example.administrationservice.models.response;

import lombok.*;
import org.example.administrationservice.models.PageInfo;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HouseDataResDTO {
    private String id;
    private String code;
    private String position;
    private Integer total;
    private Integer available;
    private List<RoomDataResDTO> roomData;
    private PageInfo page = new PageInfo();
}
