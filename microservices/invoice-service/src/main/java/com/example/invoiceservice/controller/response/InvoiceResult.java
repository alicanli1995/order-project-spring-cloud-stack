package com.example.invoiceservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

import java.io.ByteArrayInputStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResult {

    ByteArrayInputStream stream;
    HttpHeaders headers;

}
