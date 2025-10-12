package com.example.smart_resume_screener.controller;

import com.example.smart_resume_screener.model.ResumeDocument;
import com.example.smart_resume_screener.services.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResumeDocument> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            ResumeDocument savedDoc = resumeService.uploadResume(file);
            return ResponseEntity.ok(savedDoc);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
