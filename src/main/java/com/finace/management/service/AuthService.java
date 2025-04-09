package com.finace.management.service;

import com.finace.management.dto.request.AuthReqDto;
import com.finace.management.dto.response.AuthResDto;
import com.finace.management.entity.AppUser;
import com.finace.management.repository.UserRepository;
import com.finace.management.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthResDto login(AuthReqDto reqDto) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(reqDto.getUsername(), reqDto.getPassword())
            );
            AppUser user = (AppUser) auth.getPrincipal();
            String token = jwtService.generateToken(user);

            return AuthResDto.builder().token(token).build();
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    public AuthResDto singUp(AuthReqDto reqDto) {
        // Check and add new user to DB with status VERIFY

        return null;
    }
}
