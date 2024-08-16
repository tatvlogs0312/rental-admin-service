package org.example.administrationservice.models.key_cloak;

import java.util.List;
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
public class KeyCloakNewUserReqDTO {
    private String firstName;
    private String lastName;
    private String username;
    private Boolean enabled;
    private List<KeyCloakCredentialsDTO> credentials;
}
