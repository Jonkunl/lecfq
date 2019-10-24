package com.unimelb.lecmore;

public class Answer {
    private String upvotes;
    private String downvotes;
    private String answer;
    private String answerer;
    private String id;

    public Answer(){

    }

    public Answer(String upvotes, String downvotes, String answer, String answerer) {
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.answer = answer;
        this.answerer = answerer;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(String upvotes) {
        this.upvotes = upvotes;
    }

    public String getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(String downvotes) {
        this.downvotes = downvotes;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerer() {
        return answerer;
    }

    public void setAnswerer(String answerer) {
        this.answerer = answerer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
