package org.example.administrationservice.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UtilityDTO {
    private String id;
    private String code;
    private String name;
    private String price;
}
