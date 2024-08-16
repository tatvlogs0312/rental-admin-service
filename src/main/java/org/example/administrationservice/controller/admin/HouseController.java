package org.example.administrationservice.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.models.request.HouseCreateReqDTO;
import org.example.administrationservice.models.request.HouseSearchReqDTO;
import org.example.administrationservice.models.request.RoomCreateReqDTO;
import org.example.administrationservice.models.request.RoomSearchReqDTO;
import org.example.administrationservice.models.request.UpdateRoomReqDTO;
import org.example.administrationservice.service.admin.HouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/house")
@RequiredArgsConstructor
@Slf4j
public class HouseController {

    private final HouseService houseService;

    @Operation(summary = "Api thêm nhà")
    @PostMapping(value = "/add-house", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertHouse(@RequestBody HouseCreateReqDTO req) {
        houseService.insertHouse(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Api xóa nhà")
    @DeleteMapping(value = "/delete-house")
    public ResponseEntity<Object> deleteHouse(@RequestBody HouseCreateReqDTO req) {
        houseService.deleteHouse(req.getCode());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Api thêm phòng")
    @PostMapping(value = "/add-room", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertRoomByHouse(@RequestBody RoomCreateReqDTO req) {
        houseService.insertRoomByHouse(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Api lấy danh sách nhà")
    @GetMapping("/get-house")
    public ResponseEntity<Object> getHouse() {
        return new ResponseEntity<>(houseService.getHouses(), HttpStatus.OK);
    }

    @Operation(summary = "Api chi tiết nhà - tìm kiếm phòng")
    @PostMapping(value = "/get-room", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getRoom(@RequestBody HouseSearchReqDTO req) {
        return new ResponseEntity<>(houseService.getRoomByHouseCode(req), HttpStatus.OK);
    }

    @Operation(summary = "Api sửa thông tin phòng")
    @PutMapping(value = "/update-room", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateRoomByHouse(@RequestBody RoomCreateReqDTO req) {
        houseService.updateRoom(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Api xóa thông tin phòng")
    @DeleteMapping(value = "/delete-room", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteRoomByHouse(@RequestBody RoomCreateReqDTO req) {
        houseService.deleteRoom(req.getRoomCode());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Api sửa thông tin trạng thái phòng")
    @PutMapping(value = "/update-room-status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateRoomStatus(@RequestBody UpdateRoomReqDTO req) {
        houseService.updateStatusRoom(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Api danh sách mã nhà")
    @GetMapping(value = "/get-house-codes")
    public ResponseEntity<Object> getHouseCodes() {
        return new ResponseEntity<>(houseService.getAllHouseCode(), HttpStatus.OK);
    }

    @Operation(summary = "Api danh sách mã phòng")
    @GetMapping(value = "/get-room-codes")
    public ResponseEntity<Object> getRoomCodes(@RequestParam String houseCode) {
        return new ResponseEntity<>(houseService.getAllRoomCodeByHouse(houseCode), HttpStatus.OK);
    }

    @Operation(summary = "Api tìm kiếm phòng")
    @PostMapping(value = "/room-search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchRoom(@RequestBody RoomSearchReqDTO req) {
        return new ResponseEntity<>(houseService.searchRoom(req), HttpStatus.OK);
    }
}
