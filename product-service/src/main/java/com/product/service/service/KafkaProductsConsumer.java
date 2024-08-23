package com.product.service.service;


import com.retail.api.gateway.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProductsConsumer {


    private final ProductService productService;


    @Transactional
    @KafkaListener(topics = "new-products")
    public void sendProductRequest(ProductRequest productRequest) {
        log.info("Event received : {} ", productRequest.toString());
        productService.createProducts(productRequest);

        log.info("Products records has been created in database.");

    }
}
