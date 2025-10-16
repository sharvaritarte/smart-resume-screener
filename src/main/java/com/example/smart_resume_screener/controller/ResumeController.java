package com.example.smart_resume_screener.controller;

import com.example.smart_resume_screener.model.ParsedResume;
import com.example.smart_resume_screener.model.ResumeDocument;
import com.example.smart_resume_screener.services.ResumeAnalysisService;
import com.example.smart_resume_screener.services.ResumeParsingService;
import com.example.smart_resume_screener.services.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeParsingService resumeParsingService;
    private final ResumeAnalysisService resumeAnalysisService;

    public ResumeController(ResumeService resumeService,
                            ResumeParsingService resumeParsingService,
                            ResumeAnalysisService resumeAnalysisService) {
        this.resumeService = resumeService;
        this.resumeParsingService = resumeParsingService;
        this.resumeAnalysisService = resumeAnalysisService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            ResumeDocument resume = resumeService.uploadResume(file);
            return ResponseEntity.ok(resume);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading resume: " + e.getMessage());
        }
    }

    @GetMapping("/analyze/{id}")
    public ResponseEntity<?> analyzeResume(@PathVariable String id) {
        ResumeDocument resume = resumeService.getResumeById(id);

        try {
            ParsedResume parsedResume = resumeParsingService.parseResume(resume.getRawText());
            return ResponseEntity.ok(parsedResume);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error analyzing resume: " + e.getMessage());
        }
    }

    @GetMapping("/score/{id}")
    public ResponseEntity<?> scoreResume(@PathVariable String id, @RequestParam String role) {
        ResumeDocument resume = resumeService.getResumeById(id);
        if (resume == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resume not found");
        }

        ParsedResume parsedResume = resumeParsingService.parseResume(resume.getRawText());
        Map<String, Object> analysis = resumeAnalysisService.scoreResume(parsedResume, role);

        return ResponseEntity.ok(analysis);
    }

    @GetMapping("/score-multi/{id}")
    public ResponseEntity<?> scoreResumeMultipleRoles(
            @PathVariable String id,
            @RequestParam String roles // roles separated by comma
    ) {
        ResumeDocument resume = resumeService.getResumeById(id);
        if (resume == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resume not found");
        }

        ParsedResume parsedResume = resumeParsingService.parseResume(resume.getRawText());

        String[] roleList = roles.split(",");
        Map<String, Object> result = new HashMap<>();

        for (String role : roleList) {
            role = role.trim();
            Map<String, Object> analysis = resumeAnalysisService.scoreResume(parsedResume, role);
            result.put(role, analysis);
        }

        return ResponseEntity.ok(result);
    }


}
