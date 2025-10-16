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
    private final Tika tika = new Tika();

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public ResumeDocument uploadResume(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("Uploaded file is empty. Please select a valid resume file.");
            }

            String parsedText;
            try (InputStream inputStream = file.getInputStream()) {
                parsedText = tika.parseToString(inputStream);
            }

            ResumeDocument doc = new ResumeDocument();
            doc.setFileName(file.getOriginalFilename());
            doc.setRawText(parsedText);
            doc.setUploadedAt(LocalDateTime.now());

            return resumeRepository.save(doc);

        } catch (IOException e) {
            throw new RuntimeException("Error while reading resume: " + e.getMessage(), e);
        } catch (TikaException e) {
            throw new RuntimeException("Error while parsing resume: " + e.getMessage(), e);
        }
    }

    public ResumeDocument getResumeById(String id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException(id));
    }
}
