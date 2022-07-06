package com.statistics.app.service;

import com.orderservice.entity.OrderEvent;
import com.statistics.app.controller.response.DailyResponse;

public interface StatisticService {
    void processAndSave(OrderEvent event);

    DailyResponse getDailyStatistic();
}
