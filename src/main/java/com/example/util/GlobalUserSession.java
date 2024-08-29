package com.example.util;

public class GlobalUserSession {
    private static String email;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        GlobalUserSession.email = email;
    }
}
