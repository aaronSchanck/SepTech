package com.septech.centauri.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserEntity {
    @SerializedName("id")
    @Expose(serialize = false)
    private int mUserId;

    @SerializedName("username")
    @Expose(serialize = false)
    private String mUsername;

    @SerializedName("email")
    @Expose
    private String mEmail;

    @SerializedName("password")
    @Expose
    private String mPassword;

    @SerializedName("full_name")
    @Expose
    private String mFullName;

    @SerializedName("date_of_birth")
    @Expose
    private String mDateOfBirth;

    @SerializedName("phone_number")
    @Expose
    private String mPhoneNumber;

    @SerializedName("password_salt")
    @Expose
    private String mPasswordSalt;

    @SerializedName("created_at")
    @Expose(serialize=false)
    private String mCreatedAt;

    @SerializedName("modified_at")
    @Expose(serialize=false)
    private String mModifiedAt;

    @SerializedName("admin_level")
    @Expose(serialize=false)
    private String mAdminLevel;

    public UserEntity() {
        //empty
    }

    public UserEntity(int userId) {
        this.mUserId = userId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        mDateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getPasswordSalt() {
        return mPasswordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        mPasswordSalt = passwordSalt;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getModifiedAt() {
        return mModifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        mModifiedAt = modifiedAt;
    }

    public String getAdminLevel() {
        return mAdminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        mAdminLevel = adminLevel;
    }
}
