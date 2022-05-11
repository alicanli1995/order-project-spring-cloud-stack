package com.myproject.productservice.exception;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException {
    private String messageId;
    private String debugId;

    public ProductNotFoundException(String message, String messageId, String debugId) {
        super(message);
        this.messageId = messageId;
        this.debugId = debugId;
    }

}
