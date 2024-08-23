package com.invoice.service.invoice_service.dto;



import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private String productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;

}
