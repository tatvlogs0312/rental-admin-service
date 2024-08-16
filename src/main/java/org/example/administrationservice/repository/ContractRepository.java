package org.example.administrationservice.repository;

import org.example.administrationservice.entity.Contract;
import org.example.administrationservice.models.icustom.IContractSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
    Optional<Contract> findFirstByContractCode(String contractCode);

    @Query(value = "select c.id                   as id,\n" +
            "       c.contract_code        as contractCode,\n" +
            "       h.house_code           as houseCode,\n" +
            "       r.room_code            as roomCode,\n" +
            "       c.full_name            as fullName,\n" +
            "       c.identity_card_number as identityNumber,\n" +
            "       c.status               as status\n" +
            "from contract c\n" +
            "         join public.room r on c.room_code = r.room_code\n" +
            "         join public.house h on r.house_code = h.house_code\n" +
            "where 1 = 1\n" +
            "  AND r.room_code like '%' || :roomCode || '%'\n" +
            "  AND h.house_code like '%' || :houseCode || '%'\n" +
            "  AND c.identity_card_number like '%' || :identityNumber || '%'\n" +
            "  AND lower(unaccent(c.full_name)) like lower(unaccent('%' || :name || '%'))\n" +
            "  AND (:status = 'ALL' or c.status = :status)\n" +
            "order by update_date desc", nativeQuery = true)
    Page<IContractSearchDTO> searchContract(String roomCode, String houseCode, String identityNumber, String name, String status, Pageable page);
}