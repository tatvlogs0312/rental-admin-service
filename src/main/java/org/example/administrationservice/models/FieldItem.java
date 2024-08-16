package org.example.administrationservice.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FieldItem {
    private String message;
    private String name;
}
