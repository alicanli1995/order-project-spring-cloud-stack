package com.example.invoiceservice.repository;

import com.example.invoiceservice.domain.FailureRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailureRecordRepository extends JpaRepository<FailureRecord, Long> {
}
