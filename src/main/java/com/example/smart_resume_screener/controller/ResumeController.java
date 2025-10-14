package com.example.smart_resume_screener.controller;

import com.example.smart_resume_screener.model.ParsedResume;
import com.example.smart_resume_screener.model.ResumeDocument;
import com.example.smart_resume_screener.services.ResumeService;
import com.example.smart_resume_screener.services.ResumeParsingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

import java.util.Map;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeParsingService resumeParsingService;

    // ✅ Constructor-based dependency injection
    public ResumeController(ResumeService resumeService, ResumeParsingService resumeParsingService) {
        this.resumeService = resumeService;
        this.resumeParsingService = resumeParsingService;
    }

    // ✅ Upload endpoint
    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            ResumeDocument resume = resumeService.uploadResume(file);
            return ResponseEntity.ok(resume);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }

    // ✅ Analyze endpoint
    @GetMapping("/analyze/{id}")
    public ResponseEntity<?> analyzeResume(@PathVariable String id) {
        ResumeDocument resume = resumeService.getResumeById(id);
        if (resume == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resume not found");
        }

        ParsedResume parsedResume = resumeParsingService.parseResume(resume.getRawText());
        return ResponseEntity.ok(parsedResume);
    }
}
