package com.statistics.app.service.impl;


import com.orderservice.entity.OrderEvent;
import com.statistics.app.controller.response.DailyResponse;
import com.statistics.app.domain.AllOrders;
import com.statistics.app.repository.AllOrdersRepository;
import com.statistics.app.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Log4j2
public class StatisticServiceImpl implements StatisticService {

    private static final DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final AllOrdersRepository statisticsRepository;

    @Override
    public void processAndSave(OrderEvent event) {
        event.getOrderLineItems()
                .forEach(orderLineItemsDto -> {
                    var allOrders = new AllOrders();
                    allOrders.setSkuCode(orderLineItemsDto.getSkuCode());
                    allOrders.setPrice(orderLineItemsDto.getPrice());
                    allOrders.setQuantity(orderLineItemsDto.getQuantity());
                    allOrders.setTotalAmount(orderLineItemsDto.getTotalAmount());
                    statisticsRepository.save(allOrders);
                });
    }

    @Override
    public DailyResponse getDailyStatistic() {
        var dailyOrderList = statisticsRepository.findAllByOrderDate(LocalDate.now());

        return DailyResponse.
                builder()
                .totalItemsCountDay(dailyOrderList.size())
                .totalAmountInDay(dailyOrderList.stream()
                        .map(AllOrders::getTotalAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }
}
