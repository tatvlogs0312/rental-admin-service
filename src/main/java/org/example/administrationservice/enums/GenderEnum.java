package org.example.administrationservice.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum GenderEnum {
    MALE("Nam"),
    FEMALE("Nữ")
    ;

    private String value;

    private static final Map<String, GenderEnum> map = new HashMap<>();

    static {
        Arrays.stream(values()).forEach(m -> map.put(m.value, m));
    }

    GenderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GenderEnum from(String value) {
        return map.get(value);
    }
}
