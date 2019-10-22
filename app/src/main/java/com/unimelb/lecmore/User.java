package com.unimelb.lecmore;

class User {

    private String password;
    private String email;
    private String gender;
    private String name;

    public User(){

    }

    public User(String password, String email, String gender, String name) {
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }
}
