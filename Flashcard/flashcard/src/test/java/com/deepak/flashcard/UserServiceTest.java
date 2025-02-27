package com.deepak.flashcard;


import com.deepak.flashcard.model.Topic;
import com.deepak.flashcard.repository.TopicRepository;
import com.deepak.flashcard.services.ITopicService;
import com.deepak.flashcard.services.serviceImpl.TopicServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

/**  https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockito-junit-example/
 * https://github.com/hamvocke/spring-testing/blob/main/src/test/java/example/ExampleControllerTest.java
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock  //Creates a mock instance of a dependency
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicServiceImpl topicService;

    @Test
    void testGetAllTopic(){
        //given
        Topic topic1 = new Topic(1L,"Mysql");
        Topic topic2 = new Topic(2L,"Java");
        Topic topic3 = new Topic(3L,"Spring-boot");

        //when
        given(topicRepository.findAll())
                .willReturn(List.of(topic2,topic1,topic3));


        var  topicList = topicService.getAllTopics();

        assertEquals(3,topicList.size());

    }

}
