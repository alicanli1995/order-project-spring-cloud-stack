package com.example.invoiceservice.service.impl;

import com.example.invoiceservice.controller.pdf.GeneratePdfReport;
import com.example.invoiceservice.controller.response.InvoiceResult;
import com.example.invoiceservice.domain.OrderEvent;
import com.example.invoiceservice.repository.EventRepository;
import com.example.invoiceservice.service.GenerateInvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class GenerateInvoiceServiceImpl implements GenerateInvoiceService {

    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public InvoiceResult generateInvoiceBis(String orderId) {
        var invoice = eventRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        var headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "inline; filename= " + orderId + currentDateTime + ".pdf");
        var invoiceReturn = GeneratePdfReport.invoiceGeneratePdf(invoice);
        return InvoiceResult.builder()
                .stream(invoiceReturn)
                .headers(headers)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderEvent generateInvoice(String orderId) {
        return eventRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}
