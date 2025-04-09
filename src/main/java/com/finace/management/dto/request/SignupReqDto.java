package com.finace.management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupReqDto {
    private String username;
    private String password;
    private String rePassword;
    private String email;
}
