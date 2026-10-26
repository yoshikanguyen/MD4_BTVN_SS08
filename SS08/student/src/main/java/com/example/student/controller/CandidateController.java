package com.example.student.controller;

import com.example.student.DTO.CandidateDTO;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> applyJob(
            @Valid @RequestPart("candidate") CandidateDTO candidate,
            @RequestPart("cv") MultipartFile cv) {

        // CV bắt buộc là PDF
        if (cv.getOriginalFilename() == null || !cv.getOriginalFilename().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Chỉ chấp nhận định dạng file PDF cho CV");
        }

        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            Path filePath = Paths.get(UPLOAD_DIR + cv.getOriginalFilename());
            Files.copy(cv.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Lỗi lưu file CV");
        }

        return ResponseEntity.ok("Ứng tuyển thành công! Đã lưu file: " + cv.getOriginalFilename());
    }
}
