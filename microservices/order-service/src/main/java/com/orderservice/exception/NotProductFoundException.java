package com.orderservice.exception;

import lombok.Getter;

@Getter
public class NotProductFoundException extends RuntimeException {
    private String messageId;
    private String debugId;

    public NotProductFoundException(String message, String messageId, String debugId) {
        super(message);
        this.messageId = messageId;
        this.debugId = debugId;
    }
}
