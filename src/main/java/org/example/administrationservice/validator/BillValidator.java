package org.example.administrationservice.validator;

import org.example.administrationservice.models.request.BillCreateReqDTO;

public class BillValidator {

    public static void validateBillCreate(BillCreateReqDTO req) {
        ValidatorChain.builder()
                .validField(req.getContractCode(), "contractCode")
                .validObject(req.getMonth(), "")
                .validObject(req.getYear(), "")
                .validList(req.getDetails(), "")
                .build();
    }
}
