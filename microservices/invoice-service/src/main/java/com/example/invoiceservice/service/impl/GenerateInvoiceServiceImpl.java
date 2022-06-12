package com.example.invoiceservice.service.impl;

import com.example.invoiceservice.domain.OrderEvent;
import com.example.invoiceservice.repository.EventRepository;
import com.example.invoiceservice.service.GenerateInvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class GenerateInvoiceServiceImpl implements GenerateInvoiceService {

    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public OrderEvent generateInvoice(String orderId) {
        return eventRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}
