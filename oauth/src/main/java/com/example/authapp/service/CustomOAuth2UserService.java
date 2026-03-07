package com.example.authapp.service;

import com.example.authapp.model.User;
import com.example.authapp.model.User.AuthProvider;
import com.example.authapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email;
        String name;
        String imageUrl;
        String providerId;

        if ("google".equals(registrationId)) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            imageUrl = (String) attributes.get("picture");
            providerId = (String) attributes.get("sub");
        } else if ("github".equals(registrationId)) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("login");
            imageUrl = (String) attributes.get("avatar_url");
            providerId = String.valueOf(attributes.get("id"));

            // GitHub may not return email; use login as fallback
            if (email == null) {
                email = name + "@github.com";
            }
        } else {
            throw new OAuth2AuthenticationException("Unsupported provider: " + registrationId);
        }

        AuthProvider provider = AuthProvider.valueOf(registrationId.toUpperCase());

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(name);
            user.setImageUrl(imageUrl);
            userRepository.save(user);
        } else {
            User newUser = User.builder()
                    .name(name)
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .imageUrl(imageUrl)
                    .build();
            userRepository.save(newUser);
        }

        return oAuth2User;
    }
}
