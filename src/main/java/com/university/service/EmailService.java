package com.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendAssignmentNotification(String toEmail, String studentName, String assignmentTitle) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("New Assignment: " + assignmentTitle);
        message.setText("Dear " + studentName + ",\n\n"
                + "You have been assigned a new task: " + assignmentTitle + "\n"
                + "Please log in to the University Portal to view the details.\n\n"
                + "Best regards,\nUniversity Management System");
        mailSender.send(message);
    }

    @Async
    public void sendGradeNotification(String toEmail, String studentName,
                                      String assignmentTitle, int score) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Grade received for: " + assignmentTitle);
        message.setText("Dear " + studentName + ",\n\n"
                + "Your assignment \"" + assignmentTitle + "\" has been graded.\n"
                + "Score: " + score + "\n\n"
                + "Best regards,\nUniversity Management System");
        mailSender.send(message);
    }
}