package com.example.models;

public class Media {
    private int id;
    private String path;

    public Media() {
    }

    public Media(String path) {
        this.path = path;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}