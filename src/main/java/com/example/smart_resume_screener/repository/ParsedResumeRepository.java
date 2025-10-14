package com.example.smart_resume_screener.repository;

import com.example.smart_resume_screener.model.ParsedResume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParsedResumeRepository extends MongoRepository<ParsedResume, String> {}
