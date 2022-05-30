package com.orderservice.dto;

import lombok.Getter;

@Getter
public enum AppConst {
    SUCCESS_RETURN(201,"Order Placed Successfully."),
    FALLBACK_METHOD_RETURN(500, "Oppsss ! Something went wrong. Order try after some time .... ");
    private int code;
    private String message;

    AppConst(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
