package com.statistics.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderservice.entity.OrderEvent;
import com.statistics.app.service.StatisticEventService;
import com.statistics.app.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class StatisticEventServiceImpl implements StatisticEventService {

    private final StatisticService statisticService;
    private final ObjectMapper objectMapper;
    @Override
    public void processStatisticEvent(ConsumerRecord<String, String> records) throws JsonProcessingException {
        var event = objectMapper.readValue(records.value(), OrderEvent.class);
        log.info("statisticEventHandling process -> {}", event );
        statisticService.processAndSave(event);
    }
}
