package com.example.student.controller;

import com.example.student.exception.StudentNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final List<String> students = Arrays.asList("Nguyễn Văn A", "Trần Thị B", "Lê Văn C");

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable int id) {
        if (id < 0 || id >= students.size()) {
            throw new StudentNotFoundException("Sinh viên không tồn tại");
        }
        return ResponseEntity.ok(students.get(id));
    }
}
