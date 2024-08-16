package org.example.administrationservice.service.admin;

import org.example.administrationservice.models.request.ContractReqDTO;
import org.example.administrationservice.models.request.ContractSearchReqDTO;
import org.example.administrationservice.models.request.ContractUploadReqDTO;
import org.example.administrationservice.models.response.ContractDetailResDTO;
import org.example.administrationservice.models.response.ContractPageResDTO;

public interface ContractService {
    void createContract(ContractReqDTO req);
    void terminationContract(String contractCode);
    void uploadIdentityCard(ContractUploadReqDTO req);
    void updateStatusToEffective(String contractCode);
    ContractPageResDTO searchContract(ContractSearchReqDTO req);
    ContractDetailResDTO getContractDetail(String contractCode);
    byte[] getIdentityById(String id);
}
