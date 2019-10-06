package com.unimelb.lecmore;

public class Dates {
    public int dot;
    public String date;

    public Dates(){

    }

    public Dates(int dot, String feedback){
        this.dot = dot;
        this.date = feedback;
    }

    public int getDot() {
        return dot;
    }

    public String getDate() {
        return date;
    }
}
