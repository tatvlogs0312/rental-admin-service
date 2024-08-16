package org.example.administrationservice.models.response;

import lombok.*;
import org.example.administrationservice.entity.BillDetail;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillDetailResDTO {
    private String id;
    private String code;
    private String name;
    private Integer numberUse;
    private Long totalPrice;

    public BillDetailResDTO(BillDetail billDetail) {
        this.id = billDetail.getId();
        this.code = billDetail.getUtilityCode();
        this.numberUse = billDetail.getNumber();
        this.totalPrice = billDetail.getTotalPrice();
    }
}
