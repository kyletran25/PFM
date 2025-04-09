package com.finace.management.exception;

import com.finace.management.dto.response.ApiResponse;
import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handlingException(Exception e){
        log.warn("Exception: ", e);
        return e;
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        log.warn("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

//        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
//        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }


    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException e){
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        log.warn("AppException: ", e);
        return ResponseEntity.status(errorCode.getCode()).body(apiResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialException(BadCredentialsException e) {
        return new ResponseEntity<>("Your ID or password is incorrect. Please try again.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(BadCredentialsException e) {
        return new ResponseEntity<>("Upload file fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(InvalidTokenException.class)
//    public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException ex) {
//        return new ResponseEntity<>("Unauthorized: Invalid token", HttpStatus.UNAUTHORIZED);
//    }
}
