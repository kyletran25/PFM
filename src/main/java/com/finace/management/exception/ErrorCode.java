package com.finace.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_TOKEN_EXCEPTION(9000, "Invalid token", HttpStatus.BAD_REQUEST),
    WRONG_US_PW_EXCEPTION(9001, "Wrong password or username/email", HttpStatus.BAD_REQUEST),
    US_EMAIL_EXIST_EXCEPTION(9002, "Username/Email have existed", HttpStatus.BAD_REQUEST),
    REPW_NOT_MATCH_EXCEPTION(9003, "Password and re password is not matched", HttpStatus.BAD_REQUEST),
    PW_LEAST_8_CHAR(9004, "Password must be at least 8 characters long", HttpStatus.BAD_REQUEST),
    PW_LEAST_1_LETTER(9005, "Password must contain at least one letter", HttpStatus.BAD_REQUEST),
    PW_LEAST_1_NUM(9006, "Password must contain at least one number", HttpStatus.BAD_REQUEST),
    PW_LEAST_1_SPEC(9007, "Password must contain at least one special character (!@#$%^&*)", HttpStatus.BAD_REQUEST),
    PW_MUST_ENG(9008, "Password must be written in English and only contain letters, digits, and special characters (!@#$%^&*)", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(9009, "User didn't exist", HttpStatus.BAD_REQUEST),
    AUTHORIZATION_DENIED(9010, "Authorization Denied", HttpStatus.UNAUTHORIZED),
    DUPLICATE_CATEGORY(9011, "Duplicate category", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED(9012, "Category didn't exist", HttpStatus.BAD_REQUEST),
    ITEM_NOT_EXISTED(9013, "Item didn't exist", HttpStatus.BAD_REQUEST),
    INCOME_MONTH_NOT_EXISTED(9014, "Income Monthly didn't exist", HttpStatus.BAD_REQUEST),
    CATEGORY_EXPENSE_EXCEEDS_INCOME(9015, "Category expense exceeds the current income", HttpStatus.BAD_REQUEST),
    INCOME_MONTHLY_EXISTED(9016, "Currently, we just allow to create one income monthly", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(8888, "Uncategorized exception", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
