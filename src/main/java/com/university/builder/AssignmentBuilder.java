package com.university.builder;

import com.university.model.Assignment;
import com.university.model.User;
import java.time.LocalDateTime;

public class AssignmentBuilder {

    private String title;
    private String description;
    private LocalDateTime deadline;
    private User teacher;
    private User student;

    public AssignmentBuilder title(String title) {
        this.title = title;
        return this;
    }

    public AssignmentBuilder description(String description) {
        this.description = description;
        return this;
    }

    public AssignmentBuilder deadline(LocalDateTime deadline) {
        this.deadline = deadline;
        return this;
    }

    public AssignmentBuilder teacher(User teacher) {
        this.teacher = teacher;
        return this;
    }

    public AssignmentBuilder student(User student) {
        this.student = student;
        return this;
    }

    public Assignment build() {
        Assignment a = new Assignment();
        a.setTitle(title);
        a.setDescription(description);
        a.setDeadline(deadline);
        a.setTeacher(teacher);
        a.setStudent(student);
        return a;
    }
}