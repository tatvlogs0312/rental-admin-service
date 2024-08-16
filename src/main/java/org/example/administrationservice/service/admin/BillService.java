package org.example.administrationservice.service.admin;

import org.example.administrationservice.models.request.BillCreateReqDTO;
import org.example.administrationservice.models.request.BillSearchReqDTO;
import org.example.administrationservice.models.response.BillSearchResDTO;

public interface BillService {
    void createBill(BillCreateReqDTO req);
    void paymentBill(String billId);
    BillSearchResDTO searchBill(BillSearchReqDTO req);
}
