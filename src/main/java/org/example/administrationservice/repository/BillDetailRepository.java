package org.example.administrationservice.repository;

import org.example.administrationservice.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, String> {

    List<BillDetail> findAllByBillIdIn(List<String> billIds);

    @Modifying
    @Transactional
    void deleteAllByContractIdAndMonthAndYear(String contractId, Integer month, Integer year);
}