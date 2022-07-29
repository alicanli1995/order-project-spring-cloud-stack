package com.example.invoiceservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface FailureRecordService {
    void saveFailedRecord(ConsumerRecord<String , String> record, Exception exception, String retry);
}
