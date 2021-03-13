package com.septech.centauri.models;

import org.json.JSONObject;

public class User extends Model implements Comparable<User>{
    private int userid;
    private String username;
    private String email;
    private String firstName;
    private String lastName;

    public User() {

    }

    public User(int userid, String username, String email, String firstName, String lastName) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(JSONObject json) {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


    @Override
    public int compareTo(User o) {
        return this.userid - o.userid;
    }
}
