package com.septech.centauri.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserEntity {

    @SerializedName("id")
    @Expose(serialize = false)
    private int userid;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("password_salt1")
    @Expose
    private String passwordSalt;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("modified_at")
    @Expose
    private String modifiedAt;

    @SerializedName("password_reset_code")
    @Expose
    private String passwordResetCode;

    @SerializedName("password_reset_timeout")
    @Expose
    private String passwordResetTimeout;

    @SerializedName("admin_level")
    @Expose
    private String adminLevel;

    public UserEntity() {
        //empty
    }

    public UserEntity(int userid) {
        this.userid = userid;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getPasswordResetCode() {
        return passwordResetCode;
    }

    public void setPasswordResetCode(String passwordResetCode) {
        this.passwordResetCode = passwordResetCode;
    }

    public String getPasswordResetTimeout() {
        return passwordResetTimeout;
    }

    public void setPasswordResetTimeout(String passwordResetTimeout) {
        this.passwordResetTimeout = passwordResetTimeout;
    }
}
