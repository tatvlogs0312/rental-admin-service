package org.example.administrationservice.service.sms;

import org.example.administrationservice.models.sms.SmsResDTO;

public interface SmsService {
    void sendSms(SmsResDTO req);
}
