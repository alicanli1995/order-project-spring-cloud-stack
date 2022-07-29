package com.example.invoiceservice.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class FailureRecord {

    @Id
    @GeneratedValue
    private Integer id;

    private String topic_record;

    private String key_record;

    private String errorRecord;

    private Integer partition_record;

    private Long offset_value_record;

    private String exception_record;

    private String status;


}
