package org.example.administrationservice.models.response;

import lombok.*;
import org.example.administrationservice.models.PageInfo;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillSearchResDTO {
    private List<BillResDTO> bills;
    private PageInfo page;
}
