package com.smarthire.model;

public class Job {

    private int id;
    private int recruiterId;
    private int companyId;
    private String title;
    private String description;
    private String requirements;
    private String location;
    private String salary;
    private String jobType;
    private String skills;

    public Job() {
    }

    public Job(int id, int recruiterId, int companyId,
               String title, String description, String requirements,
               String location, String salary, String jobType,
               String skills) {

        this.id = id;
        this.recruiterId = recruiterId;
        this.companyId = companyId;
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.location = location;
        this.salary = salary;
        this.jobType = jobType;
        this.skills = skills;
    }

    public Job(int recruiterId, int companyId,
               String title, String description, String requirements,
               String location, String salary, String jobType,
               String skills) {

        this.recruiterId = recruiterId;
        this.companyId = companyId;
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.location = location;
        this.salary = salary;
        this.jobType = jobType;
        this.skills = skills;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
