package com.septech.centauri.ui.user.forgotpasswordnew;

import androidx.annotation.Nullable;

public class ForgotPasswordNewFormState {
    private static final String TAG = "NewPasswordFormState";

    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer confirmPasswordError;

    private boolean passwordEdited;
    private boolean confirmPasswordEdited;

    private boolean isDataValid;

    ForgotPasswordNewFormState() {
        this.passwordError = null;
        this.confirmPasswordError = null;

        this.passwordEdited = false;
        this.confirmPasswordEdited = false;

        this.isDataValid = false;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getConfirmPasswordError() {
        return confirmPasswordError;
    }

    public void setPasswordError(@Nullable Integer passwordError) {
        this.passwordError = passwordError;
    }

    public void setConfirmPasswordError(@Nullable Integer confirmPasswordError) {
        this.confirmPasswordError = confirmPasswordError;
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

    boolean isDataValid() {
        return isDataValid;
    }

    public void checkDataValid() {
        if ((confirmPasswordEdited
                && passwordEdited)
                &&
                (passwordError == null
                        && confirmPasswordError == null
                ))
            isDataValid = true;
        isDataValid = false;
    }
}
