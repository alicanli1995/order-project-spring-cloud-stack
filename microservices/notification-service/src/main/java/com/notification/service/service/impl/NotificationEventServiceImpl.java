package com.notification.service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.service.domain.OrderEvent;
import com.notification.service.plugin.MailService;
import com.notification.service.service.NotificationEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class NotificationEventServiceImpl implements NotificationEventService {

    private final MailService mailService;
    private final ObjectMapper objectMapper;

    @Override
    public void processOrderEvents(ConsumerRecord<String ,String > records) throws JsonProcessingException {
        var event = objectMapper.readValue(records.value(), OrderEvent.class);
        log.info("notificationEvents -> {}", event );
        sendMail(event);
    }

    private void sendMail(OrderEvent event) {
        mailService.sendMail(event);
    }

}
