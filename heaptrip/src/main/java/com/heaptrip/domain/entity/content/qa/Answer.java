package com.heaptrip.domain.entity.content.qa;

import com.heaptrip.domain.entity.comment.Comment;

/**
 * Answer
 */
public class Answer extends Comment {

    // is correct answer
    private Boolean correct;

    // number of likes
    private Long likes;

    // number of dislikes
    private Long dislikes;

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getDislikes() {
        return dislikes;
    }

    public void setDislikes(Long dislikes) {
        this.dislikes = dislikes;
    }
}
