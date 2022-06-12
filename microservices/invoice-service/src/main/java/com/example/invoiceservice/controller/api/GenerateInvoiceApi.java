package com.example.invoiceservice.controller.api;


import com.example.invoiceservice.controller.pdf.GeneratePdfReport;
import com.example.invoiceservice.controller.pdf.PDFExporter;
import com.example.invoiceservice.service.GenerateInvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@Validated
@RequestScope
@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
@Log4j2
public class GenerateInvoiceApi {

    private final GenerateInvoiceService generateInvoiceService;

    /**
     * @deprecated
     */
    @GetMapping(value = "/generate/{orderId}",produces = MediaType.APPLICATION_PDF_VALUE)
    @Deprecated(since = "1.0.0")
    public void generateInvoice(HttpServletResponse response,
                                @Validated @PathVariable String orderId) throws IOException {
        log.info("Generate Invoice Started -> ");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        var invoice = generateInvoiceService.generateInvoice(orderId);
        PDFExporter userPDFExporter = new PDFExporter(invoice);
        userPDFExporter.exportPdf(response);

    }

    @GetMapping(value = "/{orderId}",produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateInvoiceWithBis(@Validated @PathVariable String orderId){
        log.info("Generate Invoice Started -> ");
        var invoice = generateInvoiceService.generateInvoice(orderId);
        ByteArrayInputStream bis = GeneratePdfReport.invoiceGeneratePdf(invoice);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        var headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "inline; filename= " + invoice.getOrderId() + currentDateTime + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));

    }


}
