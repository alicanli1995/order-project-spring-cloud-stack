package com.example.invoiceservice.config;

import com.example.invoiceservice.service.FailureRecordService;
import com.example.invoiceservice.service.OrderEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConfig {
    public static final String RETRY = "RETRY";
    public static final String SUCCESS = "SUCCESS";
    public static final String DEAD = "DEAD";

    @Autowired
    OrderEventService orderEventService;

    @Autowired
    KafkaProperties kafkaProperties;

    @Autowired
    FailureRecordService failureRecordService;

    @Autowired
    KafkaTemplate kafkaTemplate;



    @Value("${spring.topics.retry}")
    private String retryTopic;

    @Value("${spring.topics.dlt}")
    private String deadLetterTopic;
    public DeadLetterPublishingRecoverer publishingRecoverer() {

        return new DeadLetterPublishingRecoverer(kafkaTemplate
                , (r, e) -> {
            log.error("Exception in publishingRecovered : {} ", e.getMessage(), e);
            if (e.getCause() instanceof RecoverableDataAccessException) {
                return new TopicPartition(retryTopic, r.partition());
            } else {
                return new TopicPartition(deadLetterTopic, r.partition());
            }
        });
    }

    ConsumerRecordRecoverer consumerRecordRecoverer = (record, exception) -> {
        log.error("Exception is : {} Failed Record : {} ", exception, record);
        if (exception.getCause() instanceof RecoverableDataAccessException) {
            log.info("Inside the recoverable logic");
            //Add any Recovery Code here.
            failureRecordService.saveFailedRecord((ConsumerRecord<Integer, String>) record, exception, RETRY);

        } else {
            failureRecordService.saveFailedRecord((ConsumerRecord<Integer, String>) record, exception, DEAD);
            log.info("Inside the non recoverable logic and skipping the record : {}", record);

        }
    };

    public DefaultErrorHandler defaultErrorHandler(){

        var exceptionIgnoreList = List.of(
                IllegalArgumentException.class
        );

        var exceptionList = List.of(
                RecoverableDataAccessException.class
        );

        var fixed = new FixedBackOff(1000L,2);

        var backOffRetries = new ExponentialBackOffWithMaxRetries(2);
        backOffRetries.setInitialInterval(1_000L);
        backOffRetries.setMultiplier(2.0);
        backOffRetries.setMaxInterval(2_000L);

        var errorHandler =  new DefaultErrorHandler(
                consumerRecordRecoverer,
//                publishingRecoverer(),
//                fixed
                backOffRetries
        );

        errorHandler.addNotRetryableExceptions();
        exceptionIgnoreList.forEach(errorHandler::addNotRetryableExceptions);
//        exceptionList.forEach(errorHandler::addRetryableExceptions);

        errorHandler
                .setRetryListeners((record, ex, deliveryAttempt) -> {
                    log.info("Failed Record in Retry Listener , Exception : {} , deliveryAttempt : {}",ex , deliveryAttempt);
                });

        return errorHandler;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.setConcurrency(3); // For all partitions read on same time parallel
        factory.setCommonErrorHandler(defaultErrorHandler());
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }


}
