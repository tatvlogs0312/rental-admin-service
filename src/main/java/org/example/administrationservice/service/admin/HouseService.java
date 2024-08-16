package org.example.administrationservice.service.admin;

import org.example.administrationservice.models.request.HouseCreateReqDTO;
import org.example.administrationservice.models.request.HouseSearchReqDTO;
import org.example.administrationservice.models.request.RoomCreateReqDTO;
import org.example.administrationservice.models.request.RoomSearchReqDTO;
import org.example.administrationservice.models.request.UpdateRoomReqDTO;
import org.example.administrationservice.models.response.HouseDataResDTO;
import org.example.administrationservice.models.response.HouseResDTO;

import java.util.List;

public interface HouseService {
    void insertHouse(HouseCreateReqDTO req);
    void deleteHouse(String houseCode);
    void insertRoomByHouse(RoomCreateReqDTO req);
    List<HouseResDTO> getHouses();
    HouseDataResDTO getRoomByHouseCode(HouseSearchReqDTO req);
    void updateRoom(RoomCreateReqDTO req);
    void deleteRoom(String roomCode);
    void updateStatusRoom(UpdateRoomReqDTO req);
    List<String> getAllHouseCode();
    List<String> getAllRoomCodeByHouse(String houseCode);
    HouseDataResDTO searchRoom(RoomSearchReqDTO req);
}
