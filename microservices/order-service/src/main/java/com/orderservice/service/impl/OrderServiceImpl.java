package com.orderservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orderservice.dto.request.OrderPlaceDto;
import com.orderservice.dto.request.OrderRequest;
import com.orderservice.dto.response.InventoryResponse;
import com.orderservice.entity.Order;
import com.orderservice.entity.OrderEvent;
import com.orderservice.exception.NotProductFoundException;
import com.orderservice.exception.NotStockFoundException;
import com.orderservice.producer.OrderProducer;
import com.orderservice.repository.OrderRepository;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static com.orderservice.dto.AppConst.SUCCESS_RETURN;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final String debugId = "43a37753-bad2-4eae-af3b-3a28269d45c5";
    private final Tracer tracer;

    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final WebClient.Builder webClientBuilder;

    @Override
    public String  placeOrder(OrderPlaceDto orderPlaceDto){

        List<Boolean> isExistAllOrders = isExist(orderPlaceDto);

        Order order;

        if(!isExistAllProducts(isExistAllOrders))
            throw new NotProductFoundException("This order list one or many product has not in database. " , "Not Product" , debugId );

        var lookUp = tracer.nextSpan().name("Inventory Service Look Up");

        try (Tracer.SpanInScope spanIsScope = tracer.withSpan(lookUp.start())){

            List<InventoryResponse> isInStockAllProduct = getInventoryResponses(orderPlaceDto);

            if(!isAllProductHasStock(isInStockAllProduct))
                throw new NotStockFoundException("This order list one or many product has not enough quantity for this order. " , "Not Enough Quantity" , debugId );

            getPriceAndCalcTotally(orderPlaceDto);

            order = orderRepository.save(modelMapper.map(orderPlaceDto,Order.class));

            log.info(String.format("Order placed successfully. Order id is -> %s " ,order.getId()));

            reduceInventoryForSuccessProcess(orderPlaceDto);

        }finally {
            lookUp.end();
        }

        var orderEvent = OrderEvent.builder()
                .orderId(order.getOrderNumber())
                .orderLineItems(orderPlaceDto.getOrderLineItemsList())
                .build();

        try {
            orderProducer.sendOrderEventAsync(orderEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }


        return SUCCESS_RETURN.getMessage();

    }

    private void reduceInventoryForSuccessProcess(OrderPlaceDto orderPlaceDto) {
        orderPlaceDto.getOrderLineItemsList()
                .forEach(orderLineItemsDto ->{
                    var request = OrderRequest.builder()
                            .skuCode(orderLineItemsDto.getSkuCode())
                            .quantity(orderLineItemsDto.getQuantity())
                            .build();
                    webClientBuilder.build().post()
                            .uri("http://inventory-service/api/inventory/")
                            .body(Mono.just(request),OrderRequest.class)
                            .retrieve()
                            .bodyToMono(Void.class)
                            .block();
                });
    }

    private List<Boolean> isExist(OrderPlaceDto orderPlaceDto) {
        return orderPlaceDto.getOrderLineItemsList()
                .stream()
                .map(orderLineItemsDto -> {
                    var response = webClientBuilder.build().get()
                            .uri("http://product-service/api/product/",
                                    uriBuilder -> uriBuilder.path(orderLineItemsDto.getSkuCode()).build())
                            .retrieve()
                            .bodyToMono(Boolean.class)
                            .block();
                    assert response != null;
                    return response;
                }).toList();
    }

    private List<InventoryResponse> getInventoryResponses(OrderPlaceDto orderPlaceDto) {
        return orderPlaceDto.getOrderLineItemsList()
                    .stream()
                    .map(orderLineItemsDto -> {
                        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                        queryParams.add("skuCode", orderLineItemsDto.getSkuCode());
                        queryParams.add("quantity", orderLineItemsDto.getQuantity().toString());
                        var response =  webClientBuilder.build().get()
                                .uri("http://inventory-service/api/inventory/",
                                        uriBuilder -> uriBuilder.queryParams(queryParams).build())
                                .retrieve()
                                .bodyToMono(InventoryResponse.class)
                                .block();
                        assert response != null;
                        return response;
                    }).toList();
    }

    private void getPriceAndCalcTotally(OrderPlaceDto orderPlaceDto) {
        orderPlaceDto.getOrderLineItemsList().forEach(orderLineItemsDto ->
        {
            var price = webClientBuilder.build().get()
                    .uri("http://price-service/api/price/" + orderLineItemsDto.getSkuCode() )
                    .retrieve()
                    .bodyToMono(BigDecimal.class)
                    .block();
            assert price != null;
            orderLineItemsDto.setPrice(price);
            orderLineItemsDto.setTotalAmount(price.multiply(BigDecimal.valueOf(orderLineItemsDto.getQuantity())));
        });
    }

    private boolean isAllProductHasStock(List<InventoryResponse> isInStockAllProduct) {
        return isInStockAllProduct.stream().allMatch(inventoryResponse -> inventoryResponse.getIsInStock().equals(Boolean.TRUE));
    }

    private boolean isExistAllProducts(List<Boolean> isExistAllOrders) {
        return isExistAllOrders.stream().allMatch(aBoolean -> aBoolean.equals(Boolean.TRUE));
    }




}
