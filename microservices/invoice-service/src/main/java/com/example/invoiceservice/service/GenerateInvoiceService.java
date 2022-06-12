package com.example.invoiceservice.service;

import com.example.invoiceservice.controller.response.InvoiceResult;
import com.example.invoiceservice.domain.OrderEvent;

public interface GenerateInvoiceService {
    InvoiceResult generateInvoiceBis(String orderId);

    OrderEvent generateInvoice(String orderId);

}
