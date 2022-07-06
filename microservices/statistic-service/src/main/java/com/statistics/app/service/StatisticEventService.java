package com.statistics.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface StatisticEventService {
    void processStatisticEvent(ConsumerRecord<String, String> records) throws JsonProcessingException;

}
