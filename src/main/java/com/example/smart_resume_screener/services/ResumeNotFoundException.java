package com.example.smart_resume_screener.services;

public class ResumeNotFoundException extends RuntimeException {
    public ResumeNotFoundException(String id) {
        super("Resume not found with id: " + id);
    }
}
