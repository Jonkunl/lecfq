package com.unimelb.lecmore;


public class Answer {
    private String upvotes;
    private String downvotes;
    private String answer;
    private String answerer;
    private String id;

    public Answer(){

    }

    /**
     * The data structure for storing the number of upvotes, downvotes, content and the author
     * of the answer
     * @param upvotes String for storing the number fo upvotes of the answer
     * @param downvotes String for storing the downvotes of the asnwer
     * @param answer String for storing the content of the answer
     * @param answerer String for storing the author of the answer
     */
    public Answer(String upvotes, String downvotes, String answer, String answerer) {
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.answer = answer;
        this.answerer = answerer;
    }

    /**
     * Getter method of upvote
     * @return a String for storing upvote of the answer
     */
    public String getUpvotes() {
        return upvotes;
    }

    /**
     * setter of upvote
     * @param upvotes String for storing the upvote to be changed to
     */
    public void setUpvotes(String upvotes) {
        this.upvotes = upvotes;
    }

    /**
     * Getter method of downvote
     * @return a String for storing downvote of the answer
     */
    public String getDownvotes() {
        return downvotes;
    }
    /**
     * setter of downvote
     * @param downvotes String for storing the downvote to be changed to
     */
    public void setDownvotes(String downvotes) {
        this.downvotes = downvotes;
    }
    /**
     * Getter method of answer
     * @return a String for storing answer of the answer
     */
    public String getAnswer() {
        return answer;
    }
    /**
     * setter of answer
     * @param answer String for storing the answer to be changed to
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    /**
     * Getter method of answer
     * @return a String for storing anser of the answer
     */
    public String getAnswerer() {
        return answerer;
    }
    /**
     * setter of answer
     * @param answerer String for storing the answerer to be changed to
     */
    public void setAnswerer(String answerer) {
        this.answerer = answerer;
    }
    /**
     * Getter method of id
     * @return a String for storing id of the answer
     */
    public String getId() {
        return id;
    }
    /**
     * setter of id
     * @param id String for storing the id to be changed to
     */
    public void setId(String id) {
        this.id = id;
    }
}
