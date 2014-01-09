package com.heaptrip.service.content.qa;

import com.heaptrip.domain.entity.comment.Comment;
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

import java.io.IOException;
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
        // check number of comments
        Question question = questionRepository.findOne(QUESTION_ID);
        Assert.assertNotNull(question);
        Assert.assertEquals(question.getComments(), 1L);
    }

    @Test(enabled = true, priority = 1)
    public void addComment() {
        // call
        Answer comment = answerService.addComment(QUESTION_ID, answer.getId(), USER_ID, "some comment");
        // check
        Assert.assertNotNull(comment);
        Assert.assertNotNull(comment.getId());
        Assert.assertNotNull(comment.getParent());
        Assert.assertEquals(comment.getParent(), answer.getId());
        Assert.assertEquals(comment.getTarget(), QUESTION_ID);
        Assert.assertEquals(comment.getAuthor().getId(), USER_ID);
        // check number of comments
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
        Assert.assertNull(answer.getCorrect());
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
        Assert.assertNull(answer.getLikes());
        Assert.assertTrue(answer.getLikes().equals(3L));
        Assert.assertNull(answer.getDislikes());
        Assert.assertTrue(answer.getDislikes().equals(2L));
    }

    @Test(enabled = true, priority = 3)
    public void getAnswers() throws IOException {
        // call
        List<Answer> answers = answerService.getAnswers(QUESTION_ID);
        // check
        Assert.assertNotNull(answers);
        Assert.assertEquals(answers.size(), 2);
        // check first parent/child comments
        Comment answer = answers.get(0);
        Comment comments = answers.get(1);
        Assert.assertNull(answer.getParent());
        Assert.assertNotNull(comments.getParent());
        Assert.assertEquals(comments.getParent(), answer.getId());
    }
}
