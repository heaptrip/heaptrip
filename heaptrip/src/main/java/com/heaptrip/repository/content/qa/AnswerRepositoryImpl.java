package com.heaptrip.repository.content.qa;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.qa.Answer;
import com.heaptrip.domain.repository.content.qa.AnswerRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class AnswerRepositoryImpl extends CrudRepositoryImpl<Answer> implements AnswerRepository {

    @Override
    protected Class<Answer> getCollectionClass() {
        return Answer.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.COMMENTS.getName();
    }
}
