<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.smarthire.model.User" %>
<%@ page import="com.smarthire.model.Job" %>
<%@ page import="com.smarthire.model.Applicant" %>

<%
    User user =
            (User) session.getAttribute("user");

    if (user == null ||
        !"RECRUITER".equals(user.getRole())) {

        response.sendRedirect(
                request.getContextPath()
                        + "/login.jsp"
        );

        return;
    }

    Job job =
            (Job) request.getAttribute(
                    "job"
            );

    List<Applicant> applicants =
            (List<Applicant>)
            request.getAttribute(
                    "applicants"
            );

    Boolean allApplicantsAttribute =
            (Boolean)
            request.getAttribute(
                    "allApplicants"
            );

    boolean allApplicants =
            Boolean.TRUE.equals(
                    allApplicantsAttribute
            );
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>
        SmartHire - Applicants
    </title>

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background: #f4f7fb;
            min-height: 100vh;
        }

        .navbar {
            background: #1e3a8a;
            color: white;
            padding: 18px 40px;

            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 25px;
            font-weight: bold;
        }

        .nav-links {
            display: flex;
            gap: 20px;
        }

        .nav-links a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }

        .nav-links a:hover {
            text-decoration: underline;
        }

        .container {
            width: 95%;
            max-width: 1250px;
            margin: 40px auto;
        }

        .header-box {
            background: white;
            padding: 25px;
            border-radius: 12px;
            margin-bottom: 25px;

            box-shadow:
                0 5px 15px rgba(0,0,0,0.08);
        }

        .header-box h2 {
            color: #1e3a8a;
            margin-bottom: 10px;
        }

        .header-box p {
            color: #666;
        }

        .table-box {
            background: white;
            padding: 25px;
            border-radius: 12px;

            box-shadow:
                0 5px 15px rgba(0,0,0,0.08);

            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            min-width: 1100px;
        }

        th,
        td {
            padding: 14px;

            border-bottom:
                1px solid #e2e8f0;

            text-align: left;
        }

        th {
            background: #f1f5f9;
            color: #333;
        }

        tr:hover {
            background: #f8fafc;
        }

        .candidate-name {
            color: #1e3a8a;
            font-weight: bold;
        }

        .candidate-email {
            color: #555;
        }

        .job-title {
            color: #1e3a8a;
            font-weight: bold;
        }

        .resume-buttons {
            display: flex;
            gap: 7px;
            flex-wrap: wrap;
        }

        .resume-btn {
            display: inline-block;
            padding: 7px 10px;

            color: white;
            text-decoration: none;

            border-radius: 5px;
            font-size: 13px;
        }

        .view-btn {
            background: #2563eb;
        }

        .download-btn {
            background: #475569;
        }

        .no-resume {
            color: #b91c1c;
            font-weight: bold;
        }

        select {
            padding: 8px;

            border: 1px solid #cbd5e1;
            border-radius: 6px;
        }

        .status-form {
            display: flex;
            align-items: center;
            gap: 7px;
        }

        .update-btn {
            background: #2563eb;
            color: white;

            border: none;
            padding: 8px 12px;

            border-radius: 5px;
            cursor: pointer;
        }

        .empty {
            text-align: center;
            padding: 40px;
            color: #666;
        }

        .back-btn {
            display: inline-block;

            margin-top: 20px;
            padding: 10px 16px;

            background: #64748b;
            color: white;

            border-radius: 6px;
            text-decoration: none;
        }

    </style>

</head>

<body>

<div class="navbar">

    <div class="logo">
        SmartHire
    </div>

    <div class="nav-links">

        <a href="<%= request.getContextPath() %>/recruiter-dashboard.jsp">
            Dashboard
        </a>

        <a href="<%= request.getContextPath() %>/jobs?action=myJobs">
            My Jobs
        </a>

        <a href="<%= request.getContextPath() %>/company">
            Companies
        </a>

        <a href="<%= request.getContextPath() %>/logout">
            Logout
        </a>

    </div>

</div>


