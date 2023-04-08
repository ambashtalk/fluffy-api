package com.ambashtalk.devops.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SignInResponse {
    private Long id;
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private String username;
    private String email;
    private List<String> roles;

}
