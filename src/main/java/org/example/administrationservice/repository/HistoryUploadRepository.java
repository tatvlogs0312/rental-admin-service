package org.example.administrationservice.repository;

import org.example.administrationservice.entity.HistoryUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryUploadRepository extends JpaRepository<HistoryUpload, String> {

}