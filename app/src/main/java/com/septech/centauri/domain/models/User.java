package com.septech.centauri.domain.models;

import androidx.annotation.NonNull;

public class User extends GenericModel {
    private int id;

    private String username;
    private String email;
    private String password;
    private String passwordSalt;

    private String fullName;

    private String dateOfBirth;
    private String phoneNumber;

    private Integer mailingAddressId;
    private Address mailingAddress;

    private Integer billingAddressId;
    private Address billingAddress;

    private String createdAt;
    private String modifiedAt;

    private String adminLevel;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(String email, String password, String passwordSalt, String fullName, String dateOfBirth, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        int index = fullName.indexOf(' ');

        return fullName.substring(0, index);
    }

    public String getLastName() {
        int index = fullName.lastIndexOf(' ');

        return index == fullName.length() ? "" : fullName.substring(index + 1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        super.setId(id);
        this.id = id;
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

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Integer getMailingAddressId() {
        return mailingAddressId;
    }

    public void setMailingAddressId(Integer mailingAddressId) {
        this.mailingAddressId = mailingAddressId;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public Integer getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(Integer billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    @Override
    public void initTestData() {

    }

    @NonNull
    @Override
    public String toString() {
        return "User " + super.toString();
    }
}