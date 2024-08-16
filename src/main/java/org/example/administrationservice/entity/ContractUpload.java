package org.example.administrationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "contract_upload")
@Table(name = "contract_upload")
public class ContractUpload {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "contract_id")
    private String contractId;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "path")
    private String path;

}