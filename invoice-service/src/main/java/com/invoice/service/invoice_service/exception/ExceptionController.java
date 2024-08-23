package com.invoice.service.invoice_service.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(InvoiceException.class)
    public ResponseEntity<InvoiceError> handleException(InvoiceException invoiceException) {

        InvoiceError invoiceError = new InvoiceError(invoiceException.getErrorMsg(), invoiceException.getErrorDetails());
        return ResponseEntity.status(invoiceException.getHttpStatus().value()).body(invoiceError);
    }

}
