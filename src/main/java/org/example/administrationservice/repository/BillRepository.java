package org.example.administrationservice.repository;

import org.example.administrationservice.entity.Bill;
import org.example.administrationservice.models.icustom.IBillSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, String> {

    Optional<Bill> findFirstByContractIdAndYearAndMonthAndStatus(String contractId, Integer year, Integer month, String status);

    @Query(value = "select b.id as id,\n" +
            "       b.created_date as createDate,\n" +
            "       b.contract_id as contractId,\n" +
            "       b.payment_date as paymentDate,\n" +
            "       b.status as status,\n" +
            "       b.month as month,\n" +
            "       b.year as year,\n" +
            "       b.total as total,\n" +
            "       c.contract_code as contractCode,\n" +
            "       r.room_code as roomCode,\n" +
            "       r.name as name\n" +
            "from bill b\n" +
            "         join public.contract c on c.id = b.contract_id\n" +
            "         join room r on c.room_code = r.room_code\n" +
            "where 1 = 1\n" +
            "and (:billId = 'ALL' or b.id = :billId)\n" +
            "and (:contractId = 'ALL' or b.contract_id = :contractId)\n" +
            "and b.month = :month\n" +
            "and b.year = :year\n" +
            "and (:status = 'ALL' or b.status = :status)", nativeQuery = true)
    Page<IBillSearchDTO> searchBill(String billId, String contractId, Integer month, Integer year, String status, Pageable page);

    @Modifying
    @Transactional
    void deleteAllByContractIdAndMonthAndYear(String contractId, Integer month, Integer year);
}