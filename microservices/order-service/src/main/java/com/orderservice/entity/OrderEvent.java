package com.orderservice.entity;

import com.orderservice.dto.request.OrderLineItemsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderEvent {

    @NotNull
    private String orderId;

    @NotNull
    private List<OrderLineItemsDto> orderLineItems;
}
