package org.example.administrationservice.models.icustom;

import java.time.LocalDate;

public interface IBillSearchDTO {
    String getId();
    LocalDate getCreateDate();
    String getContractId();
    String getPaymentDate();
    String getStatus();
    Integer getMonth();
    Integer getYear();
    Long getTotal();
    String getContractCode();
    String getRoomCode();
    String getName();
}
