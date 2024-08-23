package com.invoice.service.invoice_service.client;



import com.invoice.service.invoice_service.config.FeignConfig;
import com.invoice.service.invoice_service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(url = "${product.service.url}", value = "product-service-feign" , path = "/v1", configuration = FeignConfig.class)
public interface ProductServiceFeignClient {

    @GetMapping("/products")
    List<ProductDTO> getProducts(@RequestParam("ids") List<String> userId);
}
