package com.notification.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventsOrderLineItems {

    private Long id;

    private String skuCode;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal totalAmount;


}
