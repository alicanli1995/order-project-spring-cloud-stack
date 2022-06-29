package com.notification.service.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.notification.service.service.NotificationEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final NotificationEventService notificationEventService;


    @KafkaListener(topics = {"notification-events"} ,
            groupId = "notification-events-listener-group"
    )
    public void onMessage(ConsumerRecord<String,String > records) throws JsonProcessingException {
        log.info("Consumer Record Topic -> " + records);
        notificationEventService.processOrderEvents(records);
    }

}
