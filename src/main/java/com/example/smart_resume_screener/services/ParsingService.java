package com.example.smart_resume_screener.services;

import com.example.smart_resume_screener.model.ParsedResume;
import com.example.smart_resume_screener.repository.ParsedResumeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ParsingService {

    private final ParsedResumeRepository parsedResumeRepository;

    private static final List<String> KNOWN_SKILLS = Arrays.asList(
            "Java", "Spring Boot", "Hibernate", "SQL", "Python", "C++", "HTML", "CSS", "JavaScript", "React", "Node.js"
    );

    public ParsingService(ParsedResumeRepository parsedResumeRepository) {
        this.parsedResumeRepository = parsedResumeRepository;
    }

    public ParsedResume parseText(String fileName, String text) {
        ParsedResume parsed = new ParsedResume();
        parsed.setFileName(fileName);

        parsed.setName(extractName(text));
        parsed.setEmail(extractEmail(text));
        parsed.setPhone(extractPhone(text));
        parsed.setSkills(extractSkills(text));
        parsed.setExperience(extractExperience(text));
        parsed.setParsedAt(LocalDateTime.now());

        return parsedResumeRepository.save(parsed);
    }

    private String extractEmail(String text) {
        Matcher m = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}").matcher(text);
        return m.find() ? m.group() : "Not found";
    }

    private String extractPhone(String text) {
        Matcher m = Pattern.compile("\\b\\d{10}\\b").matcher(text);
        return m.find() ? m.group() : "Not found";
    }

    private String extractName(String text) {
        String[] lines = text.split("\\r?\\n");
        return lines.length > 0 ? lines[0].trim() : "Unknown";
    }

    private List<String> extractSkills(String text) {
        List<String> found = new ArrayList<>();
        for (String skill : KNOWN_SKILLS) {
            if (text.toLowerCase().contains(skill.toLowerCase())) {
                found.add(skill);
            }
        }
        return found;
    }
    private int extractExperience(String text) {
        // Regex to match patterns like "3 years", "3+ yrs", "2 yrs experience", etc.
        Pattern expPattern = Pattern.compile(
                "(\\d+)\\s*(\\+)?\\s*(years|yrs|year|yr)\\s*(of experience|experience|exp)?",
                Pattern.CASE_INSENSITIVE
        );

        Matcher matcher = expPattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return 0; // default if not found
    }

}
