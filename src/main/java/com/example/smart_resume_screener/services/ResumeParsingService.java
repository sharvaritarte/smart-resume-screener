package com.example.smart_resume_screener.services;

import com.example.smart_resume_screener.model.ParsedResume;
import com.example.smart_resume_screener.repository.ParsedResumeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeParsingService {

    private final ParsedResumeRepository parsedResumeRepository;

    public ResumeParsingService(ParsedResumeRepository parsedResumeRepository) {
        this.parsedResumeRepository = parsedResumeRepository;
    }

    public ParsedResume parseResume(String text) {
        ParsedResume parsed = new ParsedResume();

        // Extract name (assume first line)
        String[] lines = text.split("\n");
        if (lines.length > 0) parsed.setName(lines[0].trim());

        // Extract email
        Matcher emailMatcher = Pattern.compile("[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}").matcher(text);
        if (emailMatcher.find()) parsed.setEmail(emailMatcher.group());

        // Extract phone
        Matcher phoneMatcher = Pattern.compile("(\\+91\\s?)?[6-9]\\d{9}").matcher(text);
        if (phoneMatcher.find()) parsed.setPhone(phoneMatcher.group());

        // Extract skills
        List<String> skills = Arrays.asList("Java", "Python", "Spring", "Spring Boot", "Hibernate", "SQL", "HTML", "CSS", "JavaScript", "React");
        List<String> found = new ArrayList<>();
        for (String skill : skills) {
            if (text.toLowerCase().contains(skill.toLowerCase())) found.add(skill);
        }
        parsed.setSkills(found);
        parsed.setParsedAt(LocalDateTime.now());

        // Save parsed data in MongoDB
        return parsedResumeRepository.save(parsed);
    }
}
