package com.heaptrip.service.content.qa;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.qa.Question;
import com.heaptrip.domain.repository.content.qa.QuestionRepository;
import com.heaptrip.domain.service.content.qa.QuestionService;
import com.heaptrip.util.language.LanguageUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Locale;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class QuestionServiceTest extends AbstractTestNGSpringContextTests {

    private static final String QUESTION_ID = "QUESTION_FOR_QUESTION_SERVICE_TEST";

    private static final String OWNER_ID = "OWNER_FOR_QUESTION_SERVICE_TEST";

    private static final Locale locale = LanguageUtils.getEnglishLocale();

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    private Question question;

    @BeforeClass
    public void beforeTest() {
        question = new Question();
        question.setId(QUESTION_ID);
        question.setName(new MultiLangText("Test question"));
        question.setSummary(new MultiLangText("Summary for test question"));
        question.setDescription(new MultiLangText("Description for test question"));
        question.setOwnerId(OWNER_ID);
    }

    @AfterClass(alwaysRun = true)
    public void afterTest() {
        questionService.hardRemove(QUESTION_ID);
    }

    @Test(priority = 0, enabled = true)
    public void save() {
        // call
        questionService.save(question);
        // check
        Question question = questionRepository.findOne(QUESTION_ID);
        Assert.assertNotNull(question);
        Assert.assertEquals(question, this.question);
        Assert.assertNotNull(question.getCreated());
        Assert.assertNull(question.getDeleted());
        Assert.assertTrue(ArrayUtils.isEmpty(question.getAllowed()));
        Assert.assertNull(question.getMainLang());
        Assert.assertTrue(ArrayUtils.isEmpty(question.getLangs()));
        Assert.assertEquals(question.getOwnerId(), OWNER_ID);
        Assert.assertNotNull(question.getStatus());
        Assert.assertNotNull(question.getStatus().getValue());
        Assert.assertEquals(question.getStatus().getValue(), ContentStatusEnum.DRAFT);
        Assert.assertNotNull(question.getRating());
        Assert.assertEquals(question.getRating().getCount(), 0);
        Assert.assertNotNull(question.getViews());
        Assert.assertEquals(question.getViews().getCount(), 0);
    }

    @Test(priority = 1, enabled = true)
    public void update() {
        // call
        Question question = questionRepository.findOne(QUESTION_ID);
        Assert.assertNotNull(question);
        String name = "New name for test question";
        String summary = "New summary for test question";
        String description = "New description for test question";
        question.getName().setValue(name);
        question.getSummary().setValue(summary);
        question.getDescription().setValue(description);
        questionService.update(question);
        // check
        question = questionRepository.findOne(QUESTION_ID);
        Assert.assertNotNull(question);
        Assert.assertNotNull(question.getName());
        Assert.assertNotNull(question.getName().getValue(locale));
        Assert.assertEquals(question.getName().getValue(locale), name);
        Assert.assertNotNull(question.getSummary());
        Assert.assertNotNull(question.getSummary().getValue(locale));
        Assert.assertEquals(question.getSummary().getValue(locale), summary);
        Assert.assertNotNull(question.getDescription());
        Assert.assertNotNull(question.getDescription().getValue(locale));
        Assert.assertEquals(question.getDescription().getValue(locale), description);
    }
}
