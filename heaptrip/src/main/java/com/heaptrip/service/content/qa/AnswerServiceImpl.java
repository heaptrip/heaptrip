package com.heaptrip.service.content.qa;

import com.heaptrip.domain.entity.content.qa.Answer;
import com.heaptrip.domain.service.content.qa.AnswerService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AnswerServiceImpl implements AnswerService {

    @Override
    public Answer addAnswer(String questionId, String userId, String text) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public Answer addChildAnswer(String questionId, String parentAnswerId, String userId, String text) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setCorrect(String answerId, boolean correct) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void incLikes(String answerId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void incDislikes(String answerId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Answer> getAnswers(String questionId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeAnswers(String questionId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
