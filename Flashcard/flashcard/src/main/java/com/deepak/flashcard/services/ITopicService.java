package com.deepak.flashcard.services;

import com.deepak.flashcard.model.Topic;
import com.deepak.flashcard.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ITopicService {

    public Topic saveTopic(Topic topic);
    public List<Topic> getAllTopics();

}
