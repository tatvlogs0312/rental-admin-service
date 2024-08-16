package org.example.administrationservice.service.admin.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.entity.House;
import org.example.administrationservice.entity.Room;
import org.example.administrationservice.enums.RoomStatusEnum;
import org.example.administrationservice.exception.ApplicationException;
import org.example.administrationservice.exception.ExceptionEnums;
import org.example.administrationservice.models.PageInfo;
import org.example.administrationservice.models.request.HouseCreateReqDTO;
import org.example.administrationservice.models.request.HouseSearchReqDTO;
import org.example.administrationservice.models.request.RoomCreateReqDTO;
import org.example.administrationservice.models.request.RoomSearchReqDTO;
import org.example.administrationservice.models.request.UpdateRoomReqDTO;
import org.example.administrationservice.models.response.HouseDataResDTO;
import org.example.administrationservice.models.response.HouseResDTO;
import org.example.administrationservice.models.response.RoomDataResDTO;
import org.example.administrationservice.repository.HouseRepository;
import org.example.administrationservice.repository.RoomRepository;
import org.example.administrationservice.service.admin.HouseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;

    /**
     * Tạo nhà cho thuê
     *
     * @param req
     */
    @Override
    public void insertHouse(HouseCreateReqDTO req) {
        if (houseRepository.findFirstByHouseCode(req.getCode()).isPresent()) {
            throw new ApplicationException(ExceptionEnums.EX_HOUSE_EXIST.getMessage());
        }

        String houseId = UUID.randomUUID().toString();
        House newHouse = new House();
        newHouse.setId(houseId);
        newHouse.setHouseCode(req.getCode());
        newHouse.setHousePosition(req.getPosition());
        newHouse.setRoomNumber(req.getRoomNumber());
        newHouse.setRoomAvailable(req.getRoomNumber());

        if (CollectionUtils.isEmpty(req.getRoomCreateReqDTOS())) {
            List<Room> newRooms = new ArrayList<>();
            for (RoomCreateReqDTO roomReq : req.getRoomCreateReqDTOS()) {
                Room newRoom = new Room();
                newRoom.setId(UUID.randomUUID().toString());
                newRoom.setHouseCode(newHouse.getHouseCode());
                newRoom.setRoomCode(roomReq.getRoomCode());
                newRoom.setRoomStatus(RoomStatusEnum.EMPTY.name());
                newRoom.setName(roomReq.getName());
                newRoom.setRoomPosition(roomReq.getPosition());

                newRooms.add(newRoom);
            }
            roomRepository.saveAll(newRooms);
        }
        houseRepository.save(newHouse);

    }


    /**
     * Xóa nhà cho thuê
     *
     * @param houseCode
     */
    @Override
    public void deleteHouse(String houseCode) {
        if (houseRepository.findFirstByHouseCode(houseCode).isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_HOUSE_NOT_EXIST);
        }

        houseRepository.deleteAllByHouseCode(houseCode);
        roomRepository.deleteAllByHouseCode(houseCode);
    }

    /**
     * Thêm phòng cho nhà
     *
     * @param req
     */
    @Override
    public void insertRoomByHouse(RoomCreateReqDTO req) {
        Optional<House> houseOptional = houseRepository.findFirstByHouseCode(req.getHouseCode());
        if (houseOptional.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_HOUSE_NOT_EXIST);
        }

        List<Room> rooms = roomRepository.findAllByHouseCode(req.getHouseCode());
        if (Objects.equals(houseOptional.get().getRoomNumber(), rooms.size())) {
            throw new ApplicationException(ExceptionEnums.EX_HOUSE_NOT_EMPTY);
        }

        Room newRoom = new Room();
        newRoom.setId(UUID.randomUUID().toString());
        newRoom.setHouseCode(req.getHouseCode());
        newRoom.setRoomCode(req.getRoomCode());
        newRoom.setRoomStatus(RoomStatusEnum.EMPTY.name());
        newRoom.setName(req.getName());
        newRoom.setRoomPosition(req.getPosition());
        newRoom.setRoomPrice(req.getPrice());

        roomRepository.save(newRoom);
    }

    /**
     * Danh sách nhà
     *
     * @return
     */
    @Override
    public List<HouseResDTO> getHouses() {
        List<HouseResDTO> houseResDTOS = new ArrayList<>();

        List<House> houses = houseRepository.findAll();
        if (!CollectionUtils.isEmpty(houses)) {
            houses.forEach(x -> {
                HouseResDTO houseResDTO = HouseResDTO.builder().id(x.getId()).code(x.getHouseCode())
                        .position(x.getHousePosition()).build();

                houseResDTOS.add(houseResDTO);
            });
        }

        return houseResDTOS;
    }

    /**
     * Lấy danh sách phòng theo nhà
     *
     * @param req
     * @return
     */
    @Override
    public HouseDataResDTO getRoomByHouseCode(HouseSearchReqDTO req) {
        Optional<House> houseOptional = houseRepository.findFirstByHouseCode(req.getHouseCode());
        if (houseOptional.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_HOUSE_NOT_EXIST);
        }

        House house = houseOptional.get();
        HouseDataResDTO houseResDTO = HouseDataResDTO.builder()
                .id(house.getId())
                .code(house.getHouseCode())
                .position(house.getHousePosition())
                .available(house.getRoomAvailable())
                .total(house.getRoomNumber()).build();

        List<RoomDataResDTO> roomDataResDTOS = new ArrayList<>();

        Pageable page = PageRequest.of(req.getPage(), req.getSize());
        Page<Room> roomPage = roomRepository.findAllByHouseCodePage(req.getHouseCode(),
                req.getStatus(), page);
        List<Room> roomsExist = roomPage.stream().toList();
        if (!CollectionUtils.isEmpty(roomsExist)) {
            roomsExist.forEach(room -> {
                RoomDataResDTO roomDataResDTO = RoomDataResDTO.builder()
                        .id(room.getId())
                        .code(room.getRoomCode())
                        .name(room.getName())
                        .position(room.getRoomPosition())
                        .status(room.getRoomStatus())
                        .price(room.getRoomPrice())
                        .build();

                roomDataResDTOS.add(roomDataResDTO);
            });
        }

        roomDataResDTOS.sort(Comparator.comparing(RoomDataResDTO::getCode));
        houseResDTO.setRoomData(roomDataResDTOS);
        houseResDTO.setPage(PageInfo.builder().totalPage(roomPage.getTotalPages())
                .currentPage(page.getPageNumber() + 1).totalData(roomPage.getTotalElements())
                .build());

        return houseResDTO;
    }

    /**
     * Update phòng
     *
     * @param req
     */
    @Override
    public void updateRoom(RoomCreateReqDTO req) {
        Optional<Room> roomOptional = roomRepository.findFirstByRoomCode(req.getRoomCode());
        if (roomOptional.isEmpty() || Objects.equals(roomOptional.get().getRoomStatus(),
                RoomStatusEnum.RENTED.name())) {
            throw new ApplicationException(ExceptionEnums.EX_ROOM_UPDATE_ERROR_2);
        }

        Room room = roomOptional.get();
        room.setName(req.getName());
        room.setRoomPosition(req.getPosition());
        room.setRoomPrice(req.getPrice());

        roomRepository.save(room);
    }

    /**
     * Xóa phòng
     *
     * @param roomCode
     */
    @Override
    public void deleteRoom(String roomCode) {
        Optional<Room> roomOptional = roomRepository.findFirstByRoomCode(roomCode);
        if (roomOptional.isEmpty() || Objects.equals(roomOptional.get().getRoomStatus(),
                RoomStatusEnum.RENTED.name())) {
            throw new ApplicationException(ExceptionEnums.EX_ROOM_DELETE_ERROR_2);
        }

        roomRepository.deleteAllByRoomCode(roomCode);
    }

    /**
     * Update trạng thái phòng
     *
     * @param req
     */
    @Override
    public void updateStatusRoom(UpdateRoomReqDTO req) {
        RoomStatusEnum statusEnum = RoomStatusEnum.from(req.getStatus());
        if (Objects.isNull(statusEnum)) {
            throw new ApplicationException(ExceptionEnums.EX_CONTRACT_STATUS_NOT_VALID);
        }

        Optional<Room> roomOptional = roomRepository.findFirstByRoomCode(req.getRoomCode());
        if (roomOptional.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_ROOM_NOT_EXIST);
        }

        Room room = roomOptional.get();
        room.setRoomStatus(statusEnum.name());

        roomRepository.save(room);
    }

    /**
     * Danh sách tất cả mã nhà
     *
     * @return
     */
    @Override
    public List<String> getAllHouseCode() {
        List<House> houses = houseRepository.findAll();
        if (!CollectionUtils.isEmpty(houses)) {
            return houses.stream().map(House::getHouseCode).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Danh sách tất cả mã phòng
     *
     * @param houseCode
     * @return
     */
    @Override
    public List<String> getAllRoomCodeByHouse(String houseCode) {
        List<Room> rooms = roomRepository.findAllByHouseCode(houseCode);
        if (!CollectionUtils.isEmpty(rooms)) {
            return rooms.stream().map(Room::getRoomCode).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Tìm kiếm phòng
     *
     * @param req
     * @return
     */
    @Override
    public HouseDataResDTO searchRoom(RoomSearchReqDTO req) {
        HouseDataResDTO res = new HouseDataResDTO();
        Pageable page = PageRequest.of(req.getPage(), req.getSize());
        Page<Room> roomPage = roomRepository.findAllByHouseCodeAndRoomCodeAndStaAndRoomStatus(
                req.getHouseCode(), req.getRoomCode(), req.getStatus(), page);

        List<RoomDataResDTO> roomDataResDTOS = new ArrayList<>();

        List<Room> roomsExist = roomPage.stream().toList();
        if (!CollectionUtils.isEmpty(roomsExist)) {
            roomsExist.forEach(room -> {
                RoomDataResDTO roomDataResDTO = RoomDataResDTO.builder()
                        .id(room.getId())
                        .code(room.getRoomCode())
                        .houseCode(room.getHouseCode())
                        .name(room.getName())
                        .position(room.getRoomPosition())
                        .status(room.getRoomStatus())
                        .price(room.getRoomPrice())
                        .build();

                roomDataResDTOS.add(roomDataResDTO);
            });

            res.setRoomData(roomDataResDTOS);
            res.setPage(PageInfo.builder().totalPage(roomPage.getTotalPages())
                    .currentPage(page.getPageNumber() + 1).totalData(roomPage.getTotalElements())
                    .build());
        } else {
            res.setRoomData(new ArrayList<>());
            res.setPage(PageInfo.builder().totalPage(roomPage.getTotalPages())
                    .currentPage(page.getPageNumber() + 1).totalData(roomPage.getTotalElements())
                    .build());
        }

        roomDataResDTOS.sort(Comparator.comparing(RoomDataResDTO::getCode));
        return res;
    }

    
}
