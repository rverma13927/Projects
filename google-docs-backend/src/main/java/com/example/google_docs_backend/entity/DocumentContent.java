package com.example.google_docs_backend.entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "document_content")
@Getter
@Setter
public class DocumentContent {
    @Id
    private String documentId;
    private List<String> deltas;
}
