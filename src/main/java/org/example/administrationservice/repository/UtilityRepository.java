package org.example.administrationservice.repository;

import org.example.administrationservice.entity.Utility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilityRepository extends JpaRepository<Utility, String> {
    Optional<Utility> findFirstByCode(String code);

    List<Utility> findAllByCodeIn(List<String> utilityCodes);

    @Modifying
    @Transactional
    void deleteAllByCode(String code);
}