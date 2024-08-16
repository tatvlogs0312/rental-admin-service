package org.example.administrationservice.service.admin.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.common.DateUtils;
import org.example.administrationservice.common.FileUtils;
import org.example.administrationservice.common.JwtUtils;
import org.example.administrationservice.entity.Contract;
import org.example.administrationservice.entity.ContractUpload;
import org.example.administrationservice.entity.Room;
import org.example.administrationservice.enums.ContractStatusEnum;
import org.example.administrationservice.enums.GenderEnum;
import org.example.administrationservice.enums.IdentityEnum;
import org.example.administrationservice.enums.RoomStatusEnum;
import org.example.administrationservice.exception.ApplicationException;
import org.example.administrationservice.exception.ExceptionEnums;
import org.example.administrationservice.models.PageInfo;
import org.example.administrationservice.models.icustom.IContractSearchDTO;
import org.example.administrationservice.models.icustom.IRoomInfoResDTO;
import org.example.administrationservice.models.request.CmisUploadReq;
import org.example.administrationservice.models.request.ContractReqDTO;
import org.example.administrationservice.models.request.ContractSearchReqDTO;
import org.example.administrationservice.models.request.ContractUploadReqDTO;
import org.example.administrationservice.models.response.ContractDetailResDTO;
import org.example.administrationservice.models.response.ContractPageResDTO;
import org.example.administrationservice.models.response.ContractResDTO;
import org.example.administrationservice.models.response.ContractUploadResDTO;
import org.example.administrationservice.models.response.RoomInfoResDTO;
import org.example.administrationservice.proxy.CmisProxy;
import org.example.administrationservice.repository.ContractRepository;
import org.example.administrationservice.repository.ContractUploadRepository;
import org.example.administrationservice.repository.HouseRepository;
import org.example.administrationservice.repository.RoomRepository;
import org.example.administrationservice.service.admin.ContractService;
import org.example.administrationservice.validator.ContractValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContractServiceImpl implements ContractService {

    private final ObjectMapper objectMapper;
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final ContractRepository contractRepository;
    private final ContractUploadRepository contractUploadRepository;
    private final CmisProxy cmisProxy;

    /**
     * Tạo hợp đồng
     *
     * @param req
     */
    @Override
    public void createContract(ContractReqDTO req) {

        ContractValidator.validatorContractCreate(req);

        Optional<Room> roomOpt = roomRepository.findFirstByRoomCode(req.getRoomCode());
        if (roomOpt.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_ROOM_NOT_EXIST);
        }

        Room room = roomOpt.get();
        if (Objects.equals(room.getRoomStatus(), RoomStatusEnum.RENTED.name())) {
            throw new ApplicationException(ExceptionEnums.EX_ROOM_WAS_RENTED);
        }

        GenderEnum gender = GenderEnum.valueOf(req.getGender());

        String contractCode =
                req.getRoomCode() + "_" + LocalDate.now() + "_" + req.getIdentityCardNumber();

        Contract contract = new Contract();
        contract.setId(UUID.randomUUID().toString());
        contract.setFullName(req.getFullName());
        contract.setBirthdate(req.getBirthdate());
        contract.setIdentityCardNumber(req.getIdentityCardNumber());
        contract.setGender(gender.getValue());
        contract.setGenderCode(gender.name());
        contract.setPlaceOfOrigin(req.getPlaceOfOrigin());
        contract.setPlaceOfResidence(req.getPlaceOfResidence());
        contract.setContractCode(contractCode);
        contract.setCreateDate(LocalDateTime.now());
        contract.setUpdateDate(LocalDateTime.now());
        contract.setCreateBy(JwtUtils.getUsername());
        contract.setPhoneNumber(req.getPhoneNumber());
        contract.setOtherPhoneNumber(req.getOtherPhoneNumber());
        contract.setStatus(ContractStatusEnum.DRAFT.name());
        contract.setRoomCode(req.getRoomCode());

        room.setRoomStatus(RoomStatusEnum.RENTED.name());

        roomRepository.save(room);
        contractRepository.save(contract);
        houseRepository.updateHouseRentedIncrease(room.getHouseCode());
    }

    /**
     * Chấm dứt hợp đồng
     *
     * @param contractCode
     */
    @Override
    public void terminationContract(String contractCode) {
        Optional<Contract> contractOtp = contractRepository.findFirstByContractCode(contractCode);
        if (contractOtp.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_CONTRACT_NOT_EXIST);
        }

        Contract contract = contractOtp.get();
        contract.setStatus(ContractStatusEnum.EXPIRE.name());
        contract.setUpdateDate(LocalDateTime.now());
        contractRepository.save(contract);
        roomRepository.updateStatusByRoomCode(contract.getRoomCode(), RoomStatusEnum.EMPTY.name());

        Optional<Room> room = roomRepository.findFirstByRoomCode(contract.getRoomCode());
        room.ifPresent(value -> houseRepository.updateHouseRentedDecrease(value.getHouseCode()));
    }

    /**
     * Upload cmnd/cccd
     *
     * @param req
     */
    @Override
    public void uploadIdentityCard(ContractUploadReqDTO req) {
        Optional<Contract> contractOtp = contractRepository.findFirstByContractCode(
                req.getContractCode());
        if (contractOtp.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_CONTRACT_NOT_EXIST);
        }

        Contract contract = contractOtp.get();
        contract.setUpdateDate(LocalDateTime.now());

        List<ContractUpload> contractUploads = new ArrayList<>();
        ContractUpload front = new ContractUpload();
        front.setId(UUID.randomUUID().toString());
        front.setContractId(contract.getId());
        front.setPath(uploadToCmis(req.getFront()));
        front.setFileType(IdentityEnum.FRONT.name());
        contractUploads.add(front);

        ContractUpload back = new ContractUpload();
        back.setId(UUID.randomUUID().toString());
        back.setContractId(contract.getId());
        back.setPath(uploadToCmis(req.getBack()));
        back.setFileType(IdentityEnum.BACK.name());
        contractUploads.add(back);

        contractUploadRepository.saveAll(contractUploads);
        contractRepository.save(contract);
    }

    private String uploadToCmis(MultipartFile file) {
        CmisUploadReq req = CmisUploadReq.builder().file(FileUtils.convertFileV2(file)).build();
        try {
            return cmisProxy.upload(req);
        } catch (Exception e) {
            throw new ApplicationException(ExceptionEnums.EX_INTERNAL_SERVER.getMessage());
        } finally {
            FileUtils.cleanFileNotNull(req.getFile());
        }
    }

    /**
     * Active hợp đồng
     *
     * @param contractCode
     */
    @Override
    public void updateStatusToEffective(String contractCode) {
        Optional<Contract> contractOpt = contractRepository.findFirstByContractCode(contractCode);
        if (contractOpt.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_CONTRACT_NOT_EXIST);
        }

        Contract contract = contractOpt.get();
        if (!Objects.equals(contract.getStatus(), ContractStatusEnum.DRAFT.name())) {
            throw new ApplicationException(ExceptionEnums.EX_CONTRACT_STATUS_NOT_VALID.getMessage());
        }

        contract.setStatus(ContractStatusEnum.EFFECTIVE.name());
        contract.setUpdateDate(LocalDateTime.now());
        contractRepository.save(contract);
    }

    /**
     * Tìm kiếm hợp đồng
     *
     * @param req
     * @return
     */
    @Override
    public ContractPageResDTO searchContract(ContractSearchReqDTO req) {
        Pageable page = PageRequest.of(req.getPage(), req.getSize());
        Page<IContractSearchDTO> iContractSearchDTOPage = contractRepository
                .searchContract(req.getRoomCode(), req.getHouseCode(), req.getIdentityNumber(),
                        req.getFullName(), req.getStatus(), page);
        if (iContractSearchDTOPage.hasContent()) {
            ContractPageResDTO contractPageResDTO = new ContractPageResDTO();

            List<ContractResDTO> data = iContractSearchDTOPage.get()
                    .map(iContractSearchDTO -> objectMapper.convertValue(iContractSearchDTO,
                            ContractResDTO.class))
                    .collect(Collectors.toList());

            PageInfo pageInfo = PageInfo
                    .builder()
                    .totalPage(iContractSearchDTOPage.getTotalPages())
                    .currentPage(req.getPage() + 1)
                    .totalData(iContractSearchDTOPage.getTotalElements())
                    .build();

            contractPageResDTO.setContracts(data);
            contractPageResDTO.setPage(pageInfo);
            return contractPageResDTO;
        }

        return new ContractPageResDTO();
    }

    /**
     * Xem chi tiết hợp đồng
     *
     * @param contractCode
     * @return
     */
    @Override
    public ContractDetailResDTO getContractDetail(String contractCode) {
        Optional<Contract> contractOtp = contractRepository.findFirstByContractCode(contractCode);
        if (contractOtp.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_CONTRACT_NOT_EXIST);
        }

        Contract contract = contractOtp.get();

        ContractDetailResDTO response = ContractDetailResDTO.builder()
                .id(contract.getId())
                .contractCode(contract.getContractCode())
                .fullName(contract.getFullName())
                .birthdate(contract.getBirthdate())
                .genderCode(contract.getGenderCode())
                .identityCardNumber(contract.getIdentityCardNumber())
                .placeOfOrigin(contract.getPlaceOfOrigin())
                .placeOfResidence(contract.getPlaceOfResidence())
                .createDate(DateUtils.toStr(contract.getCreateDate().toLocalDate(), DateUtils.F_DDMMYYYY))
                .phoneNumber(contract.getPhoneNumber())
                .otherPhoneNumber(contract.getOtherPhoneNumber())
                .status(contract.getStatus())
                .build();

        List<ContractUpload> contractUploads = contractUploadRepository.findAllByContractId(
                contract.getId());
        if (!CollectionUtils.isEmpty(contractUploads)) {
            response.setFiles(
                    contractUploads.stream().map(cu -> ContractUploadResDTO.builder()
                                    .id(cu.getId())
                                    .type(cu.getFileType())
                                    .path(cu.getPath())
                                    .build())
                            .collect(Collectors.toList()));
        }

        Optional<IRoomInfoResDTO> roomInfoDTOOtp = roomRepository.getRoomInfoByCode(
                contract.getRoomCode());
        roomInfoDTOOtp.ifPresent(iRoomInfoResDTO -> response.setRoomInfo(
                objectMapper.convertValue(iRoomInfoResDTO, RoomInfoResDTO.class)));

        return response;
    }

    @Override
    public byte[] getIdentityById(String id) {
        Optional<ContractUpload> contractUploadOtp = contractUploadRepository.findById(id);
        if (contractUploadOtp.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_FILE_NOT_FOUND.getMessage());
        }
        return cmisProxy.getIdentity(contractUploadOtp.get().getPath());
    }
}
