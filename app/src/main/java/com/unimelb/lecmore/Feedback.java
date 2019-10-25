package com.unimelb.lecmore;

public class Feedback {
    private int rating;
    private String comment;

    public Feedback(){

    }

    /**
     * A data structure for storing the feedback from student, including the rating and the comment
     * @param rating String for storing the rating of the feedback
     * @param comment String for storing the comment of the feedback
     */
    public Feedback(int rating, String comment){
        this.rating = rating;
        this.comment = comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
