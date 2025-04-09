package com.finace.management.controller;

import com.finace.management.dto.request.AuthReqDto;
import com.finace.management.dto.response.ApiResponse;
import com.finace.management.dto.response.AuthResDto;
import com.finace.management.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login by username/email and password")
    public ApiResponse<AuthResDto> login(@RequestBody AuthReqDto reqDto) {
        return ApiResponse.<AuthResDto>builder().result(authService.login(reqDto)).build();
    }

    @PostMapping("/sign_up")
    @Operation(summary = "Signup with username/email and password")
    public ApiResponse<AuthResDto> signUp(@RequestBody AuthReqDto reqDto) {
        return ApiResponse.<AuthResDto>builder().result(authService.singUp(reqDto)).build();
    }
}
