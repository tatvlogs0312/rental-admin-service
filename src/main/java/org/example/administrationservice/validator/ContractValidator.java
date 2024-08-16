package org.example.administrationservice.validator;

import org.example.administrationservice.models.request.ContractReqDTO;

public class ContractValidator {

    public static void validatorContractCreate(ContractReqDTO req) {
        ValidatorChain.builder()
                .validAllIgnoreFields(req, "contractCode", "otherPhoneNumber", "status")
                .build();
    }
}
