package com.heaptrip.service.content.qa;

import com.heaptrip.domain.entity.comment.Comment;
import com.heaptrip.domain.entity.content.qa.Answer;
import com.heaptrip.domain.entity.content.qa.Question;
import com.heaptrip.domain.repository.content.qa.AnswerRepository;
import com.heaptrip.domain.service.comment.CommentService;
import com.heaptrip.domain.service.content.qa.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Answer addAnswer(String questionId, String userId, String text) {
        return commentToAnswer(commentService.addComment(Question.class, questionId, userId, text));
    }

    @Override
    public Answer addChildAnswer(String questionId, String parentAnswerId, String userId, String text) {
        return commentToAnswer(commentService.addChildComment(Question.class, questionId, parentAnswerId, userId, text));
    }

    @Override
    public void setCorrect(String answerId, boolean correct) {
        Assert.notNull(answerId, "answerId must not be null");
        answerRepository.setCorrect(answerId, correct);
    }

    @Override
    public void incLikes(String answerId) {
        Assert.notNull(answerId, "answerId must not be null");
        answerRepository.incLikes(answerId);
    }

    @Override
    public void incDislikes(String answerId) {
        Assert.notNull(answerId, "answerId must not be null");
        answerRepository.incDislikes(answerId);
    }

    @Override
    public List<Answer> getAnswers(String questionId) {
        Assert.notNull(questionId, "questionId must not be null");
        return answerRepository.findByTargetIdOrderByFullSlugAsc(questionId);
    }

    @Override
    public void removeAnswers(String questionId) {
        commentService.removeComments(questionId);
    }

    private Answer commentToAnswer(Comment comment) {
        Answer answer = new Answer();
        answer.setId(comment.getId());
        answer.setTarget(comment.getTarget());
        answer.setParent(comment.getParent());
        answer.setSlug(comment.getSlug());
        answer.setFullSlug(comment.getFullSlug());
        answer.setCreated(comment.getCreated());
        answer.setText(comment.getText());
        answer.setAuthorId(comment.getAuthorId());
        return answer;
    }
}
