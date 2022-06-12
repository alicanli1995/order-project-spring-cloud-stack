package com.example.invoiceservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface OrderEventService {

    void processOrderEvents(ConsumerRecord<String, String> records) throws JsonProcessingException;
}
