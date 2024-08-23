package com.invoice.service.invoice_service.jpa;


import com.invoice.service.invoice_service.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {

    List<Invoice> findAllByUserId(String id);
}
