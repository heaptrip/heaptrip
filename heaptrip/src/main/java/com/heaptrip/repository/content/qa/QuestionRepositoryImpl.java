package com.heaptrip.repository.content.qa;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.qa.Question;
import com.heaptrip.domain.repository.content.qa.QuestionRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.mongodb.WriteResult;
import org.apache.commons.lang.ArrayUtils;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionRepositoryImpl extends CrudRepositoryImpl<Question> implements QuestionRepository {

    private static final Logger logger = LoggerFactory.getLogger(QuestionRepositoryImpl.class);

    @Override
    protected Class<Question> getCollectionClass() {
        return Question.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.CONTENTS.getName();
    }

    @Override
    public void update(Question question) {
        String query = "{_id: #}";

        String updateQuery;
        List<Object> parameters = new ArrayList<>();

        updateQuery = "{$set: {categories: #, categoryIds: #, regions: #, regionIds: #, 'name.mainText': #, 'summary.mainText': #, 'description.mainText': #}}";

        parameters.add(question.getCategories());
        parameters.add(question.getCategoryIds());
        parameters.add(question.getRegions());
        parameters.add(question.getRegionIds());
        parameters.add(question.getName().getValue());
        parameters.add(question.getSummary().getValue());
        parameters.add(question.getDescription().getValue());

        if (logger.isDebugEnabled()) {
            String msg = String.format(
                    "update post\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s", query,
                    question.getId(), updateQuery, ArrayUtils.toString(parameters));
            logger.debug(msg);
        }

        MongoCollection coll = getCollection();
        WriteResult wr = coll.update(query, question.getId()).with(updateQuery, parameters.toArray());
        logger.debug("WriteResult for update post: {}", wr);
    }
}
