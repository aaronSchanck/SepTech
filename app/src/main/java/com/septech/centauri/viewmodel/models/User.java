package com.septech.centauri.viewmodel.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int userid;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String dateOfBirth;

    private JSONObject json;

    public User() {

    }

    public User(int userid, String username, String email, String firstName, String lastName,
                String dateOfBirth) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public User(JSONObject json) {
        this.json = json;

        try {
            this.userid = (int) Double.parseDouble(json.get("userid").toString());
            this.username = json.get("username").toString();
            this.email = json.get("email").toString();
            this.firstName = json.get("first_name").toString();
            this.lastName = json.get("last_name").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
}