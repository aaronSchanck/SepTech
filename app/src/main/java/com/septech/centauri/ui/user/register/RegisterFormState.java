package com.septech.centauri.ui.user.register;

import androidx.annotation.Nullable;

public class RegisterFormState {
    private static final String TAG = "RegisterFormState";

    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer confirmPasswordError;
    @Nullable
    private Integer fullNameError;
    @Nullable
    private Integer phoneNumberError;

    private boolean emailEdited;
    private boolean passwordEdited;
    private boolean confirmPasswordEdited;
    private boolean fullNameEdited;
    private boolean phoneNumberEdited;

    private boolean isDataValid;

    RegisterFormState() {
        this.emailError = null;
        this.passwordError = null;
        this.fullNameError = null;
        this.phoneNumberError = null;
        this.confirmPasswordError = null;

        this.emailEdited = false;
        this.passwordEdited = false;
        this.confirmPasswordEdited = false;
        this.fullNameEdited = false;
        this.phoneNumberEdited = false;

        this.isDataValid = false;
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
    public Integer getFullNameError() {
        return fullNameError;
    }

    @Nullable
    public Integer getPhoneNumberError() {
        return phoneNumberError;
    }

    @Nullable
    public Integer getConfirmPasswordError() {
        return confirmPasswordError;
    }

    public void setEmailError(@Nullable Integer emailError) {
        this.emailError = emailError;
    }

    public void setPasswordError(@Nullable Integer passwordError) {
        this.passwordError = passwordError;
    }

    public void setConfirmPasswordError(@Nullable Integer confirmPasswordError) {
        this.confirmPasswordError = confirmPasswordError;
    }

    public void setFullNameError(@Nullable Integer fullNameError) {
        this.fullNameError = fullNameError;
    }

    public void setPhoneNumberError(@Nullable Integer phoneNumberError) {
        this.phoneNumberError = phoneNumberError;
    }

    public boolean isEmailEdited() {
        return emailEdited;
    }

    public void setEmailEdited(boolean emailEdited) {
        this.emailEdited = emailEdited;
    }

    public boolean isPasswordEdited() {
        return passwordEdited;
    }

    public void setPasswordEdited(boolean passwordEdited) {
        this.passwordEdited = passwordEdited;
    }

    public boolean isConfirmPasswordEdited() {
        return confirmPasswordEdited;
    }

    public void setConfirmPasswordEdited(boolean confirmPasswordEdited) {
        this.confirmPasswordEdited = confirmPasswordEdited;
    }

    public boolean isFullNameEdited() {
        return fullNameEdited;
    }

    public void setFullNameEdited(boolean fullNameEdited) {
        this.fullNameEdited = fullNameEdited;
    }

    public boolean isPhoneNumberEdited() {
        return phoneNumberEdited;
    }

    public void setPhoneNumberEdited(boolean phoneNumberEdited) {
        this.phoneNumberEdited = phoneNumberEdited;
    }


    boolean isDataValid() {
        return isDataValid;
    }

    public void checkDataValid() {
        if ((emailEdited
                && fullNameEdited
                && confirmPasswordEdited
                && passwordEdited
                && phoneNumberEdited)
                &&
                (fullNameError == null
                && passwordError == null
                && confirmPasswordError == null
                && emailError == null
                && phoneNumberError == null
                ))
            isDataValid = true;
        isDataValid = false;
    }
}
