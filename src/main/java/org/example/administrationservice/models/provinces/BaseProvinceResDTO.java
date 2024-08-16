package org.example.administrationservice.models.provinces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseProvinceResDTO {
    private List<ProvinceResDTO> results = new ArrayList<>();
}