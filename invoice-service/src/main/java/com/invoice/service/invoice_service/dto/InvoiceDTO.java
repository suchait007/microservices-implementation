package com.invoice.service.invoice_service.dto;


import lombok.Data;

@Data
public class InvoiceDTO {

    private String invoiceId;
    private String userId;
    private String invoiceNumber;
    private String productIds;
    private String createdDate;
    private String updatedDate;

}
