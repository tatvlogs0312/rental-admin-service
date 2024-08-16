package org.example.administrationservice.repository;

import org.example.administrationservice.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, String> {
    @Query("select h from house h where h.houseCode = ?1")
    Optional<House> findFirstByHouseCode(String houseCode);

    @Modifying
    @Transactional
    @Query(value = "update house set room_available = room_available - 1 where house_code = :houseCode", nativeQuery = true)
    void updateHouseRentedIncrease(String houseCode);

    @Modifying
    @Transactional
    @Query(value = "update house set room_available = room_available + 1 where house_code = :houseCode", nativeQuery = true)
    void updateHouseRentedDecrease(String houseCode);

    @Modifying
    @Transactional
    void deleteAllByHouseCode(String houseCode);
}