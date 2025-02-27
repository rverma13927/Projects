package com.deepak.flashcard.repository;

import com.deepak.flashcard.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTopicId(Long topicId);

    @Query("SELECT q FROM Question q WHERE q.topic.id = :topicId ORDER BY FUNCTION('RAND') LIMIT 1")
    Optional<Question> findRandomQuestionByTopic(@Param("topicId") Long topicId);

}
