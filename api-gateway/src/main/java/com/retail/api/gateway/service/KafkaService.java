package com.retail.api.gateway.service;


import com.retail.api.gateway.dto.ProductRequest;
import com.retail.api.gateway.dto.RequestStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaService {


    @Value("${kafka.topic.products}")
    private String productTopicName;

    private final KafkaTemplate<String, ProductRequest> kafkaTemplate;


    @Retryable(retryFor = {Exception.class})
    public void sendProductRequest(ProductRequest productRequest) throws Exception {

        /*
        if(Math.random() > 0) {
            throw new Exception("ex testing");
        }
        */

        kafkaTemplate.send(productTopicName, productRequest);
        log.info("ProductRequest message has been sent to Kafka topic.");
    }

    @Recover
    public void recover(Exception e) throws Exception {
        log.error("All retries has been exhausted, failed to send message : {}", e.getMessage());
        throw e;
    }
}
