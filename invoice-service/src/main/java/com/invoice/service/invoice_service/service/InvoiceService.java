package com.invoice.service.invoice_service.service;


import com.invoice.service.invoice_service.dto.InvoiceDTO;
import com.invoice.service.invoice_service.dto.ProductDTO;
import com.invoice.service.invoice_service.entities.Invoice;
import com.invoice.service.invoice_service.exception.InvoiceException;
import com.invoice.service.invoice_service.jpa.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductService productService;

    public List<InvoiceDTO> listInvoice(String userId) {

        if(StringUtils.isEmpty(userId)) {
            throw new InvoiceException("Bad Request",
                    List.of("userId is not present in request."), HttpStatus.BAD_REQUEST);
        }


        List<Invoice> invoices = invoiceRepository.findAllByUserId(userId);

        return  invoices.stream()
                .map(this::populateInvoiceDTO)
                .toList();

    }

    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {

        String productIds = invoiceDTO.getProductIds();
        List<String> productIdsList = Arrays.stream(productIds.split(",")).toList();

        List<ProductDTO> productDTOS = productService.getProducts(productIdsList);
        List<String> productIdsResponse = productDTOS.stream().map(ProductDTO::getProductId).toList();


        List<Boolean> validList =
        productIdsList.stream()
                .map(productId -> isValidProductId(productId, productIdsResponse))
                .toList();

        if(invalidProductIdInRequest(validList)) {
            throw new InvoiceException("Bad Request",
                    List.of("Invalid product Id is present in request."), HttpStatus.BAD_REQUEST);
        }

        Invoice invoice = populateInvoice(invoiceDTO);
        Invoice savedInvoice = invoiceRepository.save(invoice);

        return populateInvoiceDTO(savedInvoice);
    }

    private boolean invalidProductIdInRequest(List<Boolean> validList) {
        return validList.stream().anyMatch(value -> value.booleanValue() == false);
    }

    private boolean isValidProductId(String productId, List<String> productIdsResponse) {
        return productIdsResponse.contains(productId);
    }

    private Invoice populateInvoice(InvoiceDTO invoiceDTO) {

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        invoice.setUserId(invoiceDTO.getUserId());
        invoice.setProductIds(invoiceDTO.getProductIds());

        return invoice;
    }

    private InvoiceDTO populateInvoiceDTO(Invoice invoice) {

        InvoiceDTO invoiceDTO = new InvoiceDTO();

        invoiceDTO.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceDTO.setInvoiceId(invoice.getId());
        invoiceDTO.setProductIds(invoice.getProductIds());
        invoiceDTO.setUpdatedDate(invoice.getUpdatedTime().toString());
        invoiceDTO.setCreatedDate(invoice.getCreatedTime().toString());

        return invoiceDTO;
    }
}
