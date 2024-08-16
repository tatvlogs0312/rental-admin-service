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
public class DistrictResDTO {
    @JsonProperty("province_id")
    private String provinceId;
    @JsonProperty("district_id")
    private String districtId;
    @JsonProperty("district_name")
    private String districtName;
    @JsonProperty("district_type")
    private String districtType;
}
