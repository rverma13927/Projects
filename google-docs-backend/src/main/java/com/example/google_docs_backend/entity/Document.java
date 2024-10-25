package com.example.google_docs_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Document {
    @Id
    private UUID id;
    private String title;
    private String owner;

    public Document() {
        this.id = UUID.randomUUID();
    }

    // Getters and setters
}