<div class="container">


    <!-- =====================================================
         HEADER
         ===================================================== -->

    <div class="header-box">

        <% if (allApplicants) { %>

            <h2>
                All Applicants
            </h2>

            <p>
                Candidates who applied to your jobs.
            </p>

        <% } else if (job != null) { %>

            <h2>
                Applicants for:
                <%= job.getTitle() %>
            </h2>

            <p>

                Location:
                <strong>
                    <%= job.getLocation() %>
                </strong>

                &nbsp;&nbsp;|&nbsp;&nbsp;

                Job Type:
                <strong>
                    <%= job.getJobType() %>
                </strong>

            </p>

        <% } else { %>

            <h2>
                Applicants
            </h2>

        <% } %>

    </div>


    <!-- =====================================================
         APPLICANTS TABLE
         ===================================================== -->

    <div class="table-box">

        <% if (applicants == null ||
               applicants.isEmpty()) { %>

            <div class="empty">

                <h3>
                    No Candidates Found
                </h3>

                <p style="margin-top:10px;">
                    No candidates have applied
                    to your jobs yet.
                </p>

            </div>

        <% } else { %>

            <table>

                <thead>

                    <tr>

                        <th>
                            Application ID
                        </th>

                        <% if (allApplicants) { %>

                            <th>
                                Job
                            </th>

                        <% } %>

                        <th>
                            Candidate
                        </th>

                        <th>
                            Email
                        </th>

                        <th>
                            Resume
                        </th>

                        <th>
                            Status
                        </th>

                        <th>
                            Update Status
                        </th>

                    </tr>

                </thead>


                <tbody>

                    <% for (Applicant applicant :
                            applicants) { %>

                        <tr>

                            <td>
                                <%= applicant
                                        .getApplicationId() %>
                            </td>


                            <% if (allApplicants) { %>

                                <td class="job-title">

                                    <%= applicant.getJobTitle() != null
                                            ? applicant.getJobTitle()
                                            : "Job #" +
                                              applicant.getJobId() %>

                                </td>

                            <% } %>


                            <td class="candidate-name">

                                <%= applicant
                                        .getApplicantName() %>

                            </td>


                            <td class="candidate-email">

                                <%= applicant
                                        .getApplicantEmail() %>

                            </td>


                            <!-- =================================
                                 RESUME
                                 ================================= -->

                            <td>

                                <% if (applicant
                                        .getResumeId() > 0) { %>

                                    <div class="resume-buttons">

                                        <a
                                            href="<%= request.getContextPath() %>/resume?action=view&id=<%= applicant.getResumeId() %>"
                                            class="resume-btn view-btn"
                                            target="_blank"
                                        >
                                            View
                                        </a>

                                        <a
                                            href="<%= request.getContextPath() %>/resume?action=download&id=<%= applicant.getResumeId() %>"
                                            class="resume-btn download-btn"
                                        >
                                            Download
                                        </a>

                                    </div>

                                <% } else { %>

                                    <span class="no-resume">
                                        Not Uploaded
                                    </span>

                                <% } %>

                            </td>


                            <!-- =================================
                                 STATUS
                                 ================================= -->

                            <td>

                                <strong>
                                    <%= applicant.getStatus() %>
                                </strong>

                            </td>


                            <!-- =================================
                                 UPDATE STATUS
                                 ================================= -->

                            <td>

                                <form
                                    action="<%= request.getContextPath() %>/application"
                                    method="post"
                                    class="status-form"
                                >

                                    <input
                                        type="hidden"
                                        name="action"
                                        value="updateStatus"
                                    >

                                    <input
                                        type="hidden"
                                        name="applicationId"
                                        value="<%= applicant.getApplicationId() %>"
                                    >

                                    <input
                                        type="hidden"
                                        name="jobId"
                                        value="<%= applicant.getJobId() %>"
                                    >

                                    <select
                                        name="status"
                                        required
                                    >

                                        <option
                                            value="APPLIED"
                                            <%= "APPLIED".equals(
                                                    applicant.getStatus())
                                                    ? "selected"
                                                    : "" %>
                                        >
                                            APPLIED
                                        </option>

                                        <option
                                            value="SHORTLISTED"
                                            <%= "SHORTLISTED".equals(
                                                    applicant.getStatus())
                                                    ? "selected"
                                                    : "" %>
                                        >
                                            SHORTLISTED
                                        </option>

                                        <option
                                            value="REJECTED"
                                            <%= "REJECTED".equals(
                                                    applicant.getStatus())
                                                    ? "selected"
                                                    : "" %>
                                        >
                                            REJECTED
                                        </option>

                                        <option
                                            value="SELECTED"
                                            <%= "SELECTED".equals(
                                                    applicant.getStatus())
                                                    ? "selected"
                                                    : "" %>
                                        >
                                            SELECTED
                                        </option>

                                    </select>

                                    <button
                                        type="submit"
                                        class="update-btn"
                                    >
                                        Update
                                    </button>

                                </form>

                            </td>

                        </tr>

                    <% } %>

                </tbody>

            </table>

        <% } %>


        <a
            href="<%= request.getContextPath() %>/recruiter-dashboard.jsp"
            class="back-btn"
        >
            ← Back to Dashboard
        </a>

    </div>

</div>

</body>

</html>