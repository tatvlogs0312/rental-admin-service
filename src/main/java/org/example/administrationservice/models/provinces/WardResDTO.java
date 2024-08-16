package org.example.administrationservice.models.provinces;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WardResDTO {
    @JsonProperty("district_id")
    private String districtId;
    @JsonProperty("ward_id")
    private String wardId;
    @JsonProperty("ward_name")
    private String wardName;
    @JsonProperty("ward_type")
    private String wardType;
}
