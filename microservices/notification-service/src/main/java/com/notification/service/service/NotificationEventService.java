package com.notification.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface NotificationEventService {

    void processOrderEvents(ConsumerRecord<String, String> records) throws JsonProcessingException;
}
