package com.example.priceservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePriceRequest {

    @NotBlank(message = "Name must be not null !")
    private String name;

    @NotNull(message = "Price must be not null !")
    @Positive(message = "Price must be positive ! ")
    private BigDecimal price;
}
