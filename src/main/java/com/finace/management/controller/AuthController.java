package com.finace.management.controller;

import com.finace.management.dto.request.AuthReqDto;
import com.finace.management.dto.request.SignupReqDto;
import com.finace.management.dto.response.ApiResponse;
import com.finace.management.dto.response.AuthResDto;
import com.finace.management.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login by username/email and password")
    public ApiResponse<AuthResDto> login(@RequestBody @Valid AuthReqDto reqDto) {
        return ApiResponse.<AuthResDto>builder().result(authService.login(reqDto)).build();
    }

    @PostMapping("/sign_up")
    @Operation(summary = "Signup with username/email and password")
    public ApiResponse<Void> signup(@RequestBody @Valid SignupReqDto reqDto) {
        authService.signup(reqDto);
        return ApiResponse.<Void>builder().message("Signup successfully").build();
    }

    @PostMapping("/confirm")
    @Operation(summary = "Verify email, active user account")
    public ApiResponse<AuthResDto> confirm(@RequestParam(name = "token") String token) {
        return ApiResponse.<AuthResDto>builder().result(authService.activeAccount(token)).build();
    }
}
