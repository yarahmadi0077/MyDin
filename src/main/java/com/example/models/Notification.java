package com.example.models;

public class Notification {
    private static String message;

    public Notification(String message) {
        this.message = message;
    }

    public static String getMessage(User t) {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
