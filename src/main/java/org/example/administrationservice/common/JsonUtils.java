package org.example.administrationservice.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

    public static String toJson(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            log.info("JsonUtils toJson - Exception: {}", e.getMessage());
        }
        return "";
    }

    public static <T> T fromJson(String json, Class<T> type) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            log.info("JsonUtils fromJson - Exception: {}", e.getMessage());
        }
        return null;
    }
}