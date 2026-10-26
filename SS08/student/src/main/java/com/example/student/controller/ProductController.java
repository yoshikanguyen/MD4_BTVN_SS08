package com.example.student.controller;

import com.example.student.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final String UPLOAD_DIR = "uploads/";

    @PostMapping
    public ResponseEntity<?> createProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        // Kiểm tra bắt buộc có ảnh sản phẩm
        if (image == null || image.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse(400, "Ảnh sản phẩm là bắt buộc"));
        }

        // Đổi tên ảnh thành chuỗi ngẫu nhiên
        String originalFilename = image.getOriginalFilename();
        String ext = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String newFileName = "image_" + UUID.randomUUID().toString().substring(0, 8) + ext;

        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            Path filePath = Paths.get(UPLOAD_DIR + newFileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Lỗi hệ thống khi lưu file");
        }

        Map<String, Object> productInfo = new HashMap<>();
        productInfo.put("name", name);
        productInfo.put("price", price);
        productInfo.put("imagePath", newFileName);

        return ResponseEntity.ok(productInfo);
    }
}
