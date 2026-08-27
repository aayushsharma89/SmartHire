package com.smarthire.model;

public class Company {

    private int id;
    private int recruiterId;
    private String companyName;
    private String description;
    private String location;
    private String website;

    public Company() {
    }

    public Company(int id, int recruiterId,
                   String companyName, String description,
                   String location, String website) {

        this.id = id;
        this.recruiterId = recruiterId;
        this.companyName = companyName;
        this.description = description;
        this.location = location;
        this.website = website;
    }

    public Company(int recruiterId,
                   String companyName, String description,
                   String location, String website) {

        this.recruiterId = recruiterId;
        this.companyName = companyName;
        this.description = description;
        this.location = location;
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(int recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
