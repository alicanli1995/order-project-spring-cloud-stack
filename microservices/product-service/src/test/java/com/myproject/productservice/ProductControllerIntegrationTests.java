package com.myproject.productservice;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.productservice.dto.request.CreateProductDto;
import com.myproject.productservice.entity.Product;
import com.myproject.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    private static final String KEYSPACE_NAME = "product_service";

    @Container
    private static final CassandraContainer cassandra =
            (CassandraContainer) new CassandraContainer("cassandra:latest")
            .withExposedPorts(9042);

    @BeforeAll
    static void setupCassandraConnectionProperties() {
        System.setProperty("spring.data.cassandra.keyspace-name", KEYSPACE_NAME);
        System.setProperty("spring.data.cassandra.contact-points", cassandra.getContainerIpAddress());
        System.setProperty("spring.data.cassandra.port", String.valueOf(cassandra.getMappedPort(9042)));

        createKeyspace(cassandra.getCluster());
    }

    static void createKeyspace(Cluster cluster) {
        try(Session session = cluster.connect()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + " WITH replication = \n" +
                    "{'class':'SimpleStrategy','replication_factor':'1'};");
        }
    }

    @Test
    void shouldCreateProduct() throws Exception {
        CreateProductDto productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, productRepository.findAll().size());
    }

    @Test
    void shouldReturnProducts() throws Exception {
        CreateProductDto productRequest = getProductRequest();
        CreateProductDto productRequest2 = getProductRequest();
        CreateProductDto productRequest3 = getProductRequest();

        var response = productRepository.save(modelMapper.map(productRequest, Product.class));
        var response2=  productRepository.save(modelMapper.map(productRequest2, Product.class));
        var response3= productRepository.save(modelMapper.map(productRequest3, Product.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/product")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3));

        Assertions.assertEquals(3, productRepository.findAll().size());
        Assertions.assertNotNull(productRepository.findById(response.getId()));
        Assertions.assertEquals("IPHONE_13_PLUS_RED",productRepository.findById(response.getId()).get().getName());
        Assertions.assertNotNull(productRepository.findById(response2.getId()));
        Assertions.assertNotNull(productRepository.findById(response3.getId()));
//        Assertions.assertEquals(BigDecimal.valueOf(15999), productRepository.findById(response3.getId()).get().getPrice());

    }

    private CreateProductDto getProductRequest() {
        return CreateProductDto.builder()
                .name("IPHONE_13_PLUS_RED")
                .description("iPhone 13 Red 64 GB")
                .price(BigDecimal.valueOf(15999))
                .build();
    }

}
