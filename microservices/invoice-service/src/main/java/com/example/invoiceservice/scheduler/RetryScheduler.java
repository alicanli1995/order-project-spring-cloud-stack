package com.example.invoiceservice.scheduler;


import com.example.invoiceservice.domain.FailureRecord;
import com.example.invoiceservice.domain.OrderEvent;
import com.example.invoiceservice.producer.NotificationProducer;
import com.example.invoiceservice.repository.FailureRecordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class RetryScheduler {

    private static final String RETRY = "RETRY";
    private static final String SUCCESS = "SUCCESS";
    private final ObjectMapper objectMapper;
    private final NotificationProducer notificationProducer;
    private final FailureRecordRepository failureRecordRepository;


    @Scheduled(fixedRate = 10000 )
    public void retryFailedRecords(){

        log.info("Retrying Failed Records Started!");
        failureRecordRepository.findAllByStatus(RETRY)
                .forEach(failureRecord -> {
                    try {
                        failureRecord.setKey_record(UUID.randomUUID().toString());
                        var consumerRecord = buildConsumerRecord(failureRecord);
                        OrderEvent failureEvent = objectMapper.readValue(consumerRecord.value(), OrderEvent.class);
                        failureRecord.setStatus(SUCCESS);
                        failureRecordRepository.save(failureRecord);
                        notificationProducer.sendOrderEventAsync(failureEvent);
                    } catch (Exception e){
                        log.error("Exception in retryFailedRecords : ", e);
                    }
                });
    }

    private ConsumerRecord<String, String> buildConsumerRecord(FailureRecord failureRecord) {

        return new ConsumerRecord<>(
                failureRecord.getTopic_record(),
                failureRecord.getPartition_record(),
                failureRecord.getOffset_value_record(),
                failureRecord.getKey_record(),
                failureRecord.getErrorRecord()
        );

    }
}