package com.retail.api.gateway.dto;



import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;

}
