package com.thundersharp.test.core.util;

import java.util.regex.Pattern;

public class EmailUtils {

    public static boolean isEmailValid(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(email).matches();

    }

}
