package com.statistics.app.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.statistics.app.service.StatisticEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StatisticEventHandler {

    private final StatisticEventService statisticEventService;


    @KafkaListener(topics = {"statistic-events"} ,
            groupId = "statistic-events-listener-group"
    )
    public void onMessage(ConsumerRecord<String,String > records) throws JsonProcessingException {
        log.info("Statistic Record Topic -> " + records);
        statisticEventService.processStatisticEvent(records);
    }

}
