package com.unimelb.lecmore;

public class Dates {
    public int dot;
    public String date;

    public Dates(){

    }

    /**
     * a data structure for storing date information for appropriate display
     * @param dot int for storing the number of lecture
     * @param feedback String for storing the date of a feedback
     */
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
