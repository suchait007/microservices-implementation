package com.retail.api.gateway.dto;


import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {

    private List<ProductDTO> productDTOList;
}
