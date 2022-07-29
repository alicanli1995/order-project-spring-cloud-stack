package com.example.invoiceservice.repository;

import com.example.invoiceservice.domain.FailureRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FailureRecordRepository extends JpaRepository<FailureRecord, Long> {
    List<FailureRecord> findAllByStatus(String retry);

}
