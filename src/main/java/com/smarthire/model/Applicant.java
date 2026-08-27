package com.smarthire.model;

public class Applicant {

    private int applicationId;
    private int jobId;
    private int applicantId;
    private int resumeId;

    private String applicantName;
    private String applicantEmail;
    private String jobTitle;
    private String status;

    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public Applicant() {
    }

    // =========================================================
    // APPLICATION ID
    // =========================================================

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    // =========================================================
    // JOB ID
    // =========================================================

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    // =========================================================
    // APPLICANT ID
    // =========================================================

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    // =========================================================
    // RESUME ID
    // =========================================================

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    // =========================================================
    // APPLICANT NAME
    // =========================================================

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    // =========================================================
    // APPLICANT EMAIL
    // =========================================================

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    // =========================================================
    // JOB TITLE
    // =========================================================

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    // =========================================================
    // STATUS
    // =========================================================

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}