package org.example.administrationservice.repository;

import java.util.List;
import org.example.administrationservice.entity.ContractUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractUploadRepository extends JpaRepository<ContractUpload, String> {
    List<ContractUpload> findAllByContractId(String contractId);
}