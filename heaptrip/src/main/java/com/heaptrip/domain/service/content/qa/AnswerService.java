package com.heaptrip.domain.service.content.qa;

import com.heaptrip.domain.entity.content.qa.Answer;

import java.util.List;

public interface AnswerService {

    public Answer addAnswer(String questionId, String userId, String text);

    public Answer addChildAnswer(String questionId, String parentAnswerId, String userId, String text);

    public void setCorrect(String answerId, boolean correct);

    public void incLikes(String answerId);

    public void incDislikes(String answerId);

    public List<Answer> getAnswers(String questionId);

    public void removeAnswers(String questionId);

}
