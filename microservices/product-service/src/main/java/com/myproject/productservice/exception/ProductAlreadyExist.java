package com.myproject.productservice.exception;


import lombok.Getter;

@Getter
public class ProductAlreadyExist  extends RuntimeException {
    private String messageId;
    private String debugId;

    public ProductAlreadyExist(String message, String messageId, String debugId) {
        super(message);
        this.messageId = messageId;
        this.debugId = debugId;
    }


}
