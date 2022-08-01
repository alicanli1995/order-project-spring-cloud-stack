package com.myproject.productservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.Size;

@Table("products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {

    @PrimaryKeyColumn(name = "id" , ordinal = 0 ,
            type = PrimaryKeyType.PARTITIONED,
            ordering = Ordering.DESCENDING)
    @Id
    private String id;

    @Size(min = 3)
    @Column
    private String name;

    @Column
    @Size(min = 5)
    private String description;


}
