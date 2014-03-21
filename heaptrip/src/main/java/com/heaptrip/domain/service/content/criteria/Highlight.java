package com.heaptrip.domain.service.content.criteria;

/**
 * Specifies the text that should appear before and after a highlighted term.
 * The default values are "<em>" and "</em>".
 */
public class Highlight {

    private String pre;

    private String post;

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

}
