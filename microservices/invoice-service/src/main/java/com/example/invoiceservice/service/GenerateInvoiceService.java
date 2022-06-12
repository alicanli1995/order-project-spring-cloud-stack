package com.example.invoiceservice.service;

import com.example.invoiceservice.domain.OrderEvent;

public interface GenerateInvoiceService {
    OrderEvent generateInvoice(String orderId);

}
