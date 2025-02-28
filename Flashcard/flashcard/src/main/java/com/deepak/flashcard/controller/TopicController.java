package com.deepak.flashcard.controller;

import com.deepak.flashcard.model.Topic;
import com.deepak.flashcard.services.serviceImpl.TopicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "http://localhost:3000")
public class TopicController {
    @Autowired
    private TopicServiceImpl topicServiceImpl;

    @PostMapping
    public Topic addTopic(@RequestBody Topic topic) {
        return topicServiceImpl.saveTopic(topic);
    }

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicServiceImpl.getAllTopics();
    }
}
