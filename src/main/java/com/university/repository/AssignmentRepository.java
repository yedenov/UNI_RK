package com.university.repository;

import com.university.model.Assignment;
import com.university.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByStudent(User student);
    List<Assignment> findByTeacher(User teacher);
}