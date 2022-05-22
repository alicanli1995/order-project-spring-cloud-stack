package com.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderservice.dto.request.OrderLineItemsDto;
import com.orderservice.dto.request.OrderPlaceDto;
import com.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.orderservice.dto.AppConst.SUCCESS_RETURN;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {OrderServiceApplicationTests.Initializer.class})
class OrderServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private OrderRepository orderRepository;
	@Container
	private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
			.withDatabaseName("order_service")
			.withUsername("sa")
			.withPassword("sa");

	static class Initializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
					"spring.datasource.username=" + postgreSQLContainer.getUsername(),
					"spring.datasource.password=" + postgreSQLContainer.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@Test
	void shouldCreateProduct() throws Exception {
		OrderPlaceDto orderPlaceDto = getOrderRequest();
		String orderPlaceRequestString = objectMapper.writeValueAsString(orderPlaceDto);
		var result = mockMvc.perform(MockMvcRequestBuilders.post("/order")
						.contentType(MediaType.APPLICATION_JSON)
						.content(orderPlaceRequestString))
				.andExpect(status().isCreated())
				.andReturn();
		var order = orderRepository.findAll().get(0);
		Assertions.assertNotNull(order);
		Assertions.assertEquals(SUCCESS_RETURN.getMessage(),result.getResponse().getContentAsString());
		Assertions.assertEquals("IPHONE_13_PLUS_RED_64_GB",order.getOrderLineItemsList().get(0).getSkuCode());
		Assertions.assertEquals(5,order.getOrderLineItemsList().get(0).getQuantity());
		Assertions.assertEquals(1, orderRepository.findAll().size());
	}

	private OrderPlaceDto getOrderRequest() {
		var order1 = new OrderLineItemsDto();
//		order1.setPrice(BigDecimal.valueOf(12500));
		order1.setSkuCode("IPHONE_13_PLUS_RED_64_GB");
		order1.setQuantity(5);

		var order2 = new OrderLineItemsDto();
//		order2.setPrice(BigDecimal.valueOf(1300));
		order2.setSkuCode("IPHONE_6_PLUS_RED_64_GB");
		order2.setQuantity(2);

		return OrderPlaceDto.builder()
				.orderLineItemsList(List.of(order1,order2))
				.build();
	}


}
