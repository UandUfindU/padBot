package com.bigWind.padBot.meetingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bigWind.padBot.meetingSystem.Repository.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean doesStudentExist(String studentId, String studentName) {
        return studentRepository.existsByStudentIdAndStudentName(studentId, studentName);
    }
}


