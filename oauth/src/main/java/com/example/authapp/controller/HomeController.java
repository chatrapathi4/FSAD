package com.example.authapp.controller;

import com.example.authapp.model.User;
import com.example.authapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String displayName = "User";
        String email = "";
        String imageUrl = null;
        String provider = "LOCAL";

        if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            // OAuth2 login
            String oauthEmail = oAuth2User.getAttribute("email");
            String oauthName = oAuth2User.getAttribute("name");

            // GitHub fallback
            if (oauthName == null) {
                oauthName = oAuth2User.getAttribute("login");
            }
            if (oauthEmail == null && oauthName != null) {
                oauthEmail = oauthName + "@github.com";
            }

            displayName = oauthName != null ? oauthName : "OAuth User";
            email = oauthEmail != null ? oauthEmail : "";
            imageUrl = oAuth2User.getAttribute("picture");
            if (imageUrl == null) {
                imageUrl = oAuth2User.getAttribute("avatar_url");
            }

            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                provider = userOpt.get().getProvider().name();
            }

        } else if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User userDetails) {
            // Form login
            email = userDetails.getUsername();
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                displayName = userOpt.get().getName();
                imageUrl = userOpt.get().getImageUrl();
                provider = userOpt.get().getProvider().name();
            }
        }

        model.addAttribute("displayName", displayName);
        model.addAttribute("email", email);
        model.addAttribute("imageUrl", imageUrl);
        model.addAttribute("provider", provider);

        return "home";
    }
}
