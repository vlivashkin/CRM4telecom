package com.crm4telecom.web.util;

public class StringUtils {

    public static String toString(Object object) {
        if (object != null) {
            return object.toString();
        }
        return null;
    }

    public static boolean isValidString(String str) {
        return str != null && str.length() != 0;
    }
}
