package org.example.administrationservice.service.admin.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.administrationservice.entity.*;
import org.example.administrationservice.enums.BillStatusEnum;
import org.example.administrationservice.enums.ContractStatusEnum;
import org.example.administrationservice.exception.ApplicationException;
import org.example.administrationservice.exception.ExceptionEnums;
import org.example.administrationservice.models.PageInfo;
import org.example.administrationservice.models.icustom.IBillSearchDTO;
import org.example.administrationservice.models.request.BillCreateReqDTO;
import org.example.administrationservice.models.request.BillDetailReqDTO;
import org.example.administrationservice.models.request.BillSearchReqDTO;
import org.example.administrationservice.models.response.BillDetailResDTO;
import org.example.administrationservice.models.response.BillResDTO;
import org.example.administrationservice.models.response.BillSearchResDTO;
import org.example.administrationservice.repository.*;
import org.example.administrationservice.service.admin.BillService;
import org.example.administrationservice.validator.BillValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;
    private final RoomRepository roomRepository;
    private final HouseRepository houseRepository;
    private final ContractRepository contractRepository;
    private final UtilityRepository utilityRepository;

    /**
     * Tạo hóa đơn
     *
     * @param req
     */
    @Override
    public void createBill(BillCreateReqDTO req) {

        BillValidator.validateBillCreate(req);

        Optional<Contract> contractOpt = contractRepository.findFirstByContractCode(req.getContractCode());
        if (contractOpt.isEmpty() || !Objects.equals(contractOpt.get().getStatus(), ContractStatusEnum.EFFECTIVE.name())) {
            throw new ApplicationException(ExceptionEnums.EX_CONTRACT_NOT_EXIST);
        }

        Contract contract = contractOpt.get();

        Optional<Bill> billExist = billRepository
                .findFirstByContractIdAndYearAndMonthAndStatus(contract.getId(), req.getYear(), req.getMonth(), BillStatusEnum.PAID.name());
        if (billExist.isPresent()) {
            throw new ApplicationException(String.format(ExceptionEnums.EX_BILL_IS_PAID.getMessage(), req.getMonth(), req.getYear()));
        }

        clearData(contract.getId(), req.getMonth(), req.getYear());

        Room room = roomRepository.findFirstByRoomCode(contract.getRoomCode())
                .orElseThrow(() -> new ApplicationException(ExceptionEnums.EX_ROOM_NOT_EXIST));

        List<String> utilityCodes = req.getDetails().stream().map(BillDetailReqDTO::getUtilityCode)
                .collect(Collectors.toList());

        List<Utility> utilities = utilityRepository.findAllByCodeIn(utilityCodes);

        String billId = UUID.randomUUID().toString();
        Bill bill = new Bill();
        bill.setId(billId);
        bill.setContractId(contract.getId());
        bill.setCreatedDate(LocalDate.now());
        bill.setMonth(req.getMonth());
        bill.setYear(req.getYear());
        bill.setStatus(BillStatusEnum.PENDING.name());

        List<BillDetail> billDetails = new ArrayList<>();
        req.getDetails().forEach(ur -> {
            Optional<Utility> utilityOtp = utilities.stream()
                    .filter(u -> Objects.equals(u.getCode(), ur.getUtilityCode())).findFirst();
            if (utilityOtp.isPresent()) {
                BillDetail billDetail = new BillDetail();
                billDetail.setId(UUID.randomUUID().toString());
                billDetail.setBillId(billId);
                billDetail.setUtilityCode(ur.getUtilityCode());
                billDetail.setNumber(ur.getNumberUse());
                billDetail.setContractId(contract.getId());
                billDetail.setMonth(req.getMonth());
                billDetail.setYear(req.getYear());
                billDetail.setTotalPrice(utilityOtp.get().getPrice() * ur.getNumberUse());

                billDetails.add(billDetail);
            }
        });

        if (BooleanUtils.isTrue(req.getIsContinueRent())) {
            BillDetail billDetail = new BillDetail();
            billDetail.setId(UUID.randomUUID().toString());
            billDetail.setBillId(billId);
            billDetail.setUtilityCode("ROOM");
            billDetail.setNumber(1);
            billDetail.setContractId(contract.getId());
            billDetail.setMonth(req.getMonth());
            billDetail.setYear(req.getYear());
            billDetail.setTotalPrice(room.getRoomPrice());

            billDetails.add(billDetail);
        }

        Long total = billDetails.stream().mapToLong(BillDetail::getTotalPrice).sum();
        bill.setTotal(total);

        billRepository.save(bill);
        billDetailRepository.saveAll(billDetails);
    }

    private void clearData(String contractId, Integer month, Integer year) {
        billRepository.deleteAllByContractIdAndMonthAndYear(contractId, month, year);
        billDetailRepository.deleteAllByContractIdAndMonthAndYear(contractId, month, year);
    }

    @Override
    public void paymentBill(String billId) {
        Optional<Bill> billOptional = billRepository.findById(billId);
        if (billOptional.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.DATA_NOT_VALID.getMessage());
        }

        Bill bill = billOptional.get();
        bill.setStatus(BillStatusEnum.PAID.name());
        bill.setPaymentDate(LocalDate.now());
        billRepository.save(bill);
    }

    @Override
    public BillSearchResDTO searchBill(BillSearchReqDTO req) {
        if (StringUtils.isBlank(req.getBillId())) {
            req.setBillId("ALL");
        }
        if (StringUtils.isBlank(req.getContractId())) {
            req.setContractId("ALL");
        }
        if (StringUtils.isBlank(req.getStatus())) {
            req.setStatus("ALL");
        }

        BillSearchResDTO res = new BillSearchResDTO();

        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<IBillSearchDTO> billPage = billRepository.searchBill(req.getBillId(), req.getContractId(),
                req.getMonth(), req.getYear(), req.getStatus(), pageable);
        if (billPage.hasContent()) {
            List<IBillSearchDTO> bills = billPage.stream().toList();

            List<String> billIds = bills.stream().map(IBillSearchDTO::getId).collect(Collectors.toList());

            List<BillDetail> billDetails = billDetailRepository.findAllByBillIdIn(billIds);

            List<BillResDTO> billsRes = new ArrayList<>();
            for (IBillSearchDTO bill : bills) {
                BillResDTO billDTO = new BillResDTO();
                billDTO.setId(bill.getId());
                billDTO.setMonth(bill.getMonth());
                billDTO.setYear(bill.getYear());
                billDTO.setContractCode(bill.getContractCode());
                billDTO.setRoomCode(bill.getRoomCode());
                billDTO.setRoomName(bill.getName());
                billDTO.setStatus(bill.getStatus());
                billDTO.setTotal(bill.getTotal());

                List<BillDetail> details = billDetails.stream().filter(d -> Objects.equals(d.getBillId(), bill.getId()))
                        .collect(Collectors.toList());
                billDTO.setBillDetails(details.stream().map(BillDetailResDTO::new).collect(Collectors.toList()));

                billsRes.add(billDTO);
            }

            res.setBills(billsRes);
            res.setPage(PageInfo.builder()
                    .totalPage(billPage.getTotalPages())
                    .currentPage(req.getPage() + 1)
                    .totalData(billPage.getTotalElements())
                    .build());
        }

        return res;
    }
}
