package com.smarthire.controller;

import java.io.IOException;
import java.util.List;

import com.smarthire.dao.ApplicationDAO;
import com.smarthire.dao.JobDAO;
import com.smarthire.dao.ResumeDAO;
import com.smarthire.model.Applicant;
import com.smarthire.model.Application;
import com.smarthire.model.Job;
import com.smarthire.model.Resume;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/application")
public class ApplicationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ApplicationDAO applicationDAO;
    private JobDAO jobDAO;
    private ResumeDAO resumeDAO;

    // =========================================================
    // INITIALIZE
    // =========================================================

    @Override
    public void init() {

        applicationDAO =
                new ApplicationDAO();

        jobDAO =
                new JobDAO();

        resumeDAO =
                new ResumeDAO();
    }


    // =========================================================
    // GET REQUEST
    // =========================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // -----------------------------------------------------
        // LOGIN CHECK
        // -----------------------------------------------------

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        String action =
                request.getParameter("action");

        // =====================================================
        // RECRUITER - ALL APPLICANTS
        // From Recruiter Dashboard
        // =====================================================

        if ("all".equals(action)) {

            showAllRecruiterApplicants(
                    request,
                    response
            );

            return;
        }

        // =====================================================
        // RECRUITER - APPLICANTS FOR ONE JOB
        // From My Jobs
        // =====================================================

        if ("job".equals(action)) {

            showApplicants(
                    request,
                    response
            );

            return;
        }

        // =====================================================
        // JOB SEEKER - MY APPLICATIONS
        // =====================================================

        showMyApplications(
                request,
                response
        );
    }


    // =========================================================
    // POST REQUEST
    // =========================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");

        // =====================================================
        // JOB SEEKER - APPLY
        // =====================================================

        if ("apply".equals(action)) {

            applyForJob(
                    request,
                    response
            );

            return;
        }

        // =====================================================
        // RECRUITER - UPDATE STATUS
        // =====================================================

        if ("updateStatus".equals(action)) {

            updateStatus(
                    request,
                    response
            );

            return;
        }
    }


    // =========================================================
    // APPLY FOR JOB
    // =========================================================

    private void applyForJob(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        int applicantId =
                (Integer) session.getAttribute(
                        "userId"
                );

        String role =
                (String) session.getAttribute(
                        "role"
                );

        // -----------------------------------------------------
        // Only Job Seeker can apply
        // -----------------------------------------------------

        if (!"JOB_SEEKER".equals(role)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs"
            );

            return;
        }

        // -----------------------------------------------------
        // Get Job ID
        // -----------------------------------------------------

        String jobIdParameter =
                request.getParameter(
                        "jobId"
                );

        if (jobIdParameter == null ||
            jobIdParameter.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs"
            );

            return;
        }

        int jobId;

        try {

            jobId =
                    Integer.parseInt(
                            jobIdParameter
                    );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs"
            );

            return;
        }

        // -----------------------------------------------------
        // Check job exists
        // -----------------------------------------------------

        Job job =
                jobDAO.getJobById(jobId);

        if (job == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs"
            );

            return;
        }

        // =====================================================
        // AUTOMATICALLY GET RESUME
        // =====================================================

        int resumeId = 0;

        Resume resume =
                resumeDAO.getResumeByUserId(
                        applicantId
                );

        if (resume != null) {

            resumeId =
                    resume.getId();
        }

        // =====================================================
        // CREATE APPLICATION
        // =====================================================

        Application application =
                new Application(
                        jobId,
                        applicantId,
                        resumeId,
                        "APPLIED"
                );

        boolean applied =
                applicationDAO.applyForJob(
                        application
                );

        // =====================================================
        // RESULT
        // =====================================================

        if (applied) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/application"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?error=application"
            );
        }
    }


    // =========================================================
    // JOB SEEKER - MY APPLICATIONS
    // =========================================================

    private void showMyApplications(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        int applicantId =
                (Integer) session.getAttribute(
                        "userId"
                );

        List<Application> applications =
                applicationDAO
                .getApplicationsByApplicant(
                        applicantId
                );

        request.setAttribute(
                "applications",
                applications
        );

        request.getRequestDispatcher(
                "/applications.jsp"
        ).forward(
                request,
                response
        );
    }


    // =========================================================
    // RECRUITER - APPLICANTS FOR ONE JOB
    // =========================================================

    private void showApplicants(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        String role =
                (String) session.getAttribute(
                        "role"
                );

        // -----------------------------------------------------
        // Recruiter only
        // -----------------------------------------------------

        if (!"RECRUITER".equals(role)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        String jobIdParameter =
                request.getParameter(
                        "jobId"
                );

        if (jobIdParameter == null ||
            jobIdParameter.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=myJobs"
            );

            return;
        }

        int jobId;

        try {

            jobId =
                    Integer.parseInt(
                            jobIdParameter
                    );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=myJobs"
            );

            return;
        }

        int recruiterId =
                (Integer) session.getAttribute(
                        "userId"
                );

        // =====================================================
        // SECURITY CHECK
        // =====================================================

        Job job =
                jobDAO.getJobByIdForRecruiter(
                        jobId,
                        recruiterId
                );

        if (job == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=myJobs"
            );

            return;
        }

        // =====================================================
        // LOAD APPLICANTS
        // =====================================================

        List<Applicant> applicants =
                applicationDAO
                .getApplicantsByJob(
                        jobId
                );

        request.setAttribute(
                "applicants",
                applicants
        );

        request.setAttribute(
                "job",
                job
        );

        /*
         * Tell JSP that this is the
         * single-job applicant view.
         */
        request.setAttribute(
                "allApplicants",
                false
        );

        request.getRequestDispatcher(
                "/applicants.jsp"
        ).forward(
                request,
                response
        );
    }


    // =========================================================
    // RECRUITER - ALL APPLICANTS
    // =========================================================

    private void showAllRecruiterApplicants(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        String role =
                (String) session.getAttribute(
                        "role"
                );

        // -----------------------------------------------------
        // Recruiter only
        // -----------------------------------------------------

        if (!"RECRUITER".equals(role)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        int recruiterId =
                (Integer) session.getAttribute(
                        "userId"
                );

        // =====================================================
        // GET ALL APPLICANTS FOR THIS RECRUITER
        // =====================================================

        List<Applicant> applicants =
                applicationDAO
                .getApplicantsByRecruiter(
                        recruiterId
                );

        request.setAttribute(
                "applicants",
                applicants
        );

        request.setAttribute(
                "allApplicants",
                true
        );

        request.getRequestDispatcher(
                "/applicants.jsp"
        ).forward(
                request,
                response
        );
    }


    // =========================================================
    // UPDATE APPLICATION STATUS
    // =========================================================

    private void updateStatus(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        String role =
                (String) session.getAttribute(
                        "role"
                );

        // -----------------------------------------------------
        // Recruiter only
        // -----------------------------------------------------

        if (!"RECRUITER".equals(role)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        String applicationIdParameter =
                request.getParameter(
                        "applicationId"
                );

        String jobIdParameter =
                request.getParameter(
                        "jobId"
                );

        String status =
                request.getParameter(
                        "status"
                );

        if (applicationIdParameter == null ||
            jobIdParameter == null ||
            status == null ||
            status.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/recruiter-dashboard.jsp"
            );

            return;
        }

        int applicationId;
        int jobId;

        try {

            applicationId =
                    Integer.parseInt(
                            applicationIdParameter
                    );

            jobId =
                    Integer.parseInt(
                            jobIdParameter
                    );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/recruiter-dashboard.jsp"
            );

            return;
        }

        // =====================================================
        // VALIDATE STATUS
        // =====================================================

        if (!"APPLIED".equals(status) &&
            !"SHORTLISTED".equals(status) &&
            !"REJECTED".equals(status) &&
            !"SELECTED".equals(status)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/application?action=job&jobId="
                            + jobId
            );

            return;
        }

        // =====================================================
        // VERIFY JOB OWNERSHIP
        // =====================================================

        int recruiterId =
                (Integer) session.getAttribute(
                        "userId"
                );

        Job job =
                jobDAO.getJobByIdForRecruiter(
                        jobId,
                        recruiterId
                );

        if (job == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/recruiter-dashboard.jsp"
            );

            return;
        }

        // =====================================================
        // UPDATE
        // =====================================================

        applicationDAO
                .updateApplicationStatus(
                        applicationId,
                        status
                );

        // =====================================================
        // RETURN TO THE SAME JOB'S APPLICANTS
        // =====================================================

        response.sendRedirect(
                request.getContextPath()
                        + "/application?action=job&jobId="
                        + jobId
        );
    }
}