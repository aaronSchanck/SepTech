package com.septech.centauri.data.model.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessEntity {
    @SerializedName("id")
    @Expose(serialize = false)
    private int id;

    @SerializedName("business_name")
    @Expose
    private String businessName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("owner_full_name")
    @Expose
    private String ownerFullName;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("password_salt")
    @Expose
    private String passwordSalt;

    @SerializedName("created_at")
    @Expose(serialize = false)
    private String createdAt;

    @SerializedName("modified_at")
    @Expose(serialize = false)
    private String modifiedAt;

    @SerializedName("password_reset_code")
    @Expose(serialize = false)
    private String passwordResetCode;

    @SerializedName("password_reset_timeout")
    @Expose(serialize = false)
    private String passwordResetTimeout;

    @SerializedName("description")
    @Expose
    private String description;

    public BusinessEntity() {
        //empty
    }

    public BusinessEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
