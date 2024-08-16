package org.example.administrationservice.repository;

import org.example.administrationservice.entity.Room;
import org.example.administrationservice.models.icustom.IRoomInfoResDTO;
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
public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findAllByHouseCode(String houseCode);

    @Query(value = "select * from room where house_code = :houseCode and room_status = :status order by room_code asc", nativeQuery = true)
    Page<Room> findAllByHouseCodePage(String houseCode, String status, Pageable page);

    @Query(value = "select *\n"
            + "from room r\n"
            + "where (r.house_code like '%' || :houseCode || '%'\n"
            + "and r.room_code like '%' || :roomCode || '%')\n"
            + "and r.room_status = :status", nativeQuery = true)
    Page<Room> findAllByHouseCodeAndRoomCodeAndStaAndRoomStatus(String houseCode, String roomCode, String status, Pageable page);

    Optional<Room> findFirstByRoomCode(String roomCode);

    @Query(value = "select r.house_code     as houseCode,\n"
            + "       r.room_code      as roomCode,\n"
            + "       r.name           as roomName,\n"
            + "       r.room_position  as roomPosition,\n"
            + "       h.house_position as housePosition,\n"
            + "       r.room_price     as roomPrice\n"
            + "from room r\n"
            + "         join public.house h on r.house_code = h.house_code\n"
            + "where r.room_code = :roomCode\n"
            + "limit 1", nativeQuery = true)
    Optional<IRoomInfoResDTO> getRoomInfoByCode(String roomCode);

    @Query(value = "select r.house_code     as houseCode,\n" +
            "       r.room_code      as roomCode,\n" +
            "       r.name           as roomName,\n" +
            "       r.room_position  as roomPosition,\n" +
            "       h.house_position as housePosition,\n" +
            "       r.room_price     as roomPrice\n" +
            "from room r\n" +
            "         join house h on r.house_code = h.house_code\n" +
            "         join contract c on c.room_code = r.room_code\n" +
            "where c.id in :contractId", nativeQuery = true)
    List<IRoomInfoResDTO> getRoomsByContracts(List<String> contractId);

    @Modifying
    @Transactional
    @Query(value = "update room set room_status = :roomStatus where room_code = :roomCode", nativeQuery = true)
    void updateStatusByRoomCode(String roomCode, String roomStatus);

    @Modifying
    @Transactional
    void deleteAllByHouseCode(String houseCode);

    @Modifying
    @Transactional
    void deleteAllByRoomCode(String roomCode);
}