package com.orderservice.dto;

import lombok.Getter;

@Getter
public enum AppConst {
    SUCCESS_RETURN(201,"Order Placed Successfully.");

    private int code;
    private String message;

    AppConst(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
