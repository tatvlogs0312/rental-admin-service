package org.example.administrationservice.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum RoomStatusEnum {
    EMPTY("Còn trống"),
    RENTED("Đã cho thuê")
    ;

    private String value;

    private static final Map<String, RoomStatusEnum> map = new HashMap<>();
    static {
        Arrays.stream(values()).forEach(m -> map.put(m.value, m));
    }

    RoomStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoomStatusEnum from(String value) {
        return map.get(value);
    }
}
