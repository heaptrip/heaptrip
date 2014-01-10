package com.heaptrip.service.content.qa;

import com.heaptrip.domain.entity.content.qa.Answer;
import com.heaptrip.domain.entity.content.qa.Question;
import com.heaptrip.domain.repository.content.qa.AnswerRepository;
import com.heaptrip.domain.repository.content.qa.QuestionRepository;
import com.heaptrip.domain.service.content.qa.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class AnswerServiceTest extends AbstractTestNGSpringContextTests {

    private static final String QUESTION_ID = "QUESTION_FOR_ANSWER_SERVICE_TEST";

    private static final String USER_ID = "1";

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private Question question;

    private Answer answer;

    @BeforeClass
    public void beforeTest() {
        // save question
        question = new Question();
        question.setId(QUESTION_ID);
        questionRepository.save(question);
        // remove old test answers
        answerService.removeAnswers(QUESTION_ID);
    }

    @AfterClass
    public void afterTest() {
        // remove question
        if (question != null && question.getId() != null) {
            questionRepository.remove(question);
        }
        // remove test answers
        answerService.removeAnswers(QUESTION_ID);
    }


    @Test(enabled = true, priority = 0)
    public void addAnswer() {
        // call
        answer = answerService.addAnswer(QUESTION_ID, USER_ID, "some root answer");
        // check
        Assert.assertNotNull(answer);
        Assert.assertNotNull(answer.getId());
        Assert.assertNull(answer.getParent());
        Assert.assertEquals(answer.getTarget(), QUESTION_ID);
        Assert.assertEquals(answer.getAuthor().getId(), USER_ID);
        Assert.assertNull(answer.getCorrect());
        Assert.assertNull(answer.getLikes());
        Assert.assertNull(answer.getDislikes());
        // check number of answers
        Question question = questionRepository.findOne(QUESTION_ID);
        Assert.assertNotNull(question);
        Assert.assertEquals(question.getComments(), 1L);
    }

    @Test(enabled = true, priority = 1)
    public void addChildAnswer() {
        // call
        Answer childAnswer = answerService.addChildAnswer(QUESTION_ID, answer.getId(), USER_ID, "some child answers");
        // check
        Assert.assertNotNull(childAnswer);
        Assert.assertNotNull(childAnswer.getId());
        Assert.assertNotNull(childAnswer.getParent());
        Assert.assertEquals(childAnswer.getParent(), answer.getId());
        Assert.assertEquals(childAnswer.getTarget(), QUESTION_ID);
        Assert.assertEquals(childAnswer.getAuthor().getId(), USER_ID);
        Assert.assertNull(childAnswer.getCorrect());
        Assert.assertNull(childAnswer.getLikes());
        Assert.assertNull(childAnswer.getDislikes());
        // check number of answers
        Question question = questionRepository.findOne(QUESTION_ID);
        Assert.assertNotNull(question);
        Assert.assertEquals(question.getComments(), 2L);
    }

    @Test(enabled = true, priority = 2)
    public void setCorrect() {
        // call
        answerService.setCorrect(answer.getId(), true);
        // check
        Answer answer = answerRepository.findOne(this.answer.getId());
        Assert.assertNotNull(answer.getCorrect());
        Assert.assertTrue(answer.getCorrect());
    }

    @Test(enabled = true, priority = 3)
    public void incLikes() {
        // call
        answerService.incLikes(answer.getId());
        answerService.incLikes(answer.getId());
        answerService.incLikes(answer.getId());
        answerService.incDislikes(answer.getId());
        answerService.incDislikes(answer.getId());
        // check
        Answer answer = answerRepository.findOne(this.answer.getId());
        Assert.assertNotNull(answer.getLikes());
        Assert.assertTrue(answer.getLikes().equals(3L));
        Assert.assertNotNull(answer.getDislikes());
        Assert.assertTrue(answer.getDislikes().equals(2L));
    }

    @Test(enabled = true, priority = 4)
    public void getAnswers() {
        // call
        List<Answer> answers = answerService.getAnswers(QUESTION_ID);
        // check
        Assert.assertNotNull(answers);
        Assert.assertEquals(answers.size(), 2);
        // check first parent/child comments
        Answer rootAnswer = answers.get(0);
        Answer childAnswer = answers.get(1);
        Assert.assertNull(rootAnswer.getParent());
        Assert.assertNotNull(childAnswer.getParent());
        Assert.assertEquals(childAnswer.getParent(), rootAnswer.getId());
    }
}
