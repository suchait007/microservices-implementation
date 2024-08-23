package com.invoice.service.invoice_service.controller;


import com.invoice.service.invoice_service.dto.InvoiceDTO;
import com.invoice.service.invoice_service.entities.Invoice;
import com.invoice.service.invoice_service.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;




@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class InvoiceController {

    private final InvoiceService invoiceService;


    @GetMapping("/invoices")
    public List<InvoiceDTO> getInvoices(@RequestParam("user_id") String userId) {

        log.info("Request received with userId : {} ", userId);

        return invoiceService.listInvoice(userId);
    }

    @PostMapping("/invoices")
    public InvoiceDTO createInvoices(@RequestBody InvoiceDTO invoiceDTO) {
        log.info("Request received with invoice : {} ", invoiceDTO);
        return invoiceService.createInvoice(invoiceDTO);
    }
}
