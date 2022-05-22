package com.myproject.productservice.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

}
