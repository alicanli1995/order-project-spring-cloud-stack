package com.inventory.inventoryservice.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ErrorMessage {
    private String messageId;
    private String debugId;
    private String message;

}