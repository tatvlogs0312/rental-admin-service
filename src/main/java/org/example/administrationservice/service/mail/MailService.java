package org.example.administrationservice.service.mail;

import org.example.administrationservice.models.mail.MailDTO;

public interface MailService {
    void sendMail(MailDTO mail);
    void sendMailHtml(MailDTO mail);
}
