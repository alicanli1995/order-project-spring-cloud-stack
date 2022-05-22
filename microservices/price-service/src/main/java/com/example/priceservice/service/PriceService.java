package com.example.priceservice.service;

import com.example.priceservice.dto.request.ChangePriceRequest;
import com.example.priceservice.dto.response.PriceGenericResponse;

import java.math.BigDecimal;

public interface PriceService {

    BigDecimal getPriceForProduct(String name);

    PriceGenericResponse changePriceProduct(ChangePriceRequest request);
}
