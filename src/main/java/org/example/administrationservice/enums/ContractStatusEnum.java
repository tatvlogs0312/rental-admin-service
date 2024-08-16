package org.example.administrationservice.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum ContractStatusEnum {
    DRAFT("Nháp"),
    EXPIRE("Hết hiệu lực"),
    EFFECTIVE("Có hiệu lực"),
    ;

    private String value;

    private static final Map<String, ContractStatusEnum> map = new HashMap<>();

    static {
        Arrays.stream(values()).forEach(m -> map.put(m.value, m));
    }

    ContractStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ContractStatusEnum from(String value) {
        return map.get(value);
    }
}
