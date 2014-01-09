package com.heaptrip.repository.content.qa;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.qa.Question;
import com.heaptrip.domain.repository.content.qa.QuestionRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class QuestionRepositoryImpl extends CrudRepositoryImpl<Question> implements QuestionRepository {

    @Override
    protected Class<Question> getCollectionClass() {
        return Question.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.CONTENTS.getName();
    }
}
