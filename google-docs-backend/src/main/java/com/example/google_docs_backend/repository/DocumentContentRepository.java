package com.example.google_docs_backend.repository;

import com.example.google_docs_backend.entity.DocumentContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentContentRepository extends MongoRepository<DocumentContent,String> {
}
