package com.example.google_docs_backend.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DocumentController {
    @MessageMapping("/documents/{id}")
    @SendTo("/topic/documents/{id}")
    public String handleEdit(String delta) {
        // Ideally, apply OT or CRDT here before broadcasting
        System.out.println("testing");
        return delta;
    }
}
