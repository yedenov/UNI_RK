package com.university.repository;

import com.university.model.Grade;
import com.university.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudent(User student);
    List<Grade> findByTeacher(User teacher);
}