package com.example.invoiceservice.consumer;


import com.example.invoiceservice.service.OrderEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final OrderEventService orderEventService;


    @KafkaListener(topics = {"order-events"} ,
            groupId = "order-events-listener-group"
    )
    public void onMessage(ConsumerRecord<String,String > records) throws JsonProcessingException {
        log.info("Consumer Record Topic -> " + records);
        orderEventService.processOrderEvents(records);
    }

}
