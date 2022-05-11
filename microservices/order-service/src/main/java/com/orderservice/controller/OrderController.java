package com.orderservice.controller;

import com.orderservice.dto.request.OrderPlaceDto;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import static com.orderservice.dto.AppConst.SUCCESS_RETURN;

@CrossOrigin
@Validated
@RequestScope
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody @Validated OrderPlaceDto orderPlaceDto){
        orderService.placeOrder(orderPlaceDto);
        return SUCCESS_RETURN.getMessage();
    }

}
