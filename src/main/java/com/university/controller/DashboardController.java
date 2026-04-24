package com.university.controller;

import com.university.model.Role;
import com.university.model.User;
import com.university.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return switch (user.getRole()) {
            case ADMIN   -> "redirect:/admin/dashboard";
            case TEACHER -> "redirect:/teacher/dashboard";
            case STUDENT -> "redirect:/student/dashboard";
        };
    }
}