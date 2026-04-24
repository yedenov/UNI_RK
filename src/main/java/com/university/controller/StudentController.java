package com.university.controller;

import com.university.model.*;
import com.university.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final UserService userService;
    private final AssignmentService assignmentService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User student = userService.findByUsername(userDetails.getUsername());
        List<Assignment> assignments = assignmentService.getAssignmentsByStudent(student);
        List<Grade> grades = assignmentService.getGradesByStudent(student);
        model.addAttribute("student", student);
        model.addAttribute("assignments", assignments);
        model.addAttribute("grades", grades);
        return "student/dashboard";
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User student = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", student);
        return "profile";
    }

    @PostMapping("/submit/{assignmentId}")
    public String submitAssignment(@PathVariable Long assignmentId,
                                   @RequestParam String answer) {
        assignmentService.submitAssignment(assignmentId, answer);
        return "redirect:/student/dashboard";
    }
}