package org.example.administrationservice.enums;

public enum BillStatusEnum {
    PENDING("Chờ thanh toán"),
    PAID("Đã thanh toán");

    private String value;

    BillStatusEnum(String value) {
        this.value = value;
    }
}
