package org.example.administrationservice.models.response;

import lombok.*;
import org.example.administrationservice.models.PageInfo;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContractPageResDTO {
    private List<ContractResDTO> contracts = new ArrayList<>();
    private PageInfo page;
}
