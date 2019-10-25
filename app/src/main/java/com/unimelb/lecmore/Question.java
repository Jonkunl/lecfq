package com.unimelb.lecmore;

public class Question {
    public String question;
    public String answer;

    public Question(){

    }

    /**
     * Data structure for storing information of questions including the content and the answer of
     * and question. Used in monitoring the data change of database.
     * @param question String for storing the content of the question
     * @param answer String for storing the answer of the question
     */
    public Question(String question,String answer){
        this.question = question;
        this.answer = answer;
    }
    public void setAnswer(String answer){
        this.answer = answer;
    }
}
