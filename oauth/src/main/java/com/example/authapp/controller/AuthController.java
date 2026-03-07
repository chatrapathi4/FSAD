package com.example.authapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "GitHub authentication failed. Please try again.");
        }
        if (logout != null) {
            model.addAttribute("logoutMsg", "You have been logged out successfully.");
        }
        return "login";
    }
}
