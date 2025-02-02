package com.deepak.flashcard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    @Lob
    @Column(columnDefinition = "LONGTEXT") // Stores large HTML content
    private String answerHtml;

    public Question() {}

    public Question(String questionText, String answerHtml) {
        this.questionText = questionText;
        this.answerHtml = answerHtml;
    }

    public Long getId() { return id; }
    public String getQuestionText() { return questionText; }
    public String getAnswerHtml() { return answerHtml; }

    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public void setAnswerHtml(String answerHtml) { this.answerHtml = answerHtml; }
}
