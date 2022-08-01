package com.statistics.app.domain;

import com.statistics.app.domain.base.StatisticBaseDomain;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "all_orders")
@EntityListeners(AuditingEntityListener.class)
public class AllOrders extends StatisticBaseDomain {

    private String skuCode;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal totalAmount;

    @CreatedDate
    private LocalDate orderDate;

}
