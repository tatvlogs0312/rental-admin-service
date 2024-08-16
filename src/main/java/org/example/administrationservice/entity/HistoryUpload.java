package org.example.administrationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "history_upload")
@Table(name = "history_upload")
public class HistoryUpload {

    @Id
    private String id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "data", columnDefinition = "TEXT")
    private String data;
}
