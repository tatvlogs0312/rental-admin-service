package org.example.administrationservice.models.key_cloak;

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
public class KeyCloakCredentialsDTO {
    private String type;
    private String value;
    private Boolean temporary;
}
