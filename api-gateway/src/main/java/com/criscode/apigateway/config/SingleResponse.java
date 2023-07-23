package com.criscode.apigateway.config;

public record SingleResponse<T>(T data) {

    public static <T> SingleResponse<T> of(T data) {
        return new SingleResponse<>(data);
    }

}
