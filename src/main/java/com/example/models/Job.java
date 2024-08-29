package com.example.models;

import java.util.ArrayList;
import java.sql.Date;

public class Job {
    private String title;
    private String employmentType;
    private String companyName;
    private String location;
    private String locationType;
    private boolean activity;
    private Date startToWork;
    private Date endToWork;
    private String description;
    private String skills;

    public Job(String title, String employmentType,  String companyName, String location,
               String locationType, boolean activity, Date startToWork, Date endToWork, String description) {
        this.title = title;
        this.employmentType = employmentType;
        this.location = location;
        this.companyName = companyName;
        this.locationType = locationType;
        this.activity = activity;
        this.startToWork = startToWork;
        this.endToWork = endToWork;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public Date getStartToWork() {
        return startToWork;
    }

    public void setStartToWork(Date startToWork) {
        this.startToWork = startToWork;
    }

    public Date getEndToWork() {
        return endToWork;
    }

    public void setEndToWork(Date endToWork) {
        this.endToWork = endToWork;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @Override
    public String toString(){
        return title + "\n" +
                companyName + " . " + employmentType
                + "\n" + startToWork + " - " + endToWork +
                " \n" + location;
    }
}
