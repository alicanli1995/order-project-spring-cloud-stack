package com.myproject.productservice.repository;

import com.myproject.productservice.entity.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ProductRepository extends CassandraRepository<Product,String > {

}
