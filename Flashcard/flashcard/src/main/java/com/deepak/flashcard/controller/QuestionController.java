package com.deepak.flashcard.controller;


import com.deepak.flashcard.dto.QuestionDTO;
import com.deepak.flashcard.model.Question;
import com.deepak.flashcard.model.Topic;
import com.deepak.flashcard.repository.QuestionRepository;
import com.deepak.flashcard.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend access
public class QuestionController {

    private final QuestionRepository questionRepository;

    private final TopicRepository topicRepository;

    public QuestionController(QuestionRepository questionRepository,TopicRepository topicRepository) {
        this.questionRepository = questionRepository;
        this.topicRepository = topicRepository;
    }

    @PostMapping
    @RequestMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionDTO question) {
        Topic topic = topicRepository.findById(question.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        Question q = new Question(question.getQuestionText(), topic,question.getAnswerHtml());

        return ResponseEntity.ok(questionRepository.save(q));
    }

    @GetMapping("/all")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/random")
    public ResponseEntity<Question> getRandomQuestion(@RequestParam("topicId") Long topicId) {
        return questionRepository.findRandomQuestionByTopic(Long.valueOf(topicId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
