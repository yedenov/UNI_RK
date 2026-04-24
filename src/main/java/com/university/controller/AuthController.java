package com.university.controller;

import com.university.model.Role;
import com.university.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           Model model) {
        if (userService.existsByUsername(username)) {
            model.addAttribute("error", "Username already exists");
            return "auth/register";
        }
        if (userService.existsByEmail(email)) {
            model.addAttribute("error", "Email already exists");
            return "auth/register";
        }
        userService.createUser(username, email, password, firstName, lastName, Role.STUDENT);
        return "redirect:/auth/login?registered";
    }
}