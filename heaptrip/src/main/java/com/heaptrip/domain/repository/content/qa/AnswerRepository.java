package com.heaptrip.domain.repository.content.qa;

import com.heaptrip.domain.entity.content.qa.Answer;
import com.heaptrip.domain.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer> {

    public List<Answer> findByTargetIdOrderByFullSlugAsc(String questionId);

    public void setCorrect(String id, boolean correct);

    public void incLikes(String id);

    public void incDislikes(String id);
}
