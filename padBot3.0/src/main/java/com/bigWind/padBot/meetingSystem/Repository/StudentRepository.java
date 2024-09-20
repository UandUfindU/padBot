package com.bigWind.padBot.meetingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bigWind.padBot.meetingSystem.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByStudentIdAndStudentName(String studentId, String studentName);
}

