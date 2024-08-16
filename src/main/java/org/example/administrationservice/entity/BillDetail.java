package org.example.administrationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "bill_detail")
@Entity(name = "bill_detail")
public class BillDetail {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "utility_code")
    private String utilityCode;

    @Column(name = "number")
    private Integer number;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "note")
    private String note;

    @Column(name = "bill_id")
    private String billId;

    @Column(name = "contract_id")
    private String contractId;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;
}
