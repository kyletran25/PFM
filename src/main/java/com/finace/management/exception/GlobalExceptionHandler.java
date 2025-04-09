package com.finace.management.exception;

import com.finace.management.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final int INVALID_INPUT_CODE = 1111;

    @ExceptionHandler(Exception.class)
    Object handlingException(Exception e){
        log.warn("Exception: ", e);
        return e;
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        log.warn("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException e){
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        log.warn("AppException: ", e);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var error = ex.getFieldError();
        ApiResponse apiResponse = new ApiResponse();
        try {
            apiResponse.setCode(INVALID_INPUT_CODE);
            apiResponse.setMessage(error.getDefaultMessage());
            apiResponse.setResult(error.getField());
            log.info("MethodArgumentNotValidException: {}", error);
        } catch (Exception e) {
            apiResponse.setCode(INVALID_INPUT_CODE);
            apiResponse.setMessage("Uncategorized error");
            log.error("Error: ", e);
        }
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            apiResponse.setCode(ErrorCode.AUTHORIZATION_DENIED.getCode());
            apiResponse.setMessage(ErrorCode.AUTHORIZATION_DENIED.getMessage());
            log.info("AuthorizationDeniedException: {}", ex.getMessage());
        } catch (Exception e) {
            apiResponse.setCode(INVALID_INPUT_CODE);
            apiResponse.setMessage("Uncategorized error");
            log.error("Error: ", e);
        }
        return ResponseEntity.status(ErrorCode.AUTHORIZATION_DENIED.getHttpStatus()).body(apiResponse);
    }
}
