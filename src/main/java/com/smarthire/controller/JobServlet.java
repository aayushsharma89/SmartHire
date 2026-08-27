package com.smarthire.controller;

import java.io.IOException;
import java.util.List;

import com.smarthire.dao.CompanyDAO;
import com.smarthire.dao.JobDAO;
import com.smarthire.model.Company;
import com.smarthire.model.Job;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/jobs")
public class JobServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private JobDAO jobDAO;
    private CompanyDAO companyDAO;

    @Override
    public void init() {

        jobDAO =
                new JobDAO();

        companyDAO =
                new CompanyDAO();
    }

    // =========================================================
    // GET
    // =========================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");

        if ("post".equals(action)) {

            showPostJobPage(
                    request,
                    response
            );

        } else if ("myJobs".equals(action)) {

            showRecruiterJobs(
                    request,
                    response
            );

        } else if ("details".equals(action)) {

            showJobDetails(
                    request,
                    response
            );

        } else if ("edit".equals(action)) {

            showEditJobPage(
                    request,
                    response
            );

        } else if ("delete".equals(action)) {

            deleteJob(
                    request,
                    response
            );

        } else {

            showAllJobs(
                    request,
                    response
            );
        }
    }


    // =========================================================
    // POST
    // =========================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");

        if ("update".equals(action)) {

            updateJob(
                    request,
                    response
            );

        } else {

            addJob(
                    request,
                    response
            );
        }
    }


    // =========================================================
    // LOGIN CHECK
    // =========================================================

    private boolean isLoggedIn(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        return session != null &&
               session.getAttribute("userId") != null;
    }


    // =========================================================
    // USER ID
    // =========================================================

    private int getUserId(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        return (Integer)
                session.getAttribute("userId");
    }


    // =========================================================
    // OPEN POST JOB PAGE
    // =========================================================

    private void showPostJobPage(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!isLoggedIn(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        int recruiterId =
                getUserId(request);

        /*
         * Get ALL companies owned by the recruiter.
         */

        List<Company> companies =
                companyDAO
                .getCompaniesByRecruiterId(
                        recruiterId
                );

        /*
         * If recruiter has no company,
         * send them to company management.
         */

        if (companies == null ||
            companies.isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/company?action=new"
            );

            return;
        }

        /*
         * Send company list to post-job.jsp.
         */

        request.setAttribute(
                "companies",
                companies
        );

        request.getRequestDispatcher(
                "/post-job.jsp"
        ).forward(
                request,
                response
        );
    }


    // =========================================================
    // ADD JOB
    // =========================================================

    private void addJob(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        if (!isLoggedIn(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        int recruiterId =
                getUserId(request);

        String companyIdParameter =
                request.getParameter(
                        "companyId"
                );

        if (companyIdParameter == null ||
            companyIdParameter.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=post"
            );

            return;
        }

        int companyId;

        try {

            companyId =
                    Integer.parseInt(
                            companyIdParameter
                    );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=post"
            );

            return;
        }

        /*
         * SECURITY:
         * Make sure the selected company belongs
         * to the logged-in recruiter.
         */

        Company company =
                companyDAO
                .getCompanyByIdForRecruiter(
                        companyId,
                        recruiterId
                );

        if (company == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=post"
            );

            return;
        }

        String title =
                request.getParameter("title");

        String description =
                request.getParameter("description");

        String requirements =
                request.getParameter("requirements");

        String location =
                request.getParameter("location");

        String salary =
                request.getParameter("salary");

        String jobType =
                request.getParameter("jobType");

        String skills =
                request.getParameter("skills");

        Job job =
                new Job(
                        recruiterId,
                        companyId,
                        title,
                        description,
                        requirements,
                        location,
                        salary,
                        jobType,
                        skills
                );

        boolean added =
                jobDAO.addJob(job);

        if (added) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=myJobs"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=post&error=job"
            );
        }
    }


    // =========================================================
    // SHOW ALL JOBS
    // =========================================================

    private void showAllJobs(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String keyword =
                request.getParameter(
                        "keyword"
                );

        String location =
                request.getParameter(
                        "location"
                );

        String jobType =
                request.getParameter(
                        "jobType"
                );

        List<Job> jobs;

        if ((keyword != null &&
             !keyword.isBlank()) ||

            (location != null &&
             !location.isBlank()) ||

            (jobType != null &&
             !jobType.isBlank())) {

            jobs =
                    jobDAO.searchJobs(
                            keyword,
                            location,
                            jobType
                    );

        } else {

            jobs =
                    jobDAO.getAllJobs();
        }

        request.setAttribute(
                "jobs",
                jobs
        );

        request.setAttribute(
                "myJobs",
                false
        );

        request.getRequestDispatcher(
                "/jobs.jsp"
        ).forward(
                request,
                response
        );
    }


    // =========================================================
    // RECRUITER MY JOBS
    // =========================================================

    private void showRecruiterJobs(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!isLoggedIn(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        int recruiterId =
                getUserId(request);

        List<Job> jobs =
                jobDAO.getJobsByRecruiter(
                        recruiterId
                );

        request.setAttribute(
                "jobs",
                jobs
        );

        request.setAttribute(
                "myJobs",
                true
        );

        request.getRequestDispatcher(
                "/jobs.jsp"
        ).forward(
                request,
                response
        );
    }


    // =========================================================
    // JOB DETAILS
    // =========================================================

    private void showJobDetails(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idParameter =
                request.getParameter("id");

        if (idParameter == null ||
            idParameter.isBlank()) {

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
                            idParameter
                    );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs"
            );

            return;
        }

        Job job =
                jobDAO.getJobById(jobId);

        if (job == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs"
            );

            return;
        }

        request.setAttribute(
                "job",
                job
        );

        request.getRequestDispatcher(
                "/job-details.jsp"
        ).forward(
                request,
                response
        );
    }


    // =========================================================
    // EDIT JOB
    // =========================================================

    private void showEditJobPage(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!isLoggedIn(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        String idParameter =
                request.getParameter("id");

        if (idParameter == null ||
            idParameter.isBlank()) {

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
                            idParameter
                    );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=myJobs"
            );

            return;
        }

        int recruiterId =
                getUserId(request);

        Job job =
                jobDAO
                .getJobByIdForRecruiter(
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

        request.setAttribute(
                "job",
                job
        );

        request.getRequestDispatcher(
                "/edit-job.jsp"
        ).forward(
                request,
                response
        );
    }


    // =========================================================
    // UPDATE JOB
    // =========================================================

    private void updateJob(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        if (!isLoggedIn(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        int recruiterId =
                getUserId(request);

        try {

            int jobId =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            int companyId =
                    Integer.parseInt(
                            request.getParameter(
                                    "companyId"
                            )
                    );

            Company company =
                    companyDAO
                    .getCompanyByIdForRecruiter(
                            companyId,
                            recruiterId
                    );

            if (company == null) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/jobs?action=myJobs"
                );

                return;
            }

            Job job =
                    new Job(
                            jobId,
                            recruiterId,
                            companyId,
                            request.getParameter(
                                    "title"
                            ),
                            request.getParameter(
                                    "description"
                            ),
                            request.getParameter(
                                    "requirements"
                            ),
                            request.getParameter(
                                    "location"
                            ),
                            request.getParameter(
                                    "salary"
                            ),
                            request.getParameter(
                                    "jobType"
                            ),
                            request.getParameter(
                                    "skills"
                            )
                    );

            boolean updated =
                    jobDAO.updateJob(job);

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=myJobs"
                            + (updated
                                ? ""
                                : "&error=update")
            );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=myJobs"
            );
        }
    }


    // =========================================================
    // DELETE JOB
    // =========================================================

    private void deleteJob(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        if (!isLoggedIn(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        String idParameter =
                request.getParameter("id");

        if (idParameter == null ||
            idParameter.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=myJobs"
            );

            return;
        }

        try {

            int jobId =
                    Integer.parseInt(
                            idParameter
                    );

            int recruiterId =
                    getUserId(request);

            boolean deleted =
                    jobDAO.deleteJob(
                            jobId,
                            recruiterId
                    );

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=myJobs"
                            + (deleted
                                ? ""
                                : "&error=delete")
            );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/jobs?action=myJobs"
            );
        }
    }
}