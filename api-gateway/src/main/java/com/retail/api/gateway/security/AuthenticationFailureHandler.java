package com.retail.api.gateway.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.api.gateway.dto.GatewayError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.List;

public class AuthenticationFailureHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request,
                         jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException {

        response.setStatus(401);
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        GatewayError gatewayError = new GatewayError();
        gatewayError.setErrorMessage("Authentication not passed");
        gatewayError.setErrorDetails(List.of("Token passed in request is not valid."));

        ObjectMapper mapper = new ObjectMapper();

        String content = mapper.writeValueAsString(gatewayError);

        try (var writer = response.getWriter()) {

            writer.write(content);
            writer.flush();
            response.setContentLength(content.length());
        }
    }
}
