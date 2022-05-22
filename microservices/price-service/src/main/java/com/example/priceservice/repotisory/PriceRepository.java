package com.example.priceservice.repotisory;


import com.example.priceservice.entity.Price;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface PriceRepository extends CassandraRepository<Price,String > {

    Optional<Price> findByName(String name);
}
