package com.university.controller;

import com.university.model.Role;
import com.university.model.User;
import com.university.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("students", userService.findByRole(Role.STUDENT));
        model.addAttribute("teachers", userService.findByRole(Role.TEACHER));
        return "admin/dashboard";
    }

    @GetMapping("/create-user")
    public String createUserPage(Model model) {
        model.addAttribute("roles", new Role[]{Role.STUDENT, Role.TEACHER});
        return "admin/create-user";
    }

    @PostMapping("/create-user")
    public String createUser(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam Role role) {
        userService.createUser(username, email, password, firstName, lastName, role);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        // soft delete — disable
        User user = userService.findById(id);
        user.setEnabled(false);
        userService.updateUser(user);
        return "redirect:/admin/dashboard";
    }
}