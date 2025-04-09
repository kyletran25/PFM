package com.finace.management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupReqDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String rePassword;
    @NotBlank
    private String email;
}
