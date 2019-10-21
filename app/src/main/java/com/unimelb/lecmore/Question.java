package com.unimelb.lecmore;

public class Question {
    public String question;
    public String answer;

    public Question(){

    }
    public Question(String question,String answer){
        this.question = question;
        this.answer = answer;
    }
    public void setAnswer(String answer){
        this.answer = answer;
    }

}
