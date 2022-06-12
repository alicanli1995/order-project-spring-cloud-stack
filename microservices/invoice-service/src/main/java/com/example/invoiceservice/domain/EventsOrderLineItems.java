package com.example.invoiceservice.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "t_events_order_line_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class EventsOrderLineItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skuCode;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal totalAmount;


}
