package org.example.administrationservice.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.administrationservice.exception.ApplicationException;
import org.example.administrationservice.exception.ExceptionEnums;
import org.example.administrationservice.models.FieldItem;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public class ValidatorChain {
    private final List<FieldItem> itemList;

    private ValidatorChain() {
        itemList = new ArrayList<>();
    }

    public static ValidatorChain builder() {
        return new ValidatorChain();
    }

    public void build() {
        if (!itemList.isEmpty()) {
//            List<FieldItem> collect = itemList.stream()
//                    .distinct()
//                    .toList();
            throw new ApplicationException(ExceptionEnums.DATA_NOT_VALID);
        }
    }

    private FieldItem setFieldItem(String fieldName, String message) {
        return FieldItem.builder()
                .message(message)
                .name(fieldName)
                .build();
    }

    public ValidatorChain validField(String toValid, String fieldName) {
        if (StringUtils.isBlank(toValid)) {
            itemList.add(setFieldItem(fieldName, "Trường này là bắt buộc"));
        }
        return this;
    }

    public ValidatorChain validList(Collection<?> toValid, String fieldName) {
        if (CollectionUtils.isEmpty(toValid)) {
            itemList.add(setFieldItem(fieldName, "Trường này là bắt buộc"));
        }
        return this;
    }

    public ValidatorChain validObject(Object toValid, String fieldName) {
        if (Objects.isNull(toValid)) {
            itemList.add(setFieldItem(fieldName, "Trường này là bắt buộc"));
        }
        return this;
    }

    public ValidatorChain validAllFields(Object dto) {
        Class<?> aClass = dto.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        try {
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Object o = declaredField.get(dto);
                String fieldName = declaredField.getName();
                if (o instanceof String) {
                    String fieldValue = (String) o;
                    validField(fieldValue, fieldName);
                } else if (o instanceof List<?> && CollectionUtils.isEmpty((List) o)) {
                    String messageContent = "Trường này là bắt buộc";
                    itemList.add(setFieldItem(fieldName, messageContent));
                } else if (o == null) {
                    String message = "Trường này là bắt buộc";
                    itemList.add(setFieldItem(fieldName, message));
                }
            }
        } catch (IllegalAccessException e) {
            log.error("validAllFields: {}", e.getMessage());
        }
        return this;
    }

    public ValidatorChain validAllIgnoreFields(Object dto, String... fieldsIgnore) {
        Class<?> aClass = dto.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        try {
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Object o = declaredField.get(dto);
                String fieldName = declaredField.getName();
                if (Arrays.stream(fieldsIgnore)
                        .noneMatch(fieldName::equals)) {
                    if (o instanceof String) {
                        String fieldValue = (String) o;
                        validField(fieldValue, fieldName);
                    } else if (o instanceof List && CollectionUtils.isEmpty((List) o)){
                        itemList.add(setFieldItem(fieldName, "Trường này là bắt buộc"));
                    } else if (o == null) {
                        itemList.add(setFieldItem(fieldName,  "Trường này là bắt buộc"));
                    }
                }

            }
        } catch (IllegalAccessException e) {
            log.error("validAllIgnoreFields: {}", e.getMessage());
        }
        return this;
    }

    public ValidatorChain validateFile(MultipartFile file, String fieldName) {
        if(null == file || file.isEmpty()) {
            itemList.add(setFieldItem(fieldName, "Vui lòng chọn ảnh"));
        }
        return this;
    }
}
