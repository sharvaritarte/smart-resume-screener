package com.example.smart_resume_screener.services;

import com.example.smart_resume_screener.model.ResumeDocument;
import com.example.smart_resume_screener.repository.ResumeRepository;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final Tika tika = new Tika(); // Apache Tika for text extraction

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public ResumeDocument uploadResume(MultipartFile file) {
        try {
            // ✅ Step 1: Validate file before parsing
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Uploaded file is empty. Please select a valid resume file.");
            }

            // ✅ Step 2: Extract text safely using try-with-resources
            String parsedText;
            try (InputStream inputStream = file.getInputStream()) {
                parsedText = tika.parseToString(inputStream);
            }

            // ✅ Step 3: Build ResumeDocument
            ResumeDocument doc = new ResumeDocument();
            doc.setFileName(file.getOriginalFilename());
            doc.setRawText(parsedText);
            doc.setUploadedAt(LocalDateTime.now());

            // ✅ Step 4: Save to MongoDB
            return resumeRepository.save(doc);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while reading or parsing resume: " + e.getMessage());
        } catch (TikaException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while extracting text from resume: " + e.getMessage());
        }
    }
}
