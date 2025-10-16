package com.example.smart_resume_screener.services;

import com.example.smart_resume_screener.model.ParsedResume;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeParsingService {

    // Predefined skill list (extend as needed)
    private static final List<String> SKILLS = Arrays.asList(
            "Java", "Spring Boot", "Hibernate", "SQL", "Python", "JavaScript", "React", "AWS"
    );

    // Patterns for email and phone
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\b[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}\\b");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\b\\d{10}\\b");

    // Pattern for education keywords
    private static final List<String> EDUCATION_KEYWORDS = Arrays.asList(
            "Bachelor", "Master", "B.Tech", "M.Tech", "B.E", "M.E", "MBA", "PhD"
    );

    // Pattern for experience (years)
    private static final Pattern EXPERIENCE_PATTERN = Pattern.compile("(\\d+)\\s*(years|yrs)\\s*(of experience)?", Pattern.CASE_INSENSITIVE);

    public ParsedResume parseResume(String resumeText) {
        ParsedResume parsed = new ParsedResume();

        // Extract Email
        Matcher emailMatcher = EMAIL_PATTERN.matcher(resumeText);
        if (emailMatcher.find()) {
            parsed.setEmail(emailMatcher.group());
        }

        // Extract Phone
        Matcher phoneMatcher = PHONE_PATTERN.matcher(resumeText);
        if (phoneMatcher.find()) {
            parsed.setPhone(phoneMatcher.group());
        }

        // Extract Skills
        List<String> foundSkills = new ArrayList<>();
        String lowerText = resumeText.toLowerCase();
        for (String skill : SKILLS) {
            if (lowerText.contains(skill.toLowerCase())) {
                foundSkills.add(skill);
            }
        }
        parsed.setSkills(foundSkills);

        // Extract Education
        List<String> educationList = new ArrayList<>();
        for (String edu : EDUCATION_KEYWORDS) {
            if (lowerText.contains(edu.toLowerCase())) {
                educationList.add(edu);
            }
        }
        parsed.setEducation(educationList);

        // Extract Experience
        Matcher expMatcher = EXPERIENCE_PATTERN.matcher(resumeText);
        if (expMatcher.find()) {
            parsed.setExperience(Integer.parseInt(expMatcher.group(1)));
        }

        // Extract Name (simple heuristic: first line with capital letters)
        String[] lines = resumeText.split("\\r?\\n");
        for (String line : lines) {
            if (line.trim().matches("^[A-Z][a-z]+(\\s[A-Z][a-z]+)*$")) {
                parsed.setName(line.trim());
                break;
            }
        }

        return parsed;
    }
}
