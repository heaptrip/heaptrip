package com.heaptrip.domain.service.content.qa;

import com.heaptrip.domain.entity.content.qa.Answer;

public interface AnswerService {

    public Answer addAnswer(String questionId, String userId, String text);

    public Answer addComment(String answerId, String userId, String text);

    public void setCorrect(String answerId, boolean correct);

    public void incLikes(String answerId, byte value);

}
