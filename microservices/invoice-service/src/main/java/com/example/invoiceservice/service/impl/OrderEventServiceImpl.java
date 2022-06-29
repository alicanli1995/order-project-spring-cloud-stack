package com.example.invoiceservice.service.impl;

import com.example.invoiceservice.domain.OrderEvent;
import com.example.invoiceservice.domain.OrderStatus;
import com.example.invoiceservice.producer.NotificationProducer;
import com.example.invoiceservice.repository.EventRepository;
import com.example.invoiceservice.service.OrderEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderEventServiceImpl implements OrderEventService {

    private final NotificationProducer notificationProducer;
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void processOrderEvents(ConsumerRecord<String ,String > records) throws JsonProcessingException {
        var event = objectMapper.readValue(records.value(), OrderEvent.class);
        log.info("orderEvents -> {}", event );
        validate(event);
        save(event);
        notificationProducer.sendOrderEventAsync(event);
    }

    private void validate(OrderEvent event) {
        var optional = eventRepository.findByOrderId(event.getOrderId());
        if(Objects.isNull(event.getOrderLineItems()) || optional.isPresent()){
            throw new IllegalArgumentException("Order Event Baskets is missing or already exists");
        }
        log.info("Validation is successfully for the Order event : {} " , event.getOrderId());
    }

    private void save(OrderEvent event) {
        event.setUserName("ADMIN");
        event.setOrderStatus(OrderStatus.ACTIVE);
        eventRepository.save(event);
        log.info("Successfully persisted event. -> {}" ,event );
    }
}
