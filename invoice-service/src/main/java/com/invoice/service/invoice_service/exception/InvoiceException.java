package com.invoice.service.invoice_service.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;


@Data
public class InvoiceException extends RuntimeException {

    private HttpStatus httpStatus;
    private String errorMsg;
    private List<String> errorDetails;

    public InvoiceException(String msg, List<String> errors, HttpStatus httpStatus) {
        super(msg);
        this.errorMsg = msg;
        this.errorDetails = errors;
        this.httpStatus = httpStatus;
    }
}
