package com.example.smart_resume_screener.repository;

import com.example.smart_resume_screener.model.ResumeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResumeRepository extends MongoRepository<ResumeDocument, String> { }
