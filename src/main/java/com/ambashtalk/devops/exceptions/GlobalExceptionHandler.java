package com.ambashtalk.devops.exceptions;

import com.ambashtalk.devops.exceptions.jwt.JwtParseException;
import com.ambashtalk.devops.exceptions.jwt.TokenRefreshException;
import com.ambashtalk.devops.exceptions.person.PersonAlreadyExistsException;
import com.ambashtalk.devops.exceptions.person.PersonNotFoundException;
import com.ambashtalk.devops.exceptions.role.RoleNotFoundException;
import com.ambashtalk.devops.payload.response.BaseResponse;
import com.ambashtalk.devops.payload.response.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // Person
    @ExceptionHandler(PersonAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<ErrorResponse> handlePersonAlreadyExistsException(PersonAlreadyExistsException ex) {
        return BaseResponse.build(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse<ErrorResponse> handlePersonNotFoundException(PersonNotFoundException ex) {
        return BaseResponse.build(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    // Role
    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex) {
        return BaseResponse.build(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    //JWT
    @ExceptionHandler(TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseResponse<ErrorResponse> handleTokenRefreshException(TokenRefreshException ex) {
        return BaseResponse.build(new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    @ExceptionHandler(JwtParseException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse<ErrorResponse> handleJwtParseException(JwtParseException ex) {
        return BaseResponse.build(new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    // Access
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse<ErrorResponse> AccessDeniedException(AccessDeniedException ex) {
        return BaseResponse.build(new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    // All other exception
    @ExceptionHandler(Exception.class)
    public BaseResponse<ErrorResponse> handleException(AccessDeniedException ex, HttpServletResponse response) {
        return BaseResponse.build(new ErrorResponse(HttpStatus.valueOf(response.getStatus()), ex.getMessage()));
    }
}
