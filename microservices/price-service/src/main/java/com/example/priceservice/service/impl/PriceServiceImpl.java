package com.example.priceservice.service.impl;

import com.example.priceservice.dto.request.ChangePriceRequest;
import com.example.priceservice.dto.response.PriceGenericResponse;
import com.example.priceservice.entity.Price;
import com.example.priceservice.repotisory.PriceRepository;
import com.example.priceservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Log4j2
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;

    private final ModelMapper modelMapper;

    @Override
    public BigDecimal getPriceForProduct(String name) {
        var price =  priceRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("This product is not in price database ."));
        return price.getPriceProduct();
    }

    @Override
    public PriceGenericResponse changePriceProduct(ChangePriceRequest request) {
        var priceData =  priceRepository.findByName(request.getName())
                .orElseThrow(() -> new RuntimeException("This product is not in price database ."));
        priceData.setPriceProduct(request.getPrice());
        priceRepository.save(modelMapper.map(priceData, Price.class));
        return modelMapper.map(priceData,PriceGenericResponse.class);
    }
}
