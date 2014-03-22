package com.heaptrip.domain.service.content.qa;

import com.heaptrip.domain.entity.content.qa.Answer;

import java.util.List;

/**
 * Answer service
 */
public interface AnswerService {

    /**
     * Add root answer
     *
     * @param questionId question ID
     * @param userId     id of the comment author
     * @param text       comment text
     * @return answer
     */
    public Answer addAnswer(String questionId, String userId, String text);

    /**
     * Add child answer
     *
     * @param questionId     question ID
     * @param parentAnswerId id of the parent answer
     * @param userId         id of the comment author
     * @param text           comment text
     * @return comment
     */
    public Answer addChildAnswer(String questionId, String parentAnswerId, String userId, String text);

    /**
     * Set sign that answer is corrected
     *
     * @param answerId answer ID
     * @param correct  true or false
     */
    public void setCorrect(String answerId, boolean correct);

    /**
     * Increment number of likes for answer
     *
     * @param answerId answer ID
     */
    public void incLikes(String answerId);

    /**
     * Increment number of dislikes for answer
     *
     * @param answerId answer ID
     */
    public void incDislikes(String answerId);

    /**
     * Get answers list by question id
     *
     * @param questionId question ID
     * @return comments list
     */
    public List<Answer> getAnswers(String questionId);

    /**
     * Remove answers by question ID
     *
     * @param questionId question ID
     */
    public void removeAnswers(String questionId);

}
