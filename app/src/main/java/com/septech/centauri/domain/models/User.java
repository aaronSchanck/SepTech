package com.septech.centauri.domain.models;

public class User {
    private int userid;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;

    private String salt1;

    public User() {

    }

    public User(int userid, String email, String firstName, String lastName, String dateOfBirth,
                String salt1) {
        this.userid = userid;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.salt1 = salt1;
    }

    public User(int userid, String email, String password, String firstName,
                String lastName, String dateOfBirth, String salt1) {
        this.userid = userid;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.salt1 = salt1;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordSalt() {
        return salt1;
    }
}