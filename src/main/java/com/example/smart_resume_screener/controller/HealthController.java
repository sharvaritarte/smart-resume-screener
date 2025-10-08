package com.example.smart_resume_screener.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/ping")
    public String ping() {
        return "Smart Resume Screener Backend is Up and Running!";
    }
}
