<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.smarthire.model.Job" %>
<%@ page import="com.smarthire.model.User" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>SmartHire - Jobs</title>

    <style>

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f7fb;
        }

        .navbar {
            background: #1e3a8a;
            color: white;
            padding: 18px 40px;
            display: flex;
            justify-content: space-between;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            margin-left: 18px;
        }

        .container {
            width: 92%;
            max-width: 1150px;
            margin: 35px auto;
        }

        .search-box,
        .job-card {
            background: white;
            padding: 25px;
            border-radius: 12px;
            margin-bottom: 20px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }

        .search-grid {
            display: grid;
            grid-template-columns: 2fr 1fr 1fr auto;
            gap: 12px;
        }

        input,
        select {
            padding: 11px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        button,
        .btn {
            background: #2563eb;
            color: white;
            border: none;
            padding: 11px 15px;
            border-radius: 6px;
            text-decoration: none;
            cursor: pointer;
            display: inline-block;
        }

        .danger {
            background: #dc2626;
        }

        .job-grid {
            display: grid;
            grid-template-columns:
                repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
        }

        .job-card h3 {
            color: #1e3a8a;
        }

        .info {
            color: #555;
            margin: 7px 0;
        }

        .description {
            color: #666;
            margin: 15px 0;
            line-height: 1.5;
        }

        .actions {
            margin-top: 18px;
            display: flex;
            gap: 8px;
            flex-wrap: wrap;
        }

        @media(max-width: 800px) {

            .search-grid {
                grid-template-columns: 1fr;
            }
        }

    </style>

</head>

<body>

<%
    User user =
        (User) session.getAttribute("user");

    if (user == null) {

        response.sendRedirect(
            request.getContextPath() +
            "/login.jsp"
        );

        return;
    }

    List<Job> jobs =
        (List<Job>) request.getAttribute("jobs");

    Boolean myJobs =
        (Boolean) request.getAttribute("myJobs");
%>

<div class="navbar">

    <strong>SmartHire</strong>

    <div>

        <% if ("JOB_SEEKER".equals(user.getRole())) { %>

            <a href="jobseeker-dashboard.jsp">
                Dashboard
            </a>

        <% } else if ("RECRUITER".equals(user.getRole())) { %>

            <a href="recruiter-dashboard.jsp">
                Dashboard
            </a>

        <% } else if ("ADMIN".equals(user.getRole())) { %>

            <a href="admin-dashboard.jsp">
                Dashboard
            </a>

        <% } %>

        <a href="logout">
            Logout
        </a>

    </div>

</div>

<div class="container">

    <% if (!Boolean.TRUE.equals(myJobs)) { %>

        <div class="search-box">

            <h2>
                Search Jobs
            </h2>

            <form action="jobs" method="get">

                <div class="search-grid">

                    <input
                        type="text"
                        name="keyword"
                        placeholder="Java, Developer, MySQL..."
                    >

                    <input
                        type="text"
                        name="location"
                        placeholder="Location"
                    >

                    <select name="jobType">

                        <option value="">
                            All Types
                        </option>

                        <option value="FULL_TIME">
                            Full Time
                        </option>

                        <option value="PART_TIME">
                            Part Time
                        </option>

                        <option value="INTERNSHIP">
                            Internship
                        </option>

                        <option value="CONTRACT">
                            Contract
                        </option>

                    </select>

                    <button type="submit">
                        Search
                    </button>

                </div>

            </form>

        </div>

    <% } else { %>

        <div class="search-box">

            <h2>
                My Jobs
            </h2>

            <p>
                Jobs posted by your account.
            </p>

            <a
                href="jobs?action=post"
                class="btn"
            >
                Post New Job
            </a>

        </div>

    <% } %>

    <div class="job-grid">

        <% if (jobs != null && !jobs.isEmpty()) { %>

            <% for (Job job : jobs) { %>

                <div class="job-card">

                    <h3>
                        <%= job.getTitle() %>
                    </h3>

                    <div class="info">
                        <strong>Location:</strong>
                        <%= job.getLocation() %>
                    </div>

                    <div class="info">
                        <strong>Salary:</strong>
                        <%= job.getSalary() %>
                    </div>

                    <div class="info">
                        <strong>Job Type:</strong>
                        <%= job.getJobType() %>
                    </div>

                    <div class="info">
                        <strong>Skills:</strong>
                        <%= job.getSkills() %>
                    </div>

                    <div class="description">
                        <%= job.getDescription() %>
                    </div>

                    <div class="actions">

                        <a
                            href="jobs?action=details&id=<%= job.getId() %>"
                            class="btn"
                        >
                            View Details
                        </a>

                        <% if ("JOB_SEEKER".equals(user.getRole())) { %>

                            <form
                                action="application"
                                method="post"
                                style="display:inline;"
                            >

                                <input
                                    type="hidden"
                                    name="action"
                                    value="apply"
                                >

                                <input
                                    type="hidden"
                                    name="jobId"
                                    value="<%= job.getId() %>"
                                >

                                <button type="submit">
                                    Apply
                                </button>

                            </form>

                        <% } %>

                        <% if ("RECRUITER".equals(user.getRole()) &&
                               user.getId() == job.getRecruiterId()) { %>

                            <a
                                href="jobs?action=edit&id=<%= job.getId() %>"
                                class="btn"
                            >
                                Edit
                            </a>

                            <a
                                href="jobs?action=delete&id=<%= job.getId() %>"
                                class="btn danger"
                                onclick="return confirm('Delete this job?');"
                            >
                                Delete
                            </a>

                            <a
							    href="application?action=job&jobId=<%= job.getId() %>"
							    class="btn"
							>
							    Applicants
							</a>

                        <% } %>

                    </div>

                </div>

            <% } %>

        <% } else { %>

            <div class="search-box">

                <h3>
                    No jobs found.
                </h3>

            </div>

        <% } %>

    </div>

</div>

</body>
</html>