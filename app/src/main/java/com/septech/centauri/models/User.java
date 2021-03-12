package com.septech.centauri.models;

public class User extends Model implements Comparable<User> {
    private int userid;
    private String username;
    private String email;
    private String firstName;
    private String lastName;

    public User() {

    }

    public User(int userid, String username, String email, String firstName, String lastName) {

    }

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
