package com.finace.management.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        String errorMessage = "";
        if(accessDeniedException != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            response.setContentType("application/json");

            if (authentication != null) {
                errorMessage = "{\"error\": \"You do not have permission to access this function.\"}";
            }else{ //
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                errorMessage = "{\"error\": \"Unauthorized: Invalid token\"}";
            }
        }

        // Write the error message to the response body
        PrintWriter writer = response.getWriter();
        writer.write(errorMessage);
        writer.flush();
    }
}
