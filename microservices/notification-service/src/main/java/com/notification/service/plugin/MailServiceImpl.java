package com.notification.service.plugin;

import com.notification.service.domain.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    @Override
    public void sendMail(OrderEvent event) {
        event.setUserEmail("alicanli1995@gmail.com");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("alicanli1995@gmail.com");
        message.setTo(event.getUserEmail());
        message.setText(event.getOrderLineItems().toString());
        message.setSubject("Notification Event");
        log.info("Mail sent to -> {}", message);
        try
        {
            mailSender.send(message);
        }
        catch (Exception e)
        {
            log.error("Error sending mail -> {}", e);
        }

    }
}
