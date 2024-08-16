package org.example.administrationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "bill")
@Table(name = "bill")
public class Bill {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "contract_id")
    private String contractId;

    @Column(name = "total")
    private Long total;

    @Column(name = "status")
    private String status;
}
