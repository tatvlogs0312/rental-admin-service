package org.example.administrationservice.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PageInfo {
    private int totalPage = 0;
    private int currentPage = 0;
    private long totalData = 0;
}
