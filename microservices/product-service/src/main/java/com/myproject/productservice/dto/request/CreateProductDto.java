package com.myproject.productservice.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    @Size(min = 3)
    @NotBlank
    private String name;

    @Size(min = 5)
    @NotBlank
    private String description;

    @Positive
    @NotNull
    private BigDecimal price;

}
