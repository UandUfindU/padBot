package com.bigWind.padBot.meetingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bigWind.padBot.meetingSystem.service.StudentService;

@RestController
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/checkStudent")
    public boolean checkStudent(@RequestParam("studentId") String studentId, @RequestParam("studentName") String studentName) {
        return studentService.doesStudentExist(studentId, studentName);
    }
}

