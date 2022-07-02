package com.orderservice.controller;

import com.orderservice.dto.request.OrderPlaceDto;
import com.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.concurrent.CompletableFuture;

import static com.orderservice.dto.AppConst.FALLBACK_METHOD_RETURN;

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
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody @Validated OrderPlaceDto orderPlaceDto){
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderPlaceDto));
    }

    public CompletableFuture<String> fallbackMethod(OrderPlaceDto orderRequest, RuntimeException exception){
        return CompletableFuture.supplyAsync(FALLBACK_METHOD_RETURN::getMessage);
    }
}
