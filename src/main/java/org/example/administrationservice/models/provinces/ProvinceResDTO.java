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
public class ProvinceResDTO {
    @JsonProperty("province_id")
    private String provinceId;
    @JsonProperty("province_name")
    private String provinceName;
    @JsonProperty("province_type")
    private String provinceType;
}
