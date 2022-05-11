package com.myproject.productservice.repository;

import com.myproject.productservice.entity.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends CassandraRepository<Product,String > {

}
