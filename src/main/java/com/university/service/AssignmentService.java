package com.university.service;

import com.university.builder.AssignmentBuilder;
import com.university.model.*;
import com.university.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final GradeRepository gradeRepository;
    private final EmailService emailService;

    public Assignment createAssignment(String title, String description,
                                       LocalDateTime deadline, User teacher, User student) {
        Assignment assignment = new AssignmentBuilder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .teacher(teacher)
                .student(student)
                .build();

        Assignment saved = assignmentRepository.save(assignment);

        // Email notification
        emailService.sendAssignmentNotification(
                student.getEmail(),
                student.getFirstName() + " " + student.getLastName(),
                title
        );

        return saved;
    }

    public List<Assignment> getAssignmentsByStudent(User student) {
        return assignmentRepository.findByStudent(student);
    }

    public List<Assignment> getAssignmentsByTeacher(User teacher) {
        return assignmentRepository.findByTeacher(teacher);
    }

    public Assignment findById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    public void submitAssignment(Long assignmentId, String answer) {
        Assignment assignment = findById(assignmentId);
        assignment.setStudentAnswer(answer);
        assignment.setStatus(AssignmentStatus.SUBMITTED);
        assignmentRepository.save(assignment);
    }

    public void gradeAssignment(Long assignmentId, int score, String comment, User teacher) {
        Assignment assignment = findById(assignmentId);
        assignment.setStatus(AssignmentStatus.GRADED);
        assignmentRepository.save(assignment);

        Grade grade = Grade.builder()
                .assignment(assignment)
                .student(assignment.getStudent())
                .teacher(teacher)
                .score(score)
                .comment(comment)
                .build();
        gradeRepository.save(grade);

        // Email notification
        emailService.sendGradeNotification(
                assignment.getStudent().getEmail(),
                assignment.getStudent().getFirstName(),
                assignment.getTitle(),
                score
        );
    }

    public List<Grade> getGradesByStudent(User student) {
        return gradeRepository.findByStudent(student);
    }
}