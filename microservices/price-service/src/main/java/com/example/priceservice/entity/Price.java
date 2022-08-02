package com.example.priceservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;

@Table("price")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Price {

    @Id
    private String name;

    @Column("price_product")
    private BigDecimal priceProduct;

}
