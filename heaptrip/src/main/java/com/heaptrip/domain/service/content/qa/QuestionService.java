package com.heaptrip.domain.service.content.qa;

import com.heaptrip.domain.entity.content.qa.Question;
import com.heaptrip.domain.service.content.ContentService;

/**
 * Question service
 */
public interface QuestionService extends ContentService {

    /**
     * Save a new question
     *
     * @param question
     * @return question
     */
    public Question save(Question question);

    /**
     * Update question information
     *
     * @param question
     */
    public void update(Question question);

}
