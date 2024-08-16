package org.example.administrationservice.service.mail.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.constants.Constants;
import org.example.administrationservice.exception.ApplicationException;
import org.example.administrationservice.models.mail.MailDTO;
import org.example.administrationservice.service.mail.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${mail.username}")
    private String from;

    @Override
    public void sendMail(MailDTO mail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(mail.getMailTo().get(0));
            if (CollectionUtils.isEmpty(mail.getMailCc())) {
                message.setCc(mail.getMailTo().get(0));
            }
            message.setSubject(mail.getSubject());
            message.setText(mail.getContent());

            mailSender.send(message);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public void sendMailHtml(MailDTO mail) {
        try {
            MimeMessage mimeMessage  = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, Constants.UTF_8);
            helper.setFrom(from);
            helper.setTo(mail.getMailTo().toArray(new String[0]));
            helper.setCc(mail.getMailCc().toArray(new String[0]));
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getContent(), true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
