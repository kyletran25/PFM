package com.finace.management.config;

import com.finace.management.entity.AppUser;
import com.finace.management.repository.UserRepository;
import com.finace.management.utils.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        AppUser appUser = userRepository.findByUserNameOrEmail(email, email).orElseThrow();
        String token = jwtService.generateToken(appUser);

        String redirectUrl = "http://localhost:3000/oauth2/redirect?token=" + token;
        response.sendRedirect(redirectUrl);
    }
}
