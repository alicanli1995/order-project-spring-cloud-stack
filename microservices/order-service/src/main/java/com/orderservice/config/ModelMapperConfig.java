package com.orderservice.config;


import com.orderservice.dto.request.OrderLineItemsDto;
import com.orderservice.dto.request.OrderPlaceDto;
import com.orderservice.entity.Order;
import com.orderservice.entity.OrderLineItems;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class ModelMapperConfig {

    private static final Converter<OrderLineItemsDto, OrderLineItems> ORDER_LINE_ITEMS_DTO_ORDER_LINE_ITEMS_CONVERTER =
            context -> OrderLineItems.builder()
                    .quantity(context.getSource().getQuantity())
                    .skuCode(context.getSource().getSkuCode())
                    .totalAmount(context.getSource().getTotalAmount())
                    .price(context.getSource().getPrice())
                    .build();

    private static final Converter<OrderPlaceDto, Order> ORDER_PLACE_DTO_ORDER_CONVERTER =
            context -> {
                var listOfDto = context.getSource().getOrderLineItemsList();
                var entityObject = listOfDto.stream().map(orderLineItemsDto -> {
                    var orderLineItem = new OrderLineItems();
                    orderLineItem.setPrice(orderLineItemsDto.getPrice());
                    orderLineItem.setTotalAmount(orderLineItemsDto.getTotalAmount());
                    orderLineItem.setQuantity(orderLineItemsDto.getQuantity());
                    orderLineItem.setSkuCode(orderLineItemsDto.getSkuCode());
                    return orderLineItem;
                }).toList();
                return Order.builder()
                        .orderNumber(UUID.randomUUID().toString())
                        .orderLineItemsList(entityObject)
                        .build();
            };


    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(ORDER_LINE_ITEMS_DTO_ORDER_LINE_ITEMS_CONVERTER,OrderLineItemsDto.class,OrderLineItems.class);
        modelMapper.addConverter(ORDER_PLACE_DTO_ORDER_CONVERTER,OrderPlaceDto.class,Order.class);
        return modelMapper;
    }

}
