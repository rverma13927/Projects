package com.deepak.flashcard.dto;

import com.deepak.flashcard.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private String questionText;
    private Long topicId;
    private String answerHtml;

}
