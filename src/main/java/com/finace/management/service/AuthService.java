package com.finace.management.service;

import com.finace.management.client.BrevoClient;
import com.finace.management.constants.LoginType;
import com.finace.management.constants.UserStatus;
import com.finace.management.dto.request.AuthReqDto;
import com.finace.management.dto.request.BrevoEmailReqDto;
import com.finace.management.dto.request.SignupReqDto;
import com.finace.management.dto.response.AuthResDto;
import com.finace.management.entity.AppUser;
import com.finace.management.exception.AppException;
import com.finace.management.exception.ErrorCode;
import com.finace.management.repository.UserRepository;
import com.finace.management.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final BrevoClient brevoClient;

    @Value("${spring.security.password.pattern.special-characters}")
    private String specialCharacters;
    @Value("${spring.security.password.pattern.english-and-special-pattern}")
    private String englishAndSpecialPattern;

    public AuthResDto login(AuthReqDto reqDto) {
        try {
            // match usname == pw
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(reqDto.getUsername(), reqDto.getPassword())
            );
            AppUser user = (AppUser) auth.getPrincipal();
            String token = jwtService.generateToken(user);

            return AuthResDto.builder().token(token).build();
        } catch (Exception e) {
            throw new AppException(ErrorCode.WRONG_US_PW_EXCEPTION);
        }
    }

    @Transactional
    public void signup(SignupReqDto reqDto) {
        // check email, usname duplicate
        if (userRepository.existsByUserNameOrEmail(reqDto.getUsername(), reqDto.getEmail())) {
            throw new AppException(ErrorCode.US_EMAIL_EXIST_EXCEPTION);
        }
        // check pw and re pw duplicate
        if (!reqDto.getPassword().equals(reqDto.getRePassword())) {
            throw new AppException(ErrorCode.REPW_NOT_MATCH_EXCEPTION);
        }
        // check pw match regex
        validatePassword(reqDto.getPassword());
        // add new user with status 'VERIFY'
        AppUser appUser = userRepository.save(AppUser.builder()
                .userName(reqDto.getUsername())
                .email(reqDto.getEmail())
                .password(passwordEncoder.encode(reqDto.getPassword()))
                .loginType(LoginType.NORMAL)
                .status(UserStatus.VERIFY)
                .build());
        // send mail with button token
        String token = jwtService.generateToken(appUser);
        sendSignupEmail(reqDto.getEmail(), token, reqDto.getUsername());
    }

    private void validatePassword(String password) {
        Pattern pattern = Pattern.compile(englishAndSpecialPattern);
        if (password.length() < 8) {
            throw new AppException(ErrorCode.PW_LEAST_8_CHAR);
        }
        if (password.chars().noneMatch(Character::isLetter)) {
            throw new AppException(ErrorCode.PW_LEAST_1_LETTER);
        }
        if (password.chars().noneMatch(Character::isDigit)) {
            throw new AppException(ErrorCode.PW_LEAST_1_NUM);
        }
        if (password.chars().noneMatch(ch -> specialCharacters.indexOf(ch) >= 0)) {
            throw new AppException(ErrorCode.PW_LEAST_1_SPEC);
        }
        if (!pattern.matcher(password).matches()) {
            throw new AppException(ErrorCode.PW_MUST_ENG);
        }
    }

    public void sendSignupEmail(String to, String token, String username) {
        BrevoEmailReqDto request = BrevoEmailReqDto.builder()
                .to(List.of(Map.of("email", to)))
                .templateId(1)
                .params(
                        Map.of("USERNAME", username
                                , "SIGNUP_TOKEN", token,
                                "APP_NAME", "PFM"))
                .build();
        brevoClient.sendEmail(request);
    }

    @Transactional
    public AuthResDto activeAccount(String token) {
        String username = jwtService.extractUsername(token);
        AppUser appUser = userRepository.findByUserNameOrEmail(username, username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        appUser.setStatus(UserStatus.ACTIVE);
        return AuthResDto.builder().token(token).build();
    }
}
