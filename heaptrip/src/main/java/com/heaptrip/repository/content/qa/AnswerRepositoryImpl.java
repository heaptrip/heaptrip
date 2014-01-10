package com.heaptrip.repository.content.qa;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.qa.Answer;
import com.heaptrip.domain.repository.content.qa.AnswerRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerRepositoryImpl extends CrudRepositoryImpl<Answer> implements AnswerRepository {

    private static final Logger logger = LoggerFactory.getLogger(AnswerRepositoryImpl.class);

    @Override
    protected Class<Answer> getCollectionClass() {
        return Answer.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.COMMENTS.getName();
    }

    @Override
    public List<Answer> findByTargetIdOrderByFullSlugAsc(String questionId) {
        MongoCollection coll = getCollection();
        String hint = "{target: 1, fullSlug: 1}";
        Iterable<Answer> iter = coll.find("{target: #}", questionId).hint(hint).sort("{fullSlug: 1}").as(Answer.class);
        return IteratorConverter.copyIterator(iter.iterator());
    }

    @Override
    public void setCorrect(String id, boolean correct) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update("{_id: #}", id).with("{$set: {correct: #}}", correct);
        logger.debug("WriteResult for inc views: {}", wr);
    }

    @Override
    public void incLikes(String id) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update("{_id: #}", id).with("{$inc: {likes: #}}", 1);
        logger.debug("WriteResult for inc likes: {}", wr);
    }

    @Override
    public void incDislikes(String id) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update("{_id: #}", id).with("{$inc: {dislikes: #}}", 1);
        logger.debug("WriteResult for inc dislikes: {}", wr);
    }
}
