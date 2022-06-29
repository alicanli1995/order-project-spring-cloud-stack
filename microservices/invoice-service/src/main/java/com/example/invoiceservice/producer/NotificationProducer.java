package com.example.invoiceservice.producer;

import com.example.invoiceservice.domain.OrderEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

@Component
@Log4j2
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, String > kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void sendOrderEventAsync(OrderEvent orderEvent) throws JsonProcessingException {

        String key = orderEvent.getOrderId();
        String value = objectMapper.writeValueAsString(orderEvent);

        var result = kafkaTemplate.send("notification-events",value);
        result.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                handleSuccess(key,value,result);
            }

            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key,value,ex);
            }
        });

    }

    private void handleFailure(String  key, String value, Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

    private void handleSuccess(String key, String value, SendResult<String, String> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}",
                key, value, result.getRecordMetadata().partition());
    }

    public SendResult<String, String> sendOrderEventSync(OrderEvent orderEvent) throws JsonProcessingException {

        String key = orderEvent.getOrderId();
        String value = objectMapper.writeValueAsString(orderEvent);
        SendResult<String, String> result;
        try {
            result = kafkaTemplate.sendDefault(key,value).get();

        } catch (InterruptedException | ExecutionException e) {
            log.error("InterruptedException | ExecutionException ->Event send was failed. Please Try Again ! -> Message is : " + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Exception ->Event send was failed. Please Try Again ! -> Message is : " + e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

}
