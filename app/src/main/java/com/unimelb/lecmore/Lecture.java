package com.unimelb.lecmore;

import java.util.UUID;

public class Lecture {
    private String mId;
    private String mName;
    private String mLecturer;

    public Lecture() {

    }

    public Lecture(String id, String name, String lecturer) {
        mId = id;
        mName = name;
        mLecturer = lecturer;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLecturer() {
        return mLecturer;
    }

    public void setLecturer(String lecturer) {
        this.mLecturer = lecturer;
    }
}
