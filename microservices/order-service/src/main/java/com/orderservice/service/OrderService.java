package com.orderservice.service;

import com.orderservice.dto.request.OrderPlaceDto;

public interface OrderService {
    String placeOrder(OrderPlaceDto orderPlaceDto);
}
