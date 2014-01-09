package com.heaptrip.service.content.qa;

import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.Views;
import com.heaptrip.domain.entity.content.qa.Question;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.repository.content.qa.QuestionRepository;
import com.heaptrip.domain.service.content.qa.QuestionService;
import com.heaptrip.service.content.ContentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class QuestionServiceImpl extends ContentServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question save(Question question) {
        Assert.notNull(question, "question must not be null");
        Assert.notNull(question.getOwner(), "owner must not be null");
        Assert.notNull(question.getOwner().getId(), "owner.id must not be null");
        Assert.notEmpty(question.getName(), "name must not be empty");
        Assert.notEmpty(question.getSummary(), "summary must not be empty");
        Assert.notEmpty(question.getDescription(), "description must not be empty");

        // TODO if owner account type == (CLUB or COMPANY) then set owners
        question.setOwners(new String[]{question.getOwner().getId()});

        // update categories and categoryIds
        updateCategories(question);

        // update regions and regionIds
        updateRegions(question);

        question.setStatus(new ContentStatus(ContentStatusEnum.DRAFT));
        question.setCreated(new Date());
        question.setDeleted(null);
        question.setRating(ContentRating.getDefaultValue());
        question.setComments(0L);

        Views views = new Views();
        views.setCount(0);
        question.setViews(views);

        // save to mongodb
        questionRepository.save(question);

        // save to solr
        contentSearchService.saveContent(question.getId());

        return question;
    }

    @Override
    public void remove(String contentId) {
        Assert.notNull(contentId, "contentId must not be null");
        super.remove(contentId);
        // remove from solr
        contentSearchService.removeContent(contentId);
    }

    @Override
    public void hardRemove(String contentId) {
        Assert.notNull(contentId, "contentId must not be null");
        super.hardRemove(contentId);
        // remove from solr
        contentSearchService.removeContent(contentId);
    }

    @Override
    public void update(Question question) {
        Assert.notNull(question, "question must not be null");
        Assert.notNull(question.getId(), "question.id must not be null");
        Assert.notEmpty(question.getName(), "name must not be empty");
        Assert.notEmpty(question.getSummary(), "summary must not be empty");
        Assert.notEmpty(question.getDescription(), "description must not be empty");

        // update categories and categoryIds
        updateCategories(question);

        // update regions and regionIds
        updateRegions(question);

        // update to db
        questionRepository.update(question);

        // save to solr
        contentSearchService.saveContent(question.getId());
    }
}
