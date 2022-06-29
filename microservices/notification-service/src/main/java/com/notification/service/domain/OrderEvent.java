package com.notification.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent {

    private Long id;

    private String orderId;

    private String userName;

    private String userEmail;

    private OrderStatus orderStatus;

    private Date createdDate;

    private Date lastModifiedDate;

    private List<EventsOrderLineItems> orderLineItems;

}
