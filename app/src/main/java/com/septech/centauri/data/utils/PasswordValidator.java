package com.septech.centauri.data.utils;

import androidx.annotation.Nullable;

import com.septech.centauri.R;

public class PasswordValidator {
    private static final String TAG = "PasswordValidator";

    private final String password;

    private final int MIN_LENGTH = 6;
    private final int MAX_LENGTH = 24; //arbitrary
    private final int MIN_UPPER_CHARS = 1;
    private final int MIN_LOWER_CHARS = 1;
    private final int MIN_NUMS = 1;
    private final int MIN_SPECIAL_CHARS = 1;

    @Nullable
    private Integer errorStr;

    public PasswordValidator(String password) {
        this.password = password;
    }

    public boolean isValid() {
        int length = 0;
        int upperChars = 0;
        int lowerChars = 0;
        int specialchars = 0;   //maybe use in the future
        int nums = 0;

        for (char c : this.password.toCharArray()) {
            length++;
            if (isNumeric(c)) nums++;
            else if (isLowerChar(c)) lowerChars++;
            else if (isUpperChar(c)) upperChars++;
            else if (isSpecial(c)) specialchars++;
            else return false;
        }

        if (length < MIN_LENGTH) {
            errorStr = R.string.registration_password_length;
            return false;
        } else if (lowerChars < MIN_LOWER_CHARS) {
            errorStr = R.string.registration_password_lowercase_char;
            return false;
        } else if (upperChars < MIN_UPPER_CHARS) {
            errorStr = R.string.registration_password_uppercase_char;
            return false;
        } else if (nums < MIN_NUMS) {
            errorStr = R.string.registration_password_number;
            return false;
        }
//        else if (specialchars < MIN_SPECIAL_CHARS) {
//            errorStr = R.string.registration_password_lowercase_char;
//            return false;
//        }

        else {
            errorStr = null;
            return true;
        }
    }

    private boolean isLowerChar(char c) {
        return c >= 97 && c <= 122;
    }

    private boolean isUpperChar(char c) {
        return c >= 65 && c <= 90;
    }

    private boolean isNumeric(char c) {
        return c >= 48 && c <= 57;
    }

    private boolean isSpecial(char c) {
        return (c >= 32 && c <= 46) || (c >= 63 && c <= 64) || c == 126;
    }

    public Integer getPwError() {
        return errorStr;
    }
}
