package com.deepak.flashcard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;


    @Lob
    @Column(columnDefinition = "LONGTEXT") // Stores large HTML content
    private String answerHtml;


    public Question(String questionText, Topic topic, String answerHtml) {
        this.questionText = questionText;
        this.topic = topic;
        this.answerHtml = answerHtml;
    }
}
