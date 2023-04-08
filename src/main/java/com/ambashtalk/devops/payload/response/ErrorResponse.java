package com.ambashtalk.devops.payload.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final Date timestamp;
    @JsonProperty("status")
    private int statusCode;
    private Object data;

    public ErrorResponse() {
        timestamp = new Date();
    }

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this();
        Map<String, String> data = new HashMap<>();
        data.put("message", message);

        this.statusCode = httpStatus.value();
        this.data = data;
    }

}
