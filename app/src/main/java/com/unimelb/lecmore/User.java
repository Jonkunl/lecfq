package com.unimelb.lecmore;

class User {

    private String password;
    private String email;
    private String gender;
    private String name;

    public User(){

    }

    /**
     * Data structure for storing the information of user, including the password, email, gender
     * and name
     * @param password String for storing password
     * @param email String for storing email
     * @param gender String for storing gender
     * @param name String for storing name
     */
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
