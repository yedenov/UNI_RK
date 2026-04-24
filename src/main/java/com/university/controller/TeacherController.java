package com.university.controller;

import com.university.model.*;
import com.university.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final UserService userService;
    private final AssignmentService assignmentService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User teacher = userService.findByUsername(userDetails.getUsername());
        List<Assignment> assignments = assignmentService.getAssignmentsByTeacher(teacher);
        model.addAttribute("teacher", teacher);
        model.addAttribute("assignments", assignments);
        return "teacher/dashboard";
    }

    @GetMapping("/create-assignment")
    public String createAssignmentPage(Model model) {
        model.addAttribute("students", userService.findByRole(Role.STUDENT));
        return "teacher/create-assignment";
    }

    @PostMapping("/create-assignment")
    public String createAssignment(@AuthenticationPrincipal UserDetails userDetails,
                                   @RequestParam String title,
                                   @RequestParam String description,
                                   @RequestParam String deadline,
                                   @RequestParam Long studentId) {
        User teacher = userService.findByUsername(userDetails.getUsername());
        User student = userService.findById(studentId);
        LocalDateTime dl = LocalDateTime.parse(deadline);
        assignmentService.createAssignment(title, description, dl, teacher, student);
        return "redirect:/teacher/dashboard";
    }

    @GetMapping("/grade/{assignmentId}")
    public String gradePage(@PathVariable Long assignmentId, Model model) {
        model.addAttribute("assignment", assignmentService.findById(assignmentId));
        return "teacher/grade";
    }

    @PostMapping("/grade/{assignmentId}")
    public String gradeAssignment(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable Long assignmentId,
                                  @RequestParam int score,
                                  @RequestParam String comment) {
        User teacher = userService.findByUsername(userDetails.getUsername());
        assignmentService.gradeAssignment(assignmentId, score, comment, teacher);
        return "redirect:/teacher/dashboard";
    }
}