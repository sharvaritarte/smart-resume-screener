package com.example.smart_resume_screener.model;

import java.time.LocalDateTime;
import java.util.List;

public class ParsedResume {

    private String fileName;        // <-- add this
    private LocalDateTime parsedAt; // <-- add this
    private String name;
    private String email;
    private String phone;
    private List<String> skills;
    private List<String> education;
    private int experience; // in years

    // Getters and Setters
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public LocalDateTime getParsedAt() { return parsedAt; }
    public void setParsedAt(LocalDateTime parsedAt) { this.parsedAt = parsedAt; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public List<String> getEducation() { return education; }
    public void setEducation(List<String> education) { this.education = education; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
}
