package com.invoice.service.invoice_service.service;


import com.invoice.service.invoice_service.client.ProductServiceFeignClient;
import com.invoice.service.invoice_service.dto.ProductDTO;
import com.invoice.service.invoice_service.exception.InvoiceException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductServiceFeignClient productServiceFeignClient;

    public List<ProductDTO> getProducts(List<String> productIds) {

        List<ProductDTO> productDTOS;

        try {
            productDTOS = productServiceFeignClient.getProducts(productIds);
        } catch (InvoiceException invoiceException) {
            throw invoiceException;
        } catch (FeignException.ServiceUnavailable ex) {
            throw new InvoiceException("Downstream service is down.", List.of("invoice-service is down.")
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            throw new InvoiceException("Downstream service is down.", List.of("invoice-service is down.")
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return productDTOS;
    }
}
