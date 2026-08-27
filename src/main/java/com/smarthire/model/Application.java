package com.smarthire.model;

public class Application {

    private int id;
    private int jobId;
    private int applicantId;
    private int resumeId;
    private String status;

    public Application() {
    }

    public Application(int id, int jobId, int applicantId,
                       int resumeId, String status) {

        this.id = id;
        this.jobId = jobId;
        this.applicantId = applicantId;
        this.resumeId = resumeId;
        this.status = status;
    }

    public Application(int jobId, int applicantId,
                       int resumeId, String status) {

        this.jobId = jobId;
        this.applicantId = applicantId;
        this.resumeId = resumeId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}