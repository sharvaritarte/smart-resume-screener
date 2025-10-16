package com.example.smart_resume_screener.services;

import com.example.smart_resume_screener.model.ParsedResume;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResumeAnalysisService {

    // Role to skill mapping with points
    private static final Map<String, Map<String, Integer>> ROLE_SKILL_POINTS = new HashMap<>();

    static {
        // Example: Java Developer
        Map<String, Integer> javaDevSkills = new HashMap<>();
        javaDevSkills.put("Java", 30);
        javaDevSkills.put("Spring Boot", 25);
        javaDevSkills.put("Hibernate", 20);
        javaDevSkills.put("SQL", 15);
        ROLE_SKILL_POINTS.put("Java Developer", javaDevSkills);

        // Example: Python Developer
        Map<String, Integer> pythonDevSkills = new HashMap<>();
        pythonDevSkills.put("Python", 30);
        pythonDevSkills.put("Django", 25);
        pythonDevSkills.put("Flask", 20);
        pythonDevSkills.put("SQL", 15);
        ROLE_SKILL_POINTS.put("Python Developer", pythonDevSkills);
    }

    public Map<String, Object> scoreResume(ParsedResume parsedResume, String role) {
        Map<String, Object> result = new HashMap<>();
        int score = 0;

        // Fetch relevant skills for the role
        Map<String, Integer> roleSkills = ROLE_SKILL_POINTS.getOrDefault(role, new HashMap<>());
        List<String> candidateSkills = parsedResume.getSkills();

        Map<String, String> skillFeedback = new HashMap<>();
        for (String skill : roleSkills.keySet()) {
            if (candidateSkills.contains(skill)) {
                score += roleSkills.get(skill);
                skillFeedback.put(skill, "✔ Found (+ " + roleSkills.get(skill) + " pts)");
            } else {
                skillFeedback.put(skill, "❌ Not Found (+0 pts)");
            }
        }

        // Add experience bonus
        int exp = parsedResume.getExperience();
        int expPoints = 0;
        if (exp >= 5) expPoints = 20;
        else if (exp >= 2) expPoints = 10;
        else if (exp > 0) expPoints = 5;

        score += expPoints;

        // Build feedback summary
        String feedbackSummary = "Skills:\n";
        for (Map.Entry<String, String> entry : skillFeedback.entrySet()) {
            feedbackSummary += "- " + entry.getKey() + ": " + entry.getValue() + "\n";
        }
        feedbackSummary += "Experience: " + exp + " years (+ " + expPoints + " pts)\n";
        feedbackSummary += "Total Score: " + score + "/100\n";
        feedbackSummary += score >= 70 ? "Overall Feedback: Good match!" : "Overall Feedback: Needs improvement";

        // Populate result
        result.put("role", role);
        result.put("score", score);
        result.put("feedback", feedbackSummary);

        return result;
    }
}
