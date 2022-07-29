package com.example.invoiceservice.service.impl;

import com.example.invoiceservice.domain.FailureRecord;
import com.example.invoiceservice.repository.FailureRecordRepository;
import com.example.invoiceservice.service.FailureRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@RequiredArgsConstructor
public class FailureRecordServiceImpl implements FailureRecordService {

    private final FailureRecordRepository failureRecordRepository;

    public void saveFailedRecord(ConsumerRecord<String, String> record, Exception exception, String recordStatus){
        var failureRecord = new FailureRecord(
                null,
                record.topic(),
                record.key(),
                record.value(),
                record.partition(),
                record.offset(),
                exception.getCause().getMessage(),
                recordStatus);
        failureRecordRepository.save(failureRecord);
    }
}
