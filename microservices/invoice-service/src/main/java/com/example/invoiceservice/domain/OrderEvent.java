package com.example.invoiceservice.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "t_orders_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class OrderEvent {

    @Id
    @GeneratedValue
    private Long id;

    private String orderId;

    private String userName;

    @Email
    private String userEmail;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    private Date createdDate;

    @UpdateTimestamp
    private Date lastModifiedDate;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<EventsOrderLineItems> orderLineItems;

}
