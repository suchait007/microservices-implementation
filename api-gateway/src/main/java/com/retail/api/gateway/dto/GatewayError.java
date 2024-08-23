package com.retail.api.gateway.dto;


import lombok.Data;

import java.util.List;


@Data
public class GatewayError {

    private String errorMessage;
    private List<String> errorDetails;
}
