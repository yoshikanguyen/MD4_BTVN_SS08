package com.example.student.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/math")
public class MathController {

    @GetMapping("/divide")
    public ResponseEntity<Double> divide(@RequestParam double a, @RequestParam double b) {
        if (b == 0) {
            throw new ArithmeticException("Không thể chia cho 0");
        }
        return ResponseEntity.ok(a / b);
    }
}
