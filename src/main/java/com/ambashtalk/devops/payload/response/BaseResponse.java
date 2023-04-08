package com.ambashtalk.devops.payload.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class BaseResponse<T> implements Serializable {
    private Integer status;
    private T data;
    private T error;

    public static <T> BaseResponse<T> build(final T data) {
        if(data instanceof ErrorResponse) {
            return BaseResponse.<T>builder()
                    .error(data)
                    .build();
        }
        return BaseResponse.<T>builder()
                .status(200)
                .data(data)
                .build();
    }
}
