package com.retail.api.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public ResponseEntity<String> fallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service is currently unavailable. Please try again later.");
    }

    @RequestMapping("/bulkheadfallback")
    public ResponseEntity<String> bulkheadfallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service is overloaded currently, Please try again later.");
    }
}
