package com.septech.centauri.ui.register;

import androidx.annotation.Nullable;

public class RegisterFormState {
    private static final String TAG = "RegisterFormState";

    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer firstNameError;
    @Nullable
    private Integer lastNameError;
    @Nullable
    private Integer phoneNumberError;

    private boolean isDataValid;

    RegisterFormState(@Nullable Integer emailError, @Nullable Integer passwordError, @Nullable Integer firstNameError, @Nullable Integer lastNameError, @Nullable Integer phoneNumberError) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.phoneNumberError = phoneNumberError;
        this.phoneNumberError = phoneNumberError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.emailError = null;
        this.passwordError = null;
        this.firstNameError = null;
        this.lastNameError = null;
        this.phoneNumberError = null;

        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getFirstNameError() {
        return firstNameError;
    }

    @Nullable
    public Integer getLastNameError() {
        return lastNameError;
    }

    @Nullable
    public Integer getPhoneNumberError() {
        return phoneNumberError;
    }


    boolean isDataValid() {
        return isDataValid;
    }
}
