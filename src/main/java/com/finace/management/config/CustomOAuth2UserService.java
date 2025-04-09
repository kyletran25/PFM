package com.finace.management.config;

import com.finace.management.constants.LoginType;
import com.finace.management.constants.UserStatus;
import com.finace.management.entity.AppUser;
import com.finace.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        LoginType loginType;
        String email;
        if ("google".equals(registrationId)) {
            email = oAuth2User.getAttribute("email");
            loginType = LoginType.GOOGLE;
        } else if ("facebook".equals(registrationId)) {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            email = (String) attributes.get("email");
            loginType = LoginType.FACEBOOK;
        } else {
            throw new OAuth2AuthenticationException("Unsupported provider: " + registrationId);
        }

        AppUser user = userRepository.findByUserNameOrEmail(email, email)
                .orElseGet(() -> {
                    AppUser newUser = AppUser.builder()
                            .userName(email)
                            .email(email)
                            .loginType(loginType)
                            .status(UserStatus.ACTIVE)
                            .build();
                    return userRepository.save(newUser);
                });

        return oAuth2User;
    }
}
