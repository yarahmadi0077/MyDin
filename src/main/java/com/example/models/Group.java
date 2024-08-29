package com.example.models;

public class Group {
    private String name;
    private User admin;

    public Group(String name, User admin) {
        this.name = name;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }
}
